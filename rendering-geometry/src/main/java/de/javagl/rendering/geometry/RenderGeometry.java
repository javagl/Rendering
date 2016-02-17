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

/**
 * Interface for Geometries that contain the basic information
 * required for rendering. In addition to the information 
 * provided by a plain {@link Geometry}, the RenderGeometry
 * may also contain normals and texture coordinates.
 */
public interface RenderGeometry extends Geometry
{
    /**
     * Returns the normals of this geometry, or <code>null</code> if
     * this Geometry does not have normal information.
     * 
     * @return The normals of this Geometry, or <code>null</code> 
     */
    Array3f getNormals();
    
    /**
     * Returns the texture coordinates of this Geometry, or
     * <code>null</code> if this Geometry does not have
     * texture coordinates.
     * 
     * @return The texture coordinates, or <code>null</code>.
     */
    Array2f getTexCoords();
}
