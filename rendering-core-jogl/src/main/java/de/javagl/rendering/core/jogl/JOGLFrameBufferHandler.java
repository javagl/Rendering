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


import static com.jogamp.opengl.GL.GL_CLAMP_TO_EDGE;
import static com.jogamp.opengl.GL.GL_COLOR_ATTACHMENT0;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_ATTACHMENT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_FRAMEBUFFER;
import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_RENDERBUFFER;
import static com.jogamp.opengl.GL.GL_RGBA;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_S;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_T;
import static com.jogamp.opengl.GL2ES2.GL_DEPTH_COMPONENT;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;

import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLFrameBuffer;
import de.javagl.rendering.core.gl.GLTexture;
import de.javagl.rendering.core.gl.GLTextureFormat;
import de.javagl.rendering.core.handling.AbstractFrameBufferHandler;
import de.javagl.rendering.core.handling.FrameBufferHandler;
import de.javagl.rendering.core.utils.BufferUtils;

/**
 * Implementation of a {@link FrameBufferHandler} using JOGL.
 */
class JOGLFrameBufferHandler 
    extends AbstractFrameBufferHandler<GLFrameBuffer> 
    implements FrameBufferHandler<GLFrameBuffer>
{
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * Backup of the viewport
     */
    private final IntBuffer viewport = 
        BufferUtils.createIntBuffer(16);
    
    /**
     * Set the current GL instance
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        this.gl = gl;
    }
    
    @Override
    protected GLFrameBuffer handleInternal(FrameBuffer frameBuffer)
    {
        int width = frameBuffer.getWidth();
        int height = frameBuffer.getHeight();
        
        // Generate and bind the FBO 
        int fboArray[] = {0};
        gl.glGenFramebuffers(1, fboArray, 0);
        int fbo = fboArray[0];
        gl.glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        
        // Create a depth render-buffer
        int depthBufferArray[] = {0};
        gl.glGenRenderbuffers(1, depthBufferArray, 0);
        int depthBuffer = depthBufferArray[0];
        gl.glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        gl.glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        gl.glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
        
        // Create a color render-buffer
        int colorBufferArray[] = {0};
        gl.glGenRenderbuffers(1, colorBufferArray, 0);
        int colorBuffer = colorBufferArray[0];
        gl.glBindRenderbuffer(GL_RENDERBUFFER, colorBuffer);
        gl.glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA, width, height);
        gl.glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, colorBuffer);
        
        // Create a texture
        int textureArray[] = {0};
        gl.glGenTextures(1, textureArray, 0);
        GLFrameBuffer glFrameBuffer = DefaultGL.createGLFrameBuffer(fbo, depthBuffer, colorBuffer, textureArray[0]);
        
        // Initialize the texture
        gl.glBindTexture(GL_TEXTURE_2D, textureArray[0]);
        GLTexture glTexture = glFrameBuffer.getGLTexture();
        GLTextureFormat glTextureFormat = glTexture.getGLTextureFormat();
        gl.glTexImage2D(GL_TEXTURE_2D, 0, glTextureFormat.getInternalFormat(), width, 
            height,0, glTextureFormat.getFormat() , glTextureFormat.getType() , (ByteBuffer)null);
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        
        // Attach the texture to the FBO
        gl.glFramebufferTexture2D(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureArray[0], 0);

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
        gl.glBindFramebuffer(GL_FRAMEBUFFER, 0);
        
        return glFrameBuffer;
    }
    
    

    @Override
    public void releaseInternal(FrameBuffer frameBuffer, GLFrameBuffer glFrameBuffer)
    {
        int fbo[] = { glFrameBuffer.getFBO() };
        gl.glDeleteFramebuffers(1,fbo,0);
        int depthBuffer[] = { glFrameBuffer.getDepthBuffer() };
        gl.glDeleteRenderbuffers(1, depthBuffer, 0);
        int colorBuffer[] = { glFrameBuffer.getColorBuffer() };
        gl.glDeleteRenderbuffers(1, colorBuffer, 0);
        GLTexture glTexture = glFrameBuffer.getGLTexture();
        int texture[] = { glTexture.getTexture() };
        gl.glDeleteTextures(1, texture, 0);
    }

    @Override
    public void setFrameBufferActive(FrameBuffer frameBuffer)
    {
        if (frameBuffer != null)
        {
            GLFrameBuffer glFrameBuffer = getInternal(frameBuffer);
            gl.glBindFramebuffer(GL_FRAMEBUFFER, glFrameBuffer.getFBO());
            gl.glGetIntegerv(GL3.GL_VIEWPORT, viewport);
            gl.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
            gl.glClearColor(0,0,1,0);
            gl.glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        }
        else
        {
            gl.glBindFramebuffer(GL_FRAMEBUFFER, 0);
            gl.glViewport(
                viewport.get(0), viewport.get(1), 
                viewport.get(2), viewport.get(3));
        }
    }


}
