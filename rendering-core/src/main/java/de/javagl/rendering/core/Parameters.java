/*
 * www.javagl.de - Rendering
 * 
 * Copyright 2010-2016 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.rendering.core;

import java.util.Arrays;
import java.util.List;

/**
 * Commonly used {@link Parameter} instances and methods
 * to create new ones
 */
public class Parameters
{
    /**
     * A {@link Parameter} describing the VERTEX_POSITION for a program.
     * The name is "vertexPosition" and the {@link ParameterType} is TUPLE3F.
     */
    public static final Parameter VERTEX_POSITION = 
        Parameters.create("vertexPosition", ParameterType.TUPLE3F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_NORMAL for a program.
     * The name is "vertexNormal" and the {@link ParameterType} is TUPLE3F.
     */
    public static final Parameter VERTEX_NORMAL = 
        Parameters.create("vertexNormal", ParameterType.TUPLE3F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_COLOR for a program.
     * The name is "vertexColor" and the {@link ParameterType} is TUPLE3F.
     */
    public static final Parameter VERTEX_COLOR = 
        Parameters.create("vertexColor", ParameterType.TUPLE3F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_TANGENT for a program.
     * The name is "vertexTangent" and the {@link ParameterType} is TUPLE3F.
     */
    public static final Parameter VERTEX_TANGENT = 
        Parameters.create("vertexTangent", ParameterType.TUPLE3F);

    
    
    /**
     * A {@link Parameter} describing the VERTEX_TEXCOORD0 for a program.
     * The name is "vertexTexcoord0" and the {@link ParameterType} is TUPLE2F.
     */
    public static final Parameter VERTEX_TEXCOORD0 = 
        Parameters.create("vertexTexcoord0", ParameterType.TUPLE2F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_TEXCOORD1 for a program.
     * The name is "vertexTexcoord1" and the {@link ParameterType} is TUPLE2F.
     */
    public static final Parameter VERTEX_TEXCOORD1 = 
        Parameters.create("vertexTexcoord1", ParameterType.TUPLE2F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_TEXCOORD2 for a program.
     * The name is "vertexTexcoord2" and the {@link ParameterType} is TUPLE2F.
     */
    public static final Parameter VERTEX_TEXCOORD2 = 
        Parameters.create("vertexTexcoord2", ParameterType.TUPLE2F);
    
    
    /**
     * A {@link Parameter} describing the VERTEX_TEXCOORD3 for a program.
     * The name is "vertexTexcoord3" and the {@link ParameterType} is TUPLE2F.
     */
    public static final Parameter VERTEX_TEXCOORD3 = 
        Parameters.create("vertexTexcoord3", ParameterType.TUPLE2F);
    
    
    
    /**
     * A {@link Parameter} describing the MODEL_MATRIX for a program.
     * The name is "modelMatrix" and the {@link ParameterType} is MATRIX4F.
     */
    public static final Parameter MODEL_MATRIX = 
        Parameters.create("modelMatrix", ParameterType.MATRIX4F);
    
    
    /**
     * A {@link Parameter} describing the NORMAL_MATRIX for a program.
     * The name is "normalMatrix" and the {@link ParameterType} is MATRIX4F.
     */
    public static final Parameter NORMAL_MATRIX = 
        Parameters.create("normalMatrix", ParameterType.MATRIX4F);
    
    
    /**
     * A {@link Parameter} describing the VIEW_MATRIX for a program.
     * The name is "viewMatrix" and the {@link ParameterType} is MATRIX4F.
     */
    public static final Parameter VIEW_MATRIX = 
        Parameters.create("viewMatrix", ParameterType.MATRIX4F);
    
    
    /**
     * A {@link Parameter} describing the PROJECTION_MATRIX for a program.
     * The name is "projectionMatrix" and the {@link ParameterType} is MATRIX4F.
     */
    public static final Parameter PROJECTION_MATRIX = 
        Parameters.create("projectionMatrix", ParameterType.MATRIX4F);
    
    
    /**
     * A class summarizing {@link Parameter}s for the material.
     */
    public static final class MaterialParameters
    {
        /**
         * A {@link Parameter} describing the EMISSION of a material
         * for a program. The name is "material.emission", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter EMISSION;

        /**
         * A {@link Parameter} describing the AMBIENT part of a material
         * for a program. The name is "material.ambient", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter AMBIENT;

        /**
         * A {@link Parameter} describing the DIFFUSE part of a material
         * for a program. The name is "material.diffuse", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter DIFFUSE;

        /**
         * A {@link Parameter} describing the SPECULAR part of a material
         * for a program. The name is "material.specular", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter SPECULAR;

        /**
         * A {@link Parameter} describing the SHININESS of a material
         * for a program. The name is "material.shininess", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter SHININESS;
        
        /**
         * Creates a new {@link MaterialParameters} structure with
         * the given prefix for the names of the fields.
         * 
         * @param prefix The prefix
         */
        private MaterialParameters(String prefix)
        {
            EMISSION = create(prefix+"emission", ParameterType.TUPLE4F);
            AMBIENT = create(prefix+"ambient", ParameterType.TUPLE4F);
            DIFFUSE = create(prefix+"diffuse", ParameterType.TUPLE4F);
            SPECULAR = create(prefix+"specular", ParameterType.TUPLE4F);
            SHININESS = create(prefix+"shininess", ParameterType.FLOAT);
        }
    }
    
    /**
     * The {@link MaterialParameters}
     */
    public static final MaterialParameters MATERIAL = 
        new MaterialParameters("material.");
    
    
    
    /**
     * A class summarizing {@link Parameter}s for a light.
     */
    public static final class LightParameters
    {
        /**
         * A {@link Parameter} describing the TYPE of a light
         * for a program. The name is "light[n].type", and the
         * {@link ParameterType} is INT. 
         */
        public final Parameter TYPE;

        /**
         * A {@link Parameter} describing the AMBIENT part of a light
         * for a program. The name is "light[n].ambient", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter AMBIENT;

        /**
         * A {@link Parameter} describing the DIFFUSE part of a light
         * for a program. The name is "light[n].diffuse", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter DIFFUSE;

        /**
         * A {@link Parameter} describing the SPECULAR part of a light
         * for a program. The name is "light[n].specular", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter SPECULAR;

        /**
         * A {@link Parameter} describing the POSITION of a light
         * for a program. The name is "light[n].position", and the
         * {@link ParameterType} is TUPLE4F. 
         */
        public final Parameter POSITION;

        /**
         * A {@link Parameter} describing the SPOT_DIRECTION of a light
         * for a program. The name is "light[n].spotDirection", and the
         * {@link ParameterType} is TUPLE3F. 
         */
        public final Parameter SPOT_DIRECTION;

        /**
         * A {@link Parameter} describing the SPOT_EXPONENT of a light
         * for a program. The name is "light[n].spotExponent", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter SPOT_EXPONENT;

        /**
         * A {@link Parameter} describing the SPOT_CUTOFF of a light
         * for a program. The name is "light[n].spotCutoff", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter SPOT_CUTOFF;

        /**
         * A {@link Parameter} describing the CONSTANT_ATTENUATION of a light
         * for a program. The name is "light[n].constantAttenuation", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter CONSTANT_ATTENUATION;

        /**
         * A {@link Parameter} describing the LINEAR_ATTENUATION of a light
         * for a program. The name is "light[n].linearAttenuation", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter LINEAR_ATTENUATION;

        /**
         * A {@link Parameter} describing the QUADRATIC_ATTENUATION of a light
         * for a program. The name is "light[n].quadraticAttenuation", and the
         * {@link ParameterType} is FLOAT. 
         */
        public final Parameter QUADRATIC_ATTENUATION;
        
        /**
         * Creates a new {@link LightParameters} structure with
         * the given prefix for the names of the fields.
         * 
         * @param prefix The prefix
         */
        private LightParameters(String prefix)
        {
            TYPE = create(prefix+"type", ParameterType.INT); 
            AMBIENT = create(prefix+"ambient", ParameterType.TUPLE4F);
            DIFFUSE = create(prefix+"diffuse", ParameterType.TUPLE4F);
            SPECULAR = create(prefix+"specular", ParameterType.TUPLE4F);
            POSITION = create(prefix+"position", ParameterType.TUPLE4F);
            SPOT_DIRECTION = create(prefix+"spotDirection", ParameterType.TUPLE3F);
            SPOT_EXPONENT = create(prefix+"spotExponent", ParameterType.FLOAT);
            SPOT_CUTOFF = create(prefix+"spotCutoff", ParameterType.FLOAT);
            CONSTANT_ATTENUATION = 
                create(prefix+"constantAttenuation", ParameterType.FLOAT);
            LINEAR_ATTENUATION = 
                create(prefix+"linearAttenuation", ParameterType.FLOAT);
            QUADRATIC_ATTENUATION = 
                create(prefix+"quadraticAttenuation", ParameterType.FLOAT);
        }
    }
    

    /**
     * The {@link LightParameters} for light 0
     */
    public static final LightParameters LIGHT0 = 
        new LightParameters("lights[0].");
    
    /**
     * The {@link LightParameters} for light 1
     */
    public static final LightParameters LIGHT1 = 
        new LightParameters("lights[1].");
    
    /**
     * The {@link LightParameters} for light 2
     */
    public static final LightParameters LIGHT2 = 
        new LightParameters("lights[2].");
    
    /**
     * The {@link LightParameters} for light 3
     */
    public static final LightParameters LIGHT3 = 
        new LightParameters("lights[3].");
    
    /**
     * The {@link LightParameters} for light 4
     */
    public static final LightParameters LIGHT4 = 
        new LightParameters("lights[4].");
    
    /**
     * The {@link LightParameters} for light 5
     */
    public static final LightParameters LIGHT5 = 
        new LightParameters("lights[5].");
    
    /**
     * The {@link LightParameters} for light 6
     */
    public static final LightParameters LIGHT6 = 
        new LightParameters("lights[6].");
    
    /**
     * The {@link LightParameters} for light 7
     */
    public static final LightParameters LIGHT7 = 
        new LightParameters("lights[7].");
    
    /**
     * The (unmodifiable) List of {@link LightParameters}
     */
    public static final List<LightParameters> LIGHTS = Arrays.asList(
        LIGHT0, LIGHT1, LIGHT2, LIGHT3, LIGHT4, LIGHT5, LIGHT6, LIGHT7); 
    
    /**
     * A {@link Parameter} describing the number of lights for a program.
     * The name is "numLights" and the {@link ParameterType} is INT.
     */
    public static final Parameter NUM_LIGHTS = 
        Parameters.create("numLights", ParameterType.INT);
    
    
    
    
    
    
    
    
    
   
    /**
     * A {@link Parameter} describing the number of textures for a program.
     * The name is "numTextures" and the {@link ParameterType} is INT.
     */
    public static final Parameter NUM_TEXTURES = 
        Parameters.create("numTextures", ParameterType.INT);
     
    /**
     * A {@link Parameter} describing the TEXTURE0 for a program.
     * The name is "texture0" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE0 = 
        Parameters.create("texture0", ParameterType.TEXTURE2D);
     
    
    /**
     * A {@link Parameter} describing the TEXTURE1 for a program.
     * The name is "texture1" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE1 = 
        Parameters.create("texture1", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE2 for a program.
     * The name is "texture2" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE2 = 
        Parameters.create("texture2", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE3 for a program.
     * The name is "texture3" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE3 = 
        Parameters.create("texture3", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE4 for a program.
     * The name is "texture4" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE4 = 
        Parameters.create("texture4", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE5 for a program.
     * The name is "texture5" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE5 = 
        Parameters.create("texture5", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE6 for a program.
     * The name is "texture6" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE6 = 
        Parameters.create("texture6", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE7 for a program.
     * The name is "texture7" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE7 = 
        Parameters.create("texture7", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE8 for a program.
     * The name is "texture8" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE8 = 
        Parameters.create("texture8", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE9 for a program.
     * The name is "texture9" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE9 = 
        Parameters.create("texture9", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE10 for a program.
     * The name is "texture10" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE10 = 
        Parameters.create("texture10", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE11 for a program.
     * The name is "texture11" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE11 = 
        Parameters.create("texture11", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE12 for a program.
     * The name is "texture12" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE12 = 
        Parameters.create("texture12", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE13 for a program.
     * The name is "texture13" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE13 = 
        Parameters.create("texture13", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE14 for a program.
     * The name is "texture14" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE14 = 
        Parameters.create("texture14", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE15 for a program.
     * The name is "texture15" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE15 = 
        Parameters.create("texture15", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE16 for a program.
     * The name is "texture16" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE16 = 
        Parameters.create("texture16", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE17 for a program.
     * The name is "texture17" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE17 = 
        Parameters.create("texture17", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE18 for a program.
     * The name is "texture18" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE18 = 
        Parameters.create("texture18", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE19 for a program.
     * The name is "texture19" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE19 = 
        Parameters.create("texture19", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE20 for a program.
     * The name is "texture20" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE20 = 
        Parameters.create("texture20", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE21 for a program.
     * The name is "texture21" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE21 = 
        Parameters.create("texture21", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE22 for a program.
     * The name is "texture22" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE22 = 
        Parameters.create("texture22", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE23 for a program.
     * The name is "texture23" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE23 = 
        Parameters.create("texture23", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE24 for a program.
     * The name is "texture24" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE24 = 
        Parameters.create("texture24", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE25 for a program.
     * The name is "texture25" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE25 = 
        Parameters.create("texture25", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE26 for a program.
     * The name is "texture26" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE26 = 
        Parameters.create("texture26", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE27 for a program.
     * The name is "texture27" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE27 = 
        Parameters.create("texture27", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE28 for a program.
     * The name is "texture28" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE28 = 
        Parameters.create("texture28", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE29 for a program.
     * The name is "texture29" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE29 = 
        Parameters.create("texture29", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE30 for a program.
     * The name is "texture30" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE30 = 
        Parameters.create("texture30", ParameterType.TEXTURE2D);
    
    
    /**
     * A {@link Parameter} describing the TEXTURE31 for a program.
     * The name is "texture31" and the {@link ParameterType} is TEXTURE2D.
     */
    public static final Parameter TEXTURE31 = 
        Parameters.create("texture31", ParameterType.TEXTURE2D);


    /**
     * Creates a new {@link Parameter} with the given name and {@link ParameterType}
     * 
     * @param name The name
     * @param type The {@link ParameterType}
     * @return The new Parameter
     */
    public static Parameter create(String name, ParameterType type)
    {
        Parameter parameter = Parameter.create(name, type);
        return parameter;
    }

    
    /**
     * Private constructor to prevent instantiation
     */
    private Parameters()
    {
        // Private constructor to prevent instantiation
    }
}

