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


/**
 * Default implementation of a {@link FrameBuffer}.
 */
class DefaultFrameBuffer implements FrameBuffer
{
    /**
     * The width of the frame buffer
     */
    private final int width;

    /**
     * The height of the frame buffer
     */
    private final int height;
    
    /**
     * Creates a new frame buffer with the specified size
     * 
     * @param width The width of the frame buffer
     * @param height The height of the frame buffer
     */
    DefaultFrameBuffer(int width, int height)
    {
        this.width = width;
        this.height = height;
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
    public String toString()
    {
        return "DefaultFrameBuffer["+
            "0x"+Integer.toHexString(System.identityHashCode(this))+","+
            "width="+width+","+
            "height="+height+"]";
    }
    
    // Note: This class uses the default implementations of hashCode and equals 
}

