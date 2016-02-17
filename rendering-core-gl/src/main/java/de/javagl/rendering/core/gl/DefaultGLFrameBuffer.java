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
 * Default implementation of a {@link GLFrameBuffer}
 */
class DefaultGLFrameBuffer implements GLFrameBuffer
{
    /**
     * The FBO identifier
     */
    private final int fbo;

    /**
     * The depth buffer identifier
     */
    private final int depthBuffer;
    
    /**
     * The color buffer identifier
     */
    private final int colorBuffer;
    
    /**
     * The GL texture that is backed by this FBO
     */
    private final GLTexture glTexture;
    
    /**
     * Creates a new DefaultGLFrameBuffer
     * 
     * @param fbo The FBO identifier
     * @param depthBuffer The depth buffer identifier
     * @param colorBuffer The color buffer identifier
     * @param texture The GL texture that is backed by this FBO
     */
    DefaultGLFrameBuffer(
        int fbo, int depthBuffer, int colorBuffer, int texture)
    {
        this.fbo = fbo;
        this.depthBuffer = depthBuffer;
        this.colorBuffer = colorBuffer;
        GLTextureFormat glTextureFormat = 
            DefaultGL.createGLTextureFormat(
                GLConstants.GL_RGBA, 
                GLConstants.GL_RGBA, 
                GLConstants.GL_UNSIGNED_BYTE, 4);
        glTexture = DefaultGL.createGLTexture(texture, 0, glTextureFormat);
    }

    @Override
    public int getFBO()
    {
        return fbo;
    }

    @Override
    public int getDepthBuffer()
    {
        return depthBuffer;
    }

    @Override
    public int getColorBuffer()
    {
        return colorBuffer;
    }

    @Override
    public GLTexture getGLTexture()
    {
        return glTexture;
    }

    @Override
    public String toString()
    {
        return "DefaultGLFrameBuffer[" +
            "fbo=" + fbo + "," +
            "depthBuffer=" + depthBuffer + "," +
            "colorBuffer=" + colorBuffer + "," +
            "glTexture=" + glTexture + "]";
    }
}