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

package de.javagl.rendering.core.gl;

import de.javagl.rendering.core.Attribute;

/**
 * Interface describing a graphical object in OpenGL. It mainly consists of 
 * a buffer for indices, and VBO for the different {@link Attribute}s which 
 * are contained in this object. The indices buffer may be <code>null</code>,
 * for non-indexed geometry. Otherwise, the indices buffer may be bound as 
 * GL_ELEMENT_ARRAY_BUFFER.
 */
public interface GLGraphicsObject
{
    /**
     * Returns the {@link GLDataBuffer} for the indices. This may be 
     * <code>null</code> for non-indexed geometry.
     * 
     * @return The {@link GLDataBuffer} for the indices
     */
    GLDataBuffer getIndicesGLDataBuffer();
    
    /**
     * Returns the number of vertices that this object consists of
     * 
     * @return The number of vertices
     */
    int getNumVertices();
    
    /**
     * Returns the {@link GLDataBuffer} for the given {@link Attribute}
     * 
     * @param attribute The {@link Attribute}
     * @return The {@link GLDataBuffer} for the {@link Attribute}
     */
    GLDataBuffer getGLDataBuffer(Attribute attribute);
}
