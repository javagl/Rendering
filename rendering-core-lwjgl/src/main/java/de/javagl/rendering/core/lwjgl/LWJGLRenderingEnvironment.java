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
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Canvas;
import java.awt.Component;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

import de.javagl.rendering.core.Command;
import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.Renderer;
import de.javagl.rendering.core.RenderingEnvironment;
import de.javagl.rendering.core.RenderingException;
import de.javagl.rendering.core.gl.GLRenderer;
import de.javagl.rendering.core.handling.AbstractRenderingEnvironment;

/**
 * Implementation of a {@link RenderingEnvironment} using LWJGL.
 */
public class LWJGLRenderingEnvironment 
    extends AbstractRenderingEnvironment<Component> 
    implements RenderingEnvironment<Component>
{
    /**
     * The AWTGLCanvas, i.e. the rendering component of this renderer
     */
    private Canvas canvas;

    /**
     * The renderer used in this environment
     */
    private final LWJGLRenderer renderer;
    
    /**
     * Creates a new LWJGLRenderingEnvironment
     */
    public LWJGLRenderingEnvironment()
    {
        this.renderer = new LWJGLRenderer();
        initializeComponent();
        initInternal();
    }
    
    /**
     * Initialize the rendering component
     */
    @SuppressWarnings("serial")
    private void initializeComponent()
    {
        if (canvas != null)
        {
            return;
        }
        
        try
        {
            canvas = new AWTGLCanvas()
            {
                @Override
                public void paintGL()
                {
                    render();
                    try
                    {
                        swapBuffers();
                    }
                    catch (LWJGLException e)
                    {
                        throw new RenderingException(
                            "Could not swap buffers", e);
                    }
                }
            };
        }
        catch (LWJGLException e)
        {
            throw new RenderingException(
                "Could not create canvas", e);
        }
    }
    
    
    @Override
    protected int getRenderComponentWidth()
    {
        return canvas.getWidth();
    }

    @Override
    protected int getRenderComponentHeight()
    {
        return canvas.getHeight();
    }
    
    @Override
    public GLRenderer getRenderer()
    {
        return renderer;
    }
    
    
    @Override
    public Component getRenderComponent()
    {
        return canvas;
    }
    
    
    @Override
    public void triggerRendering()
    {
        canvas.repaint();
    }


    
    
    
    
    @Override
    protected void preRender()
    {
        super.preRender();
        int width = getRenderComponent().getWidth();
        int height = getRenderComponent().getHeight();
        glViewport(0, 0, width, height);
        glClearColor(0,0,0,0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
    }
    
    
    // XXX TODO Could be in AbstractGLRenderingEnvironment
//    @Override
//    public void enableFrameBufferTexture(
//        Program program, String programInputName, FrameBuffer frameBuffer)
//    {
//        GLTexture glTexture = 
//            getRenderer().getFrameBufferHandler().getInternal(frameBuffer).getGLTexture();
//        getRenderer().getRenderedObjectHandler().enableTexture(program, programInputName, glTexture);
//    }

    // XXX TODO Could be in AbstractGLRenderingEnvironment
    @Override
    public void handleFrameBuffer(final FrameBuffer frameBuffer)
    {
        addTask(wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                getRenderer().getFrameBufferHandler().handle(frameBuffer);
            }
        }));
    }
    
    // XXX TODO Could be in AbstractGLRenderingEnvironment
    @Override
    public void releaseFrameBuffer(final FrameBuffer frameBuffer)
    {
        addTask(wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                getRenderer().getFrameBufferHandler().release(frameBuffer);
            }
        }));
    }

 
    
    
//    private final IntBuffer m = BufferUtils.createIntBuffer(16);
//    @SuppressWarnings("unused")
//    private void memInfo()
//    {
//        glGetInteger(0x9049, m);
//        int available = m.get(0);
//        glGetInteger(0x904B, m);
//        int evicted = m.get(0);
//        System.out.println("Available "+available+" evicted "+evicted);
//    }
//
//    @Override
//    public void postRender()
//    {
//        //memInfo();
//    }
    
    
}