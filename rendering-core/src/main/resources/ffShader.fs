/*
 * www.javagl.de - Rendering
 * 
 * Copyright 2010-2015 Marco Hutter - http://www.javagl.de
 */
 
/**
 * A fragment shader emulating the OpenGL fixed function pipeline
 */ 
 
#version 330 core

#define MAX_LIGHTS 8

#define LIGHT_TYPE_DIRECTIONAL 0
#define LIGHT_TYPE_POINT 1
#define LIGHT_TYPE_SPOT 2


// The position, normal, color and texture coordinates
in vec3 fragmentPosition;
in vec3 fragmentNormal;
in vec4 fragmentColor;
in vec2 fragmentTexcoord0;
in vec2 fragmentTexcoord1;
in vec2 fragmentTexcoord2;
in vec2 fragmentTexcoord3;
uniform int numTextures;

//The matrices
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 normalMatrix;


//The light structure. The position and spotDirection
//are given in view space
struct Light
{
    int type;
    
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 position;

    vec3 spotDirection;
    float spotExponent;
    float spotCutoff;
    
    float constantAttenuation;
    float linearAttenuation;
    float quadraticAttenuation;
};

// The lights
uniform Light lights[MAX_LIGHTS];
uniform int numLights;

uniform vec4 globalAmbient;


//The material structure
struct Material 
{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emission;
    float shininess;
};

// The material
uniform Material material;

// The texture samplers
uniform sampler2D texture0;
uniform sampler2D texture1;
uniform sampler2D texture2;
uniform sampler2D texture3;

// The ambient, diffuse and specular
// components of the lights output
vec4 lightsAmbient;
vec4 lightsDiffuse;
vec4 lightsSpecular;
vec4 lightsColor;

// The resulting pixel color
out vec4 outColor;



//Compute the contribution of the point light with index i
//to lightsAmbient, lightsDiffuse and lightsSpecular
void pointLight(in int i)
{
    // The eye position in view space is constant
    vec3 eye = vec3 (0.0, 0.0, 1.0);
    
    // Normalize the input normal
    vec3 normal = normalize(fragmentNormal);
   
    // Compute vector from surface to light position
    vec3 VP = vec3 (lights[i].position) - fragmentPosition;

    // Compute distance between surface and light position
    float d = length(VP);

    // Normalize the vector from surface to light position
    VP = normalize(VP);

    // Compute attenuation
    float attenuation = 1.0 / 
        (lights[i].constantAttenuation +
         lights[i].linearAttenuation * d +
         lights[i].quadraticAttenuation * d * d);


    // Compute the ambient contribution
    lightsAmbient += lights[i].ambient * attenuation;
    
    // Check if the surface faces the light 
    float nDotVP = dot(normal, VP);
    if (!gl_FrontFacing) 
    {
        nDotVP = -nDotVP;
    }
    if (nDotVP > 0)
    {
        // Add the diffuse contribution
        lightsDiffuse  += lights[i].diffuse * nDotVP * attenuation;

        // Compute the specular contribution
        vec3 halfVector = normalize(VP + eye);
        float nDotHV = dot(normal, halfVector);
        if (!gl_FrontFacing) 
        {
            nDotHV = -nDotHV;
        }
        float pf = pow(nDotHV, material.shininess);
        lightsSpecular += lights[i].specular * pf * attenuation;
    }
}



//Compute the contribution of the spot light with index i
//to lightsAmbient, lightsDiffuse and lightsSpecular
void spotLight(in int i)
{
    // The eye position in view space is constant
    vec3 eye = vec3 (0.0, 0.0, 1.0);
   
    // Normalize the input normal
    vec3 normal = normalize(fragmentNormal);
   
    // Compute vector from surface to light position
    vec3 VP = vec3 (lights[i].position) - fragmentPosition;
   
    // Compute distance between surface and light position
    float d = length(VP);

    // Normalize the vector from surface to light position
    VP = normalize(VP);

    // Compute attenuation
    float attenuation = 1.0 / 
        (lights[i].constantAttenuation +
         lights[i].linearAttenuation * d +
         lights[i].quadraticAttenuation * d * d);

    // Check if the surface point is inside the light cone
    float spotDot = dot(-VP, normalize(lights[i].spotDirection));
    float spotAttenuation = 0;
    if (spotDot >= cos(radians(lights[i].spotCutoff)))
    {
        spotAttenuation = pow(spotDot, lights[i].spotExponent);
    }
    attenuation *= spotAttenuation;

    // Compute the ambient contribution
    lightsAmbient  += lights[i].ambient * attenuation;
    
    // Check if the surface faces the light 
    float nDotVP = dot(normal, VP);
    if (!gl_FrontFacing) 
    {
        nDotVP = -nDotVP;
    }
    if (nDotVP > 0)
    {
        // Add the diffuse contribution
        lightsDiffuse  += lights[i].diffuse * nDotVP * attenuation;

        // Compute the specular contribution
        vec3 halfVector = normalize(VP + eye);
        float nDotHV = dot(normal, halfVector);
        if (!gl_FrontFacing) 
        {
            nDotHV = -nDotHV;
        }
        float pf = pow(nDotHV, material.shininess);
        lightsSpecular += lights[i].specular * pf * attenuation;
    }
}




