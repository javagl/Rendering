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
 * Methods for creating {@link Texture} instances
 */
public class Textures
{
    /**
     * Creates a new {@link ImageTexture} with the given size.
     * 
     * @param width The width
     * @param height The height
     * @return The {@link Texture}
     */
    public static ImageTexture createImageTexture(int width, int height)
    {
        return new DefaultImageTexture(width, height);
    }
    
    /**
     * Creates a new {@link FrameBufferTexture} for the given 
     * {@link FrameBuffer}
     * 
     * @param frameBuffer The {@link FrameBuffer}
     * @return The {@link FrameBufferTexture}
     */
    public static FrameBufferTexture createFrameBufferTexture(
        FrameBuffer frameBuffer)
    {
        return new DefaultFrameBufferTexture(frameBuffer);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Textures()
    {
        // Private constructor to prevent instantiation
    }
}
