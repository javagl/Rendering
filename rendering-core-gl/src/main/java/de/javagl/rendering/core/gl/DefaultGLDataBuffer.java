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
 * Default implementation of a {@link GLDataBuffer}
 */
class DefaultGLDataBuffer implements GLDataBuffer
{
    /**
     * The VBO ID
     */
    private final int vbo;
    
    /**
     * The type
     */
    private final int type;

    /**
     * The size, in number of elements
     */
    private final int size;

    /**
     * The offset, in bytes
     */
    private int offset;
    
    /**
     * The stride between two elements, in bytes
     */
    private int stride;
    
    /**
     * Creates a new DefaultGLDataBuffer
     * 
     * @param vbo The VBO ID
     * @param type The type
     * @param size The size, in number of elements
     * @param offset The offset, in bytes
     * @param stride The stride between two elements, in bytes
     */
    DefaultGLDataBuffer(int vbo, int type, int size, int offset, int stride)
    {
        this.vbo = vbo;
        this.type = type;
        this.size = size;
        this.offset = offset;
        this.stride = stride;
    }
    
    @Override
    public int getVBO()
    {
        return vbo;
    }

    @Override
    public int getType()
    {
        return type;
    }

    @Override
    public int getSize()
    {
        return size;
    }
    
    @Override
    public int getOffset()
    {
        return offset;
    }
    
    @Override
    public int getStride()
    {
        return stride;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + size;
        result = prime * result + type;
        result = prime * result + vbo;
        result = prime * result + offset;
        result = prime * result + stride;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultGLDataBuffer other = (DefaultGLDataBuffer)obj;
        if (size != other.size)
            return false;
        if (type != other.type)
            return false;
        if (vbo != other.vbo)
            return false;
        if (offset != other.offset)
            return false;
        if (stride != other.stride)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "DefaultGLDataBuffer[" + 
            "vbo=" + vbo + "," + 
            "type=" + type + "," + 
            "size=" + size + "," + 
            "offset=" + offset + "," + 
            "stride=" + stride + "]";
    }


}
