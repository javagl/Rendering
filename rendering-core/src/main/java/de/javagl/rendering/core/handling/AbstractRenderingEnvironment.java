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


package de.javagl.rendering.core.handling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import de.javagl.rendering.core.Command;
import de.javagl.rendering.core.RenderedObject;
import de.javagl.rendering.core.Renderer;
import de.javagl.rendering.core.RenderingEnvironment;
import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.core.view.CameraListener;
import de.javagl.rendering.core.view.Rectangle;
import de.javagl.rendering.core.view.Rectangles;
import de.javagl.rendering.core.view.View;
import de.javagl.rendering.core.view.Views;

/**
 * Abstract implementation of a {@link RenderingEnvironment}
 * 
 * @param <T> The component type
 */
public abstract class AbstractRenderingEnvironment<T>
    implements RenderingEnvironment<T>
{
    /**
     * Compile-time flag which indicates whether the stack
     * traces of tasks submitted to this rendering environment should
     * be preserved and printed when a task causes an
     * exception.
     */
    private static boolean PRESERVE_STACK_TRACES = true;
    
    /**
     * The list of supplier instances for {@link Command} lists
     */
    private final List<Supplier<? extends List<? extends Command>>> 
        commandSuppliers;
    
    /**
     * The {@link View} that is used in this rendering environment
     */
    private final View view;

    /**
     * One-shot tasks that should be executed before
     * the next rendering pass, on the rendering thread.
     */
    private final List<Command> tasks;
    
    /**
     * Creates a new AbstractRenderingEnvironment
     */
    protected AbstractRenderingEnvironment()
    {
        this.commandSuppliers = 
            new CopyOnWriteArrayList<Supplier<
                ? extends List<? extends Command>>>();
        this.view = Views.create();
        this.tasks = Collections.synchronizedList(new ArrayList<Command>());
    }
    
    /**
     * Returns the width of the render component
     * 
     * @return The width of the render component
     */
    protected abstract int getRenderComponentWidth();

    /**
     * Returns the height of the render component
     * 
     * @return The height of the render component
     */
    protected abstract int getRenderComponentHeight();
    
    /**
     * Internal initialization, to be called after the constructors
     * of the derived classes have completed.
     */
    protected final void initInternal()
    {
        getView().getCamera().addCameraListener(new CameraListener()
        {
            @Override
            public void cameraChanged(Camera camera)
            {
                triggerRendering();
            }
        });
    }
    
    
    @Override
    public final View getView()
    {
        return view;
    }
    
    
    @Override
    public final void addTask(Command command)
    {
        tasks.add(command);
    }
    
    
    @Override
    public final void addCommandSupplier(
        Supplier<? extends List<? extends Command>> commandSupplier)
    {
        this.commandSuppliers.add(commandSupplier);
    }
    
    @Override
    public final void removeCommandSupplier(
        Supplier<? extends List<? extends Command>> commandSupplier)
    {
        this.commandSuppliers.remove(commandSupplier);
    }
    
    
    /**
     * This method will be executed before rendering. 
     * The default implementation is empty.
     */
    protected void preRender()
    {
        // Empty, may be overridden
    }

    /**
     * This method will be executed after rendering. 
     * The default implementation is empty.
     */
    protected void postRender()
    {
        // Empty, may be overridden
    }
    
    
    /**
     * Renders. This will consist of
     * <ul>
     *   <li>calling {@link #preRender()} </li>
     *   <li>Updating the view</li>
     *   <li>Executing all tasks that had been 
     *       added with {@link #addTask(Command)}</li>
     *   <li>Obtaining and executing all Commands from the Command supplier</li> 
     *   <li>calling {@link #postRender()} </li>
     * </ul>
     * 
     */
    protected void render() 
    {
        //System.out.println("Rendering pass");
        
        preRender();
        
        int w = getRenderComponentWidth();
        int h = getRenderComponentHeight();

        Rectangle viewport = Rectangles.create(0, 0, w, h);
        getView().setViewport(viewport);
        
        float aspect = (float)w/h;
        getView().setAspect(aspect);
        
        synchronized (tasks)
        {
            while (tasks.size() > 0)
            {
                Command command = tasks.get(0);
                command.execute(getRenderer());
                tasks.remove(0);
            }
        }
        
        for (Supplier<? extends List<? extends Command>> commandSupplier : 
            commandSuppliers)
        {
            List<? extends Command> commands = commandSupplier.get();
            for (int i=0; i<commands.size(); i++)
            {
                Command command = commands.get(i);
                command.execute(getRenderer());
            }
        }
        
        postRender();
        
        //System.out.println("Rendering pass DONE");
    }

    @Override
    public void handleRenderedObject(final RenderedObject renderedObject)
    {
        addTask(wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getRenderedObjectHandler().handle(renderedObject);
            }
        }));
    }
    
    @Override
    public void releaseRenderedObject(final RenderedObject renderedObject)
    {
        addTask(wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getRenderedObjectHandler().release(renderedObject);
            }
        }));
    }
    
    
    
    /**
     * When {@link #PRESERVE_STACK_TRACES} is <code>true</code>, this method 
     * wraps the given Command into one that preserves the stack trace of 
     * this method call, and, if the command causes an exception, prints 
     * it together with the stack trace of the exception caused by the 
     * command. <br> 
     * <br>
     * When {@link #PRESERVE_STACK_TRACES} is <code>false</code>, the given
     * command is returned.
     * 
     * @param command The command to wrap.
     * @return The wrapped command or the command
     */
    protected static Command wrap(final Command command)
    {
        if (!PRESERVE_STACK_TRACES)
        {
            return command;
        }
        
        final StackTraceElement ste[] = Thread.currentThread().getStackTrace();
        return new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                try
                {
                    command.execute(renderer);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.err.println("Scheduled at");
                    for (StackTraceElement s : ste)
                    {
                        System.err.println("\tat "+s);
                    }
                }
            }
            
            @Override
            public String toString()
            {
                return command.toString();
            }
        };
    }
    
    
}
