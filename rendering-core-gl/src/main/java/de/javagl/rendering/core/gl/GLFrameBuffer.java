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
 * Interface describing an OpenGL Frame Buffer Object (FBO)
 */
public interface GLFrameBuffer
{
    /**
     * Returns the FBO identifier
     * 
     * @return The FBO identifier
     */
    int getFBO();

    /**
     * Returns the depth buffer ID of this FBO
     * 
     * @return The depth buffer ID of this FBO
     */
    int getDepthBuffer();

    /**
     * Returns the color buffer ID of this FBO
     * 
     * @return The color buffer ID of this FBO
     */
    int getColorBuffer();

    /**
     * Returns the {@link GLTexture} that is backed by this FBO
     * 
     * @return The {@link GLTexture}
     */
    GLTexture getGLTexture();

}