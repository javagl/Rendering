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

package de.javagl.rendering.geometry;

import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;

/**
 * Methods and classes for creating {@link RenderGeometry} instances.
 */
public class RenderGeometries
{
    /**
     * A builder for {@link MutableRenderGeometry} instances.
     */
    public static class MutableRenderGeometryBuilder
    {
        /**
         * Creates a new {@link MutableRenderGeometryBuilder}
         * @return The new {@link MutableRenderGeometryBuilder}
         */
        public static MutableRenderGeometryBuilder create()
        {
            return new MutableRenderGeometryBuilder();
        }
        
        /**
         * The {@link MutableRenderGeometry} that is currently being built
         */
        private DefaultRenderGeometry geometry;
        
        /**
         * Creates a new {@link MutableRenderGeometryBuilder}
         */
        private MutableRenderGeometryBuilder()
        {
            geometry = new DefaultRenderGeometry();
        }
        
        /**
         * Add the given vertex to the geometry.
         * 
         * @param vertex The vertex
         * @return This builder
         */
        public MutableRenderGeometryBuilder addVertex(Tuple3f vertex)
        {
            geometry.addVertex(vertex);
            return this;
        }
        
        /**
         * Add the given texture coordinate to the geometry
         * 
         * @param texCoord The texture coordinate
         * @return This builder
         */
        public MutableRenderGeometryBuilder addTexCoord(Tuple2f texCoord)
        {
            geometry.addTexCoord(texCoord);
            return this;
        }

        /**
         * Adds the given normal to the geometry
         * 
         * @param normal The normal
         * @return This builder
         */
        public MutableRenderGeometryBuilder addNormal(Tuple3f normal)
        {
            geometry.addNormal(normal);
            return this;
        }

        /**
         * Add the triangle consisting of the given indices to
         * the geometry. The same indices will be used for
         * vertices, texture coordinates and normals.
         * 
         * @param i0 The first index
         * @param i1 The second index
         * @param i2 The third index
         * @return This builder
         */
        public MutableRenderGeometryBuilder addTriangle(int i0, int i1, int i2)
        {
            geometry.addTriangle(i0, i1, i2);
            return this;
        }
        
        /**
         * Build the {@link MutableRenderGeometry}
         * 
         * @return The {@link MutableRenderGeometry}
         */
        public MutableRenderGeometry build()
        {
            MutableRenderGeometry result = geometry;
            geometry = null;
            return result;
        }
        
    }
}
