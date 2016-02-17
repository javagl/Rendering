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

package de.javagl.rendering.core.lwjgl;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL21.GL_PIXEL_UNPACK_BUFFER;

import java.nio.ByteBuffer;

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
 * Implementation of a {@link TextureHandler} using LWJGL
 */
class LWJGLTextureHandler 
    extends AbstractTextureHandler<GLTexture> 
    implements TextureHandler<GLTexture>
{
    /**
     * The {@link FrameBufferHandler}
     */
    private final LWJGLFrameBufferHandler frameBufferHandler;
    
    /**
     * Creates a new LWJGLTextureHandler
     */
    LWJGLTextureHandler()
    {
        this.frameBufferHandler = new LWJGLFrameBufferHandler();
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
            glDeleteTextures(glTexture.getTexture());
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
        
        int textureID = glGenTextures();
        int pbo = glGenBuffers();

        GLTextureFormat glTextureFormat = 
            TextureFormatUtils.createGLTextureFormat(imageTexture);
        GLTexture glTexture = 
            DefaultGL.createGLTexture(
                textureID, pbo, glTextureFormat);
        
        glBindBuffer(GL_PIXEL_UNPACK_BUFFER, glTexture.getTexturePBO());
        glBufferData(GL_PIXEL_UNPACK_BUFFER, 
            imageTexture.getWidth() * 
            imageTexture.getHeight() * 
            glTextureFormat.getElements(),
            GL_STREAM_DRAW);

        glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); 
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); 
        
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
        
        executeTextureUpdate(glTexture, imageTexture);
        
        return glTexture;
    }

    @Override
    public void updateImageTexture(
        ImageTexture imageTexture, int x, int y, int w, int h)
    {
        //System.out.println("Update texture "+x+" "+y+" "+w+" "+h);

        GLTexture glTexture = getInternal(imageTexture);
        executeTextureUpdate(glTexture, imageTexture);
    }

    @Override
    public void updateImageTexture(ImageTexture imageTexture)
    {
        GLTexture glTexture = getInternal(imageTexture);
        executeTextureUpdate(glTexture, imageTexture);
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
        // TODO: Use dirtyRectangle to update only the necessary part

        GLTextureFormat glTextureFormat = glTexture.getGLTextureFormat();
        
        glBindBuffer(GL_PIXEL_UNPACK_BUFFER, glTexture.getTexturePBO());
        glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());

        ByteBuffer mappedPBOBuffer = 
            glMapBuffer(GL_PIXEL_UNPACK_BUFFER, GL_READ_WRITE, null);

        
        //System.out.println("Update texture "+glTexture);
        mappedPBOBuffer.put(imageTexture.getImageData().getData());
        //System.out.println("Update texture "+glTexture+" done");
        
        glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER);

        ImageData imageData = imageTexture.getImageData();
        int w = imageData.getWidth();
        int h = imageData.getHeight();
        glTexImage2D(
            GL_TEXTURE_2D, 0, glTextureFormat.getInternalFormat(), w, h, 
            0, glTextureFormat.getFormat(), glTextureFormat.getType(), 0);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);

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
