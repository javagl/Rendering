/*
 * www.javagl.de - Rendering
 * 
 * Copyright 2010-2015 Marco Hutter - http://www.javagl.de
 */
 
/**
 * A vertex shader emulating the OpenGL fixed function pipeline
 */ 
 
#version 330 core

#define MAX_LIGHTS 8

#define LIGHT_TYPE_DIRECTIONAL 0
#define LIGHT_TYPE_POINT 1
#define LIGHT_TYPE_SPOT 2

// The attributes of the object, stored in VBOs. 
// Namely the position, normal, color and texcoords
in vec3 vertexPosition;
in vec3 vertexNormal;
in vec4 vertexColor;
in vec2 vertexTexcoord0;
in vec2 vertexTexcoord1;
in vec2 vertexTexcoord2;
in vec2 vertexTexcoord3;
uniform int numTextures;

//The matrices
uniform mat4 modelMatrix;
uniform mat4 normalMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

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

// Position, normal, color and texture coordinates for fragment shader
out vec3 fragmentPosition;
out vec3 fragmentNormal;
out vec4 fragmentColor;
out vec2 fragmentTexcoord0;
out vec2 fragmentTexcoord1;
out vec2 fragmentTexcoord2;
out vec2 fragmentTexcoord3;

void main (void)
{
    // Compute the eye-coordinate position of the vertex
    mat4 modelviewMatrix = viewMatrix*modelMatrix;
    vec4 position = vec4(vertexPosition, 1.0);
    vec4 fragmentPosition4 = modelviewMatrix * position; 
    fragmentPosition = fragmentPosition4.xyz / fragmentPosition4.w;

    // Compute the transformed normal
    fragmentNormal = (normalMatrix*vec4(normalize(vertexNormal), 0.0)).xyz;
    
    fragmentColor = vertexColor;

    // Pass the texture coordinates to the fragment shader
    if (numTextures > 0) fragmentTexcoord0 = vertexTexcoord0;
    if (numTextures > 1) fragmentTexcoord1 = vertexTexcoord1;
    if (numTextures > 2) fragmentTexcoord2 = vertexTexcoord2;
    if (numTextures > 3) fragmentTexcoord3 = vertexTexcoord3;
    
    // Do fixed functionality vertex transform
    gl_Position = projectionMatrix*modelviewMatrix*position;
}



