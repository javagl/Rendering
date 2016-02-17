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

//import de.javagl.rendering.core.impl.AttributeMappings;

/**
 * Methods and classes for creating {@link RenderedObject} instances.
 */
public class RenderedObjects
{
    /**
     * A Builder for {@link RenderedObject} instances
     */
    public static class Builder
    {
        /**
         * The {@link GraphicsObject} 
         */
        private final GraphicsObject graphicsObject;
        
        /**
         * The {@link Program}
         */
        private final Program program;
        
        /**
         * The {@link Mappings.Builder} for the {@link Mapping}
         * that maps {@link Parameter}s of the {@link Program} 
         * to {@link Attribute}s 
         */
        private final Mappings.Builder<Parameter, Attribute> 
            attributeMappingBuilder;
        
        /**
         * The {@link Mappings.Builder} for the {@link Mapping}
         * that maps {@link Parameter}s of the {@link Program} 
         * to an {@link Texture}s 
         */
        private final Mappings.Builder<Parameter, Texture> 
            textureMappingBuilder;
        
        /**
         * Private constructor for the Builder
         * 
         * @param graphicsObject The {@link GraphicsObject}
         * @param program The {@link Program}
         */
        private Builder(GraphicsObject graphicsObject, Program program)
        {
            this.graphicsObject = graphicsObject;
            this.program = program;
            this.attributeMappingBuilder = Mappings.Builder.create();
            this.textureMappingBuilder = Mappings.Builder.create();
        }
        
        /**
         * Connect the specified input {@link Parameter} of the
         * {@link Program} to the given {@link Attribute} of the
         * {@link GraphicsObject}.
         * 
         * @param parameter The {@link Parameter}
         * @param attribute The {@link Attribute}
         * @return This builder
         * @throws IllegalArgumentException If the current 
         * {@link GraphicsObject} does not have the specified
         * {@link Attribute}.
         */
        public Builder connect(Parameter parameter, Attribute attribute)
        {
            if (graphicsObject.getDataBuffer(attribute) == null)
            {
                throw new IllegalArgumentException(
                    "The current GraphicsObject does not have " +
                    "an attribute named "+attribute);
            }
            attributeMappingBuilder.put(parameter, attribute);
            return this;
        }
    
        /**
         * Connect the specified input {@link Parameter} of the
         * {@link Program} to the given {@link Texture}.
         * 
         * @param parameter The {@link Parameter}
         * @param texture The {@link Texture}
         * @return This builder
         */
        public Builder connect(Parameter parameter, Texture texture)
        {
            textureMappingBuilder.put(parameter, texture);
            return this;
        }
        
        /**
         * Build the current {@link RenderedObject}
         * 
         * @return The {@link RenderedObject}
         */
        public RenderedObject build()
        {
            RenderedObject result = new DefaultRenderedObject(
                program, graphicsObject, 
                attributeMappingBuilder.build(), 
                textureMappingBuilder.build());
            return result;
        }
        
    }

    /**
     * Creates a {@link Builder} for a new {@link RenderedObject}
     * 
     * @param graphicsObject The {@link GraphicsObject}
     * @param program The {@link Program}
     * @return The {@link Builder} for the {@link RenderedObject}
     */
    public static Builder create(GraphicsObject graphicsObject, Program program)
    {
        return new Builder(graphicsObject, program);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private RenderedObjects()
    {
        // Private constructor to prevent instantiation
    }

}
