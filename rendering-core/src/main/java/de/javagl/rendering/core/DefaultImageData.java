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

import java.nio.ByteBuffer;

import de.javagl.rendering.core.utils.BufferUtils;

/**
 * Default implementation of an {@link ImageData}
 */
class DefaultImageData implements ImageData
{
    /**
     * The actual data
     */
    private final ByteBuffer data;
    
    /**
     * The width of the image
     */
    private final int width;
    
    /**
     * The height of the image
     */
    private final int height;
    
    /**
     * The {@link ImageDataType}
     */
    private final ImageDataType type;
    
    /**
     * Creates a new ImageData with a default {@link ImageDataType}, for an 
     * image with the specified size
     * 
     * @param width The width of the image
     * @param height The height of the image
     */
    DefaultImageData(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.data = BufferUtils.createByteBuffer(width * height * 4);
        this.type = ImageDataType.INT_ARGB;
    }
    
    @Override
    public ByteBuffer getData()
    {
        return data.slice();
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public ImageDataType getType()
    {
        return type;
    }
    
    @Override
    public String toString()
    {
        return "DefaultImageData[" +
        		"width=" + width + "," +
        		"height=" + height + "," +
        		"data=" + data + "]";
    }

    
    /* 
     * Implementations of equals and hashCode. Note that these implementations
     * only consider the *references* of some of the fields! 
     */
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : 
            System.identityHashCode(data));
        result = prime * result + height;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (getClass() != object.getClass())
        {
            return false;
        }
        DefaultImageData other = (DefaultImageData) object;
        if (data == null)
        {
            if (other.data != null)
            {
                return false;
            }
        } 
        else if (data != other.data)
        {
            return false;
        }
        if (height != other.height)
        {
            return false;
        }
        if (type != other.type)
        {
            return false;
        }
        if (width != other.width)
        {
            return false;
        }
        return true;
    }
    
    
}
