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

package de.javagl.rendering.core.gl.util;

import de.javagl.rendering.core.ImageTexture;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLTextureFormat;

/**
 * Utility methods for texture format handling
 */
public class TextureFormatUtils
{
    /**
     * Create the {@link GLTextureFormat} for the given {@link ImageTexture}.
     * 
     * @param texture The {@link ImageTexture}
     * @return The {@link GLTextureFormat}
     */
    public static GLTextureFormat createGLTextureFormat(ImageTexture texture)
    {
        int format = 0;
        int internalFormat = 0;
        int type = 0;
        int elements = 0;
        
        switch (texture.getImageData().getType())
        {
            case INT_ARGB:
                type = GLConstants.GL_UNSIGNED_BYTE;
                internalFormat = GLConstants.GL_RGBA;
                format = GLConstants.GL_BGRA; // TODO: Is BGRA correct here?
                elements = 4;
                break;

            default:
                throw new IllegalArgumentException(
                    "Invalid image format. Currently only " +
                    "INT_ARGB is supported.");
        }

        GLTextureFormat glTextureFormat = 
            DefaultGL.createGLTextureFormat(format, internalFormat, type, elements);
        return glTextureFormat;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private TextureFormatUtils()
    {
        // Private constructor to prevent instantiation
    }
    
}