//Compute the contribution of the directional light with index i
//to lightsAmbient, lightsDiffuse and lightsSpecular
void directionalLight(in int i)
{
    // The eye position in view space is constant
    vec3 eye = vec3 (0.0, 0.0, 1.0);

    // Normalize the input normal
    vec3 normal = normalize(fragmentNormal);

    // Compute the light direction (stored in the position 
    // for directional lights)
    vec3 VP = normalize(lights[i].position.xyz);

    // Compute the ambient contribution
    lightsAmbient  += lights[i].ambient;
    
    // Check if the surface faces the light 
    float nDotVP = dot(normal, VP);
    if (!gl_FrontFacing) 
    {
        nDotVP = -nDotVP;
    }
    if (nDotVP > 0)
    {
        // Add the diffuse contribution
        lightsDiffuse  += lights[i].diffuse * nDotVP;
        
        // Compute the specular contribution
        vec3 halfVector = normalize(VP + eye);
        float nDotHV = dot(normal, halfVector);
        if (!gl_FrontFacing) 
        {
            nDotHV = -nDotHV;
        }
        float pf = pow(nDotHV, material.shininess);
        lightsSpecular += lights[i].specular * pf;
    }

}


// Compute the contributions of all lights  
// to lightsAmbient, lightsDiffuse and lightsSpecular
void handleLights()
{
    // Initialize the light intensity accumulators
    lightsAmbient = globalAmbient;
    lightsDiffuse  = vec4 (0.0);
    lightsSpecular = vec4 (0.0);

    // Accumulate all light contributions 
    for (int i=0; i<numLights; i++)
    {
        Light light = lights[i];
        if (light.type == LIGHT_TYPE_DIRECTIONAL)
        {
            directionalLight(i);
        }
        if (light.type == LIGHT_TYPE_POINT)
        {
            pointLight(i);
        }
        if (light.type == LIGHT_TYPE_SPOT)
        {
            spotLight(i);
        }
    }
    
    // Light component of the pixel color
    lightsColor = 
        lightsAmbient  * material.ambient +
        lightsDiffuse  * material.diffuse +
        lightsSpecular * material.specular +
        material.emission;
    lightsColor = clamp(lightsColor, 0.0, 1.0 );
}



void main (void) 
{
    vec4 color = fragmentColor;
    if (numTextures == 1) 
    {
    	color =            texture2D(texture0, fragmentTexcoord0);
    }
    if (numTextures == 2) 
    {
        color =            texture2D(texture0, fragmentTexcoord0);
        color = mix(color, texture2D(texture1, fragmentTexcoord1), 0.5);
    }  
    if (numTextures == 3) 
    {
        color =            texture2D(texture0, fragmentTexcoord0);
        color = mix(color, texture2D(texture1, fragmentTexcoord1), 0.5);
        color = mix(color, texture2D(texture2, fragmentTexcoord2), 0.5);
    }  
    if (numTextures == 4) 
    {
        color =            texture2D(texture0, fragmentTexcoord0);
        color = mix(color, texture2D(texture1, fragmentTexcoord1), 0.5);
        color = mix(color, texture2D(texture2, fragmentTexcoord2), 0.5);
        color = mix(color, texture2D(texture3, fragmentTexcoord3), 0.5);
    }
    if (numLights == 0)
    {
        outColor = color; 
    }
    else
    {
        handleLights();
        outColor = lightsColor * color;
    }
}
















