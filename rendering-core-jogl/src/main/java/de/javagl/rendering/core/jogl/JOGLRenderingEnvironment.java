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

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

import java.awt.Component;
import java.util.logging.Logger;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import de.javagl.rendering.core.Command;
import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.Renderer;
import de.javagl.rendering.core.RenderingEnvironment;
import de.javagl.rendering.core.gl.GLRenderer;
import de.javagl.rendering.core.handling.AbstractRenderingEnvironment;

/**
 * Implementation of a {@link RenderingEnvironment} using JOGL
 */
public class JOGLRenderingEnvironment 
    extends AbstractRenderingEnvironment<Component> 
    implements RenderingEnvironment<Component>
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(JOGLRenderingEnvironment.class.getName());
    
    /**
     * The GLCanvas, i.e. the rendering component of this renderer
     */
    private GLCanvas glComponent;

    /**
     * The renderer used in this environment
     */
    private final JOGLRenderer renderer;
    
    /**
     * Creates a new JOGLRenderingEnvironment
     */
    public JOGLRenderingEnvironment()
    {
        this.renderer = new JOGLRenderer();
        initializeComponent();
        initInternal();
    }
    
    /**
     * Initialize the rendering component
     */
    private void initializeComponent()
    {
        if (glComponent != null)
        {
            return;
        }
        GLProfile profile = GLProfile.getMaxProgrammable(true);
        logger.config("GLProfile: " + profile);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);
        glComponent = new GLCanvas(capabilities);
        glComponent.addGLEventListener(glEventListener);
    }
    
    
    @Override
    protected int getRenderComponentWidth()
    {
        return glComponent.getWidth();
    }

    @Override
    protected int getRenderComponentHeight()
    {
        return glComponent.getHeight();
    }
    
    @Override
    public GLRenderer getRenderer()
    {
        return renderer;
    }
    
    /**
     * The event listener that will be attached to the canvas
     */
    private final GLEventListener glEventListener = new GLEventListener()
    {
        private boolean initComplete;

        @Override
        public void init(GLAutoDrawable drawable)
        {
            initComplete = false;

            GL3 gl = (GL3)drawable.getGL();
            gl.setSwapInterval(0);
            initComplete = true;
        }
        
        
        @Override
        public void display(GLAutoDrawable drawable) 
        {
            if (!initComplete)
            {
                return;
            }
            render();
        }
        
        @Override
        public void reshape(
            GLAutoDrawable drawable, int x, int y, int width, int height)
        {
            GL3 gl = (GL3)drawable.getGL();
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void dispose(GLAutoDrawable arg0)
        {
            // Nothing to do here
        }
    };
    

    @Override
    public void triggerRendering()
    {
        glComponent.repaint();
    }

    
    @Override
    public Component getRenderComponent()
    {
        return glComponent;
    }


    
    @Override
    protected void preRender()
    {
        super.preRender();

        GL3 gl = glComponent.getGL().getGL3();
        renderer.setGL(gl);
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL3.GL_DEPTH_TEST);
    }
    

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
    
    @Override
    public void releaseFrameBuffer(final FrameBuffer frameBuffer)
    {
        addTask(wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getFrameBufferHandler().release(frameBuffer);
            }
        }));
    }

    
    
}


