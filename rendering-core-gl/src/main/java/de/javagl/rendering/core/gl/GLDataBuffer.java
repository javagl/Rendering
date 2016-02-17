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

/**
 * Interface for OpenGL data buffers (Vertex Buffer Objects)
 */
public interface GLDataBuffer
{
    /**
     * Returns the VBO id which may be bound as a GL_ARRAY_BUFFER
     * 
     * @return The VBO id
     */
    int getVBO();
    
    /**
     * The type of the data buffer (GL_BYTE, GL_INT, GL_FLOAT...)
     * 
     * @return The type of the data buffer
     */
    int getType();
    
    /**
     * Return the size of the buffer, in number of elements
     * 
     * @return The number of elements in the buffer
     */
    int getSize();
    
    /**
     * Returns the offset of the data buffer, in bytes, which is used
     * for initializing the vertex attribute pointer with
     * glVertexAttribPointer.
     * 
     * @return The offset
     */
    int getOffset();
    
    /**
     * Returns the stride between two consecutive elements of the data 
     * buffer, in bytes, which is used for initializing the vertex 
     * attribute pointer with glVertexAttribPointer. 
     * 
     * @return The stride
     */
    int getStride();
    
}
