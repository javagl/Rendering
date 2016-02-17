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
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLFrameBuffer;
import de.javagl.rendering.core.gl.GLTexture;
import de.javagl.rendering.core.gl.GLTextureFormat;
import de.javagl.rendering.core.handling.AbstractFrameBufferHandler;
import de.javagl.rendering.core.handling.FrameBufferHandler;
import de.javagl.rendering.core.utils.BufferUtils;

/**
 * Implementation of a {@link FrameBufferHandler} using LWJGL.
 */
class LWJGLFrameBufferHandler 
    extends AbstractFrameBufferHandler<GLFrameBuffer> 
    implements FrameBufferHandler<GLFrameBuffer>
{
    /**
     * Backup of the viewport
     */
    private final IntBuffer viewport = 
        BufferUtils.createIntBuffer(16);
    
    @Override
    public GLFrameBuffer handleInternal(FrameBuffer frameBuffer)
    {
        int width = frameBuffer.getWidth();
        int height = frameBuffer.getHeight();
        
        // Generate and bind the FBO 
        int fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        
        // Create a depth render-buffer
        int depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
        
        // Create a color render-buffer
        int colorBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, colorBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, colorBuffer);
        
        // Create a texture
        int texture = glGenTextures(); 
        GLFrameBuffer glFrameBuffer = DefaultGL.createGLFrameBuffer(fbo, depthBuffer, colorBuffer, texture);
        
        // Initialize the texture
        glBindTexture(GL_TEXTURE_2D, texture);
        GLTexture glTexture = glFrameBuffer.getGLTexture();
        GLTextureFormat glTextureFormat = glTexture.getGLTextureFormat();
        glTexImage2D(GL_TEXTURE_2D, 0, glTextureFormat.getInternalFormat(), width, 
            height,0, glTextureFormat.getFormat() , glTextureFormat.getType() , (ByteBuffer)null);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        
        // Attach the texture to the FBO
        glFramebufferTexture2D(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

        /*
        int status =glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (status != GL_FRAMEBUFFER_COMPLETE) 
        {
            System.out.println("Error creating fbo: "+status);
        }
        else
        {
            System.out.println("Created fbo "+fbo);
        }
        //*/
        
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        
        return glFrameBuffer;
    }
    

    @Override
    public void releaseInternal(FrameBuffer frameBuffer, GLFrameBuffer glFrameBuffer)
    {
        glDeleteFramebuffers(glFrameBuffer.getFBO());
        glDeleteRenderbuffers(glFrameBuffer.getDepthBuffer());
        glDeleteRenderbuffers(glFrameBuffer.getColorBuffer());
        GLTexture glTexture = glFrameBuffer.getGLTexture();
        glDeleteTextures(glTexture.getTexture());
    }

    @Override
    public void setFrameBufferActive(FrameBuffer frameBuffer)
    {
        if (frameBuffer != null)
        {
            GLFrameBuffer glFrameBuffer = getInternal(frameBuffer);
            glBindFramebuffer(GL_FRAMEBUFFER, glFrameBuffer.getFBO());
            glGetInteger(GL_VIEWPORT, viewport);
            glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
            glClearColor(0,0,1,0);
            glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        }
        else
        {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            glViewport(
                viewport.get(0), viewport.get(1), 
                viewport.get(2), viewport.get(3));
        }
    }

}
