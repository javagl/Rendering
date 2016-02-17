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

package de.javagl.rendering.core.jogl;
import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_REPEAT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_S;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_T;
import static com.jogamp.opengl.GL2ES2.GL_STREAM_DRAW;
import static com.jogamp.opengl.GL2GL3.GL_PIXEL_UNPACK_BUFFER;
import static com.jogamp.opengl.GL2GL3.GL_READ_WRITE;

import java.nio.ByteBuffer;

import com.jogamp.opengl.GL3;

import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.FrameBufferTexture;
import de.javagl.rendering.core.ImageData;
import de.javagl.rendering.core.ImageTexture;
import de.javagl.rendering.core.Texture;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLFrameBuffer;
import de.javagl.rendering.core.gl.GLTexture;
import de.javagl.rendering.core.gl.GLTextureFormat;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.gl.util.TextureFormatUtils;
import de.javagl.rendering.core.handling.AbstractTextureHandler;
import de.javagl.rendering.core.handling.FrameBufferHandler;
import de.javagl.rendering.core.handling.TextureHandler;


/**
 * Implementation of a {@link TextureHandler} using JOGL.
 */
class JOGLTextureHandler 
    extends AbstractTextureHandler<GLTexture> 
    implements TextureHandler<GLTexture>
{
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * The {@link FrameBufferHandler}
     */
    private final JOGLFrameBufferHandler frameBufferHandler;
    
    /**
     * Creates a new JOGLTextureHandler
     */
    JOGLTextureHandler()
    {
        this.frameBufferHandler = new JOGLFrameBufferHandler();
    }

    /**
     * Set the current GL instance
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        this.gl = gl;
        frameBufferHandler.setGL(gl);
    }
    
    @Override
    public GLTexture handleInternal(Texture texture)
    {
        if (texture instanceof ImageTexture)
        {
            ImageTexture imageTexture = (ImageTexture)texture;
            return handleImageTexture(imageTexture);
        }
        if (texture instanceof FrameBufferTexture)
        {
            FrameBufferTexture frameBufferTexture = 
                (FrameBufferTexture)texture;
            return handleFrameBufferTexture(frameBufferTexture);
        }
        ErrorHandler.handle("Invalid texture type: "+texture.getClass());
        return null;
    }
    
    @Override
    public void releaseInternal(Texture texture, GLTexture glTexture)
    {
        if (texture instanceof ImageTexture)
        {
            int buffer[] = {glTexture.getTexture()};
            gl.glDeleteTextures(1, buffer, 0);
            
            //ImageTexture imageTexture = (ImageTexture)texture;
            //imageTexture.removeImageTextureListener(this);
        }
    }
    
    
    /**
     * Handle the given {@link ImageTexture} and return the 
     * corresponding {@link GLTexture}
     * 
     * @param imageTexture The {@link ImageTexture}
     * @return The {@link GLTexture}
     */
    private GLTexture handleImageTexture(ImageTexture imageTexture)
    {
        //imageTexture.addImageTextureListener(this);
        
        int textureArray[] = {0};
        gl.glGenTextures(1, textureArray, 0);
        
        int pboArray[] = {0};
        gl.glGenBuffers(1, pboArray, 0);

        GLTextureFormat glTextureFormat = 
            TextureFormatUtils.createGLTextureFormat(imageTexture);
        GLTexture glTexture =
            DefaultGL.createGLTexture(
                textureArray[0], pboArray[0], glTextureFormat);
 
        gl.glBindBuffer(GL_PIXEL_UNPACK_BUFFER, glTexture.getTexturePBO());
        gl.glBufferData(GL_PIXEL_UNPACK_BUFFER, 
            imageTexture.getWidth() * 
            imageTexture.getHeight() * 
            glTextureFormat.getElements(),
            null, GL_STREAM_DRAW);

        gl.glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); 
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); 

        gl.glBindTexture(GL_TEXTURE_2D, 0);
        gl.glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
        
        executeTextureUpdate(glTexture, imageTexture);

        return glTexture;
    }


    @Override
    public void updateImageTexture(
        ImageTexture texture, int x, int y, int w, int h)
    {
        GLTexture glTexture = getInternal(texture);
        executeTextureUpdate(glTexture, texture);
    }

    @Override
    public void updateImageTexture(ImageTexture texture)
    {
        GLTexture glTexture = getInternal(texture);
        executeTextureUpdate(glTexture, texture);
    }
    
    /**
     * Execute the update of the given {@link GLTexture} based on 
     * the given {@link ImageTexture}
     * 
     * @param glTexture The {@link GLTexture}
     * @param imageTexture The {@link ImageTexture}
     */
    private void executeTextureUpdate(
        GLTexture glTexture, ImageTexture imageTexture)
    {
        GLTextureFormat glTextureFormat = glTexture.getGLTextureFormat();

        gl.glBindBuffer(GL_PIXEL_UNPACK_BUFFER, glTexture.getTexturePBO());
        gl.glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());
    
        ByteBuffer mappedPBOBuffer = 
            gl.glMapBuffer(GL_PIXEL_UNPACK_BUFFER, GL_READ_WRITE);
        
        //System.out.println("Update texture "+glTexture);
        mappedPBOBuffer.put(imageTexture.getImageData().getData());
        //System.out.println("Update texture "+glTexture+" done");
        
        gl.glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER);

        ImageData imageData = imageTexture.getImageData();
        int w = imageData.getWidth();
        int h = imageData.getHeight();
        gl.glTexImage2D(
            GL_TEXTURE_2D, 0, glTextureFormat.getInternalFormat(), w, h, 
            0, glTextureFormat.getFormat(), glTextureFormat.getType(), 0);
        
        gl.glBindTexture(GL_TEXTURE_2D, 0);
        gl.glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
    }
    

    /**
     * Handle the given {@link FrameBufferTexture} and return 
     * the corresponding {@link GLTexture}
     * 
     * @param frameBufferTexture The {@link FrameBufferTexture}
     * @return The {@link GLTexture}
     */
    private GLTexture handleFrameBufferTexture(
        FrameBufferTexture frameBufferTexture)
    {
        FrameBuffer frameBuffer = frameBufferTexture.getFrameBuffer();
        GLFrameBuffer glFrameBuffer = 
            getFrameBufferHandler().getInternal(frameBuffer);
        return glFrameBuffer.getGLTexture();
    }

    @Override
    public FrameBufferHandler<GLFrameBuffer> getFrameBufferHandler()
    {
        return frameBufferHandler;
    }
    
    
}
