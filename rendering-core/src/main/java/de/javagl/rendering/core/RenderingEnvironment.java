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

package de.javagl.rendering.core;

import java.util.List;
import java.util.function.Supplier;

import de.javagl.rendering.core.view.View;


/**
 * General interface for a rendering environment. This interface describes
 * the facilities necessary for rendering a scene on a component.
 *  
 * @param <T> The component type 
 */
public interface RenderingEnvironment<T> 
{
    /**
     * Return the {@link Renderer}
     * 
     * @return The {@link Renderer}
     */
    Renderer getRenderer();
    
    /**
     * Returns the component to which this rendering environment will render
     * 
     * @return The rendering component
     */
    T getRenderComponent();

    /**
     * Returns the current {@link View} used by this rendering environment
     * 
     * @return The current {@link View}
     */
    View getView();
    
    /**
     * Add the given supplier of {@link Command}s to this
     * rendering environment. In each rendering pass, the suppliers will
     * be queried for lists of commands, and all commands will be 
     * executed on the {@link Renderer} of this rendering environment.
     * 
     * @param commandSupplier The rendering command supplier to add
     */
    void addCommandSupplier(
        Supplier<? extends List<? extends Command>> commandSupplier);

    /**
     * Remove the given supplier of {@link Command}s
     * 
     * @param commandSupplier The rendering command supplier to remove
     */
    void removeCommandSupplier(
        Supplier<? extends List<? extends Command>> commandSupplier);
    
    /**
     * Add a {@link Command} that should be executed exactly once on the 
     * rendering thread, at the beginning of the next rendering pass.
     * 
     * @param command The {@link Command}
     */
    void addTask(Command command);

    
    /**
     * Triggers a rendering pass.
     */
    void triggerRendering();

    /**
     * Schedules a task to handle the given {@link RenderedObject}. This 
     * method may be called from any thread, and makes sure that the 
     * object is handled before the next rendering happens.
     * 
     * @param renderedObject The {@link RenderedObject} to handle
     */
    void handleRenderedObject(RenderedObject renderedObject);
    
    /**
     * Schedules the task to release the given {@link RenderedObject}. This 
     * method may be called from any thread, and makes sure that the 
     * object is released before the next rendering happens.
     * 
     * @param renderedObject The {@link RenderedObject} to release
     */
    void releaseRenderedObject(RenderedObject renderedObject);
    
    
    
    
    /**
     * Schedules the task to handle the given {@link FrameBuffer}. This 
     * method may be called from any thread, and makes sure that the 
     * object is handled before the next rendering happens.
     * 
     * @param frameBuffer The {@link FrameBuffer} to handle
     */
    void handleFrameBuffer(FrameBuffer frameBuffer);

    /**
     * Schedules the task to release the given {@link FrameBuffer}. This 
     * method may be called from any thread, and makes sure that the 
     * object is released before the next rendering happens.
     * 
     * @param frameBuffer The {@link FrameBuffer} to release
     */
    void releaseFrameBuffer(FrameBuffer frameBuffer);
    
    
    
}