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


import de.javagl.rendering.core.gl.util.GLConstants;

/**
 * Default implementation of a {@link GLTextureFormat}
 */
class DefaultGLTextureFormat implements GLTextureFormat
{
    /**
     * The format
     */
    private final int format;
    
    /**
     * The internal format
     */
    private final int internalFormat;
    
    /**
     * The data type
     */
    private final int type;
    
    /**
     * The number of elements of the type
     */
    private final int elements;
    
    /**
     * Creates a new DefaultGLTextureFormat with the given parameters
     * 
     * @param format The format
     * @param internalFormat The internal format
     * @param type The data type
     * @param elements The number of elements of the type
     */
    DefaultGLTextureFormat(
        int format, int internalFormat, int type, int elements)
    {
        this.format = format;
        this.internalFormat = internalFormat;
        this.type = type;
        this.elements = elements;
    }
    
    /**
     * Copy constructor
     * 
     * @param other The GLTextureFormat to copy
     */
    public DefaultGLTextureFormat(GLTextureFormat other)
    {
        this.format = other.getFormat();
        this.internalFormat = other.getInternalFormat();
        this.type = other.getType();
        this.elements = other.getElements();
    }
    
    @Override
    public int getFormat()
    {
        return format;
    }

    @Override
    public int getInternalFormat()
    {
        return internalFormat;
    }

    @Override
    public int getType()
    {
        return type;
    }

    @Override
    public int getElements()
    {
        return elements;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + elements;
        result = prime * result + format;
        result = prime * result + internalFormat;
        result = prime * result + type;
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
        DefaultGLTextureFormat other = (DefaultGLTextureFormat)obj;
        if (elements != other.elements)
            return false;
        if (format != other.format)
            return false;
        if (internalFormat != other.internalFormat)
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    
    
    @Override
    public String toString()
    {
        return "DefaultGLTextureFormat["+
            "format="+GLConstants.stringFor(format)+","+
            "internalFormat="+GLConstants.stringFor(internalFormat)+","+
            "type="+GLConstants.stringFor(type)+"]";
    }

}
