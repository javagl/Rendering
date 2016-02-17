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
 * Default implementation of a {@link GLTexture}
 */
class DefaultGLTexture implements GLTexture
{
    /**
     * The texture ID
     */
    private final int texture;
    
    /**
     * The texture buffer object
     */
    private final int texturePBO;
    
    /**
     * The texture format
     */
    private final GLTextureFormat glTextureFormat;
    
    /**
     * Creates a new DefaultGLTexture with the given
     * parameters.
     * 
     * @param texture The texture ID
     * @param texturePBO The texture PBO
     * @param glTextureFormat The texture format
     */
    DefaultGLTexture(
        int texture, int texturePBO, 
        GLTextureFormat glTextureFormat)
    {
        this.texture = texture;
        this.texturePBO = texturePBO;
        this.glTextureFormat = glTextureFormat;
    }

    @Override
    public int getTexture()
    {
        return texture;
    }
    @Override
    public int getTexturePBO()
    {
        return texturePBO;
    }
    
    @Override
    public GLTextureFormat getGLTextureFormat()
    {
        return glTextureFormat;
    }
    
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((glTextureFormat == null) ? 0 : glTextureFormat.hashCode());
        result = prime * result + texture;
        result = prime * result + texturePBO;
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
        DefaultGLTexture other = (DefaultGLTexture)obj;
        if (glTextureFormat == null)
        {
            if (other.glTextureFormat != null)
                return false;
        }
        else if (!glTextureFormat.equals(other.glTextureFormat))
            return false;
        if (texture != other.texture)
            return false;
        if (texturePBO != other.texturePBO)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "DefaultGLTexture["+
            "texture="+texture+","+
            "texturePBO="+texturePBO+","+
            "glTextureFormat="+glTextureFormat+"]";
    }
    
}
