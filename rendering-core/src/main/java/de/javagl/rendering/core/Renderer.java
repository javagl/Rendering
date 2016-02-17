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

import de.javagl.rendering.core.handling.DataBufferHandler;
import de.javagl.rendering.core.handling.FrameBufferHandler;
import de.javagl.rendering.core.handling.GraphicsObjectHandler;
import de.javagl.rendering.core.handling.Handler;
import de.javagl.rendering.core.handling.ProgramHandler;
import de.javagl.rendering.core.handling.RenderedObjectHandler;
import de.javagl.rendering.core.handling.TextureHandler;

/**
 * General interface for a Renderer. <br> 
 * <br>
 * A Renderer offers a set of specific {@link Handler} instances
 * for different classes associated with rendering:
 * <ul>
 *   <li> A {@link RenderedObjectHandler} for 
 *          {@link RenderedObject} instances</li>
 *   <li> A {@link ProgramHandler} for 
 *          {@link Program} instances</li>
 *   <li> A {@link GraphicsObjectHandler} for 
 *          {@link GraphicsObject} instances</li>
 *   <li> A {@link DataBufferHandler} for 
 *          {@link DataBuffer} instances</li>
 *   <li> A {@link TextureHandler} for 
 *          {@link Texture} instances</li>
 *   <li> A {@link FrameBufferHandler} for 
 *          {@link FrameBuffer} instances</li>
 * </ul>
 * Each of these handlers offer method for handling instances of the
 * respective classes, and for performing operations on these instances.
 * For example, the {@link ProgramHandler} allows setting the input
 * variables for a {@link Program}, and the {@link DataBufferHandler}
 * allows updating a modified region of a {@link DataBuffer}.
 */
public interface Renderer
{
    /**
     * Returns the {@link RenderedObjectHandler}
     * 
     * @return The {@link RenderedObjectHandler}
     */
    RenderedObjectHandler<?> getRenderedObjectHandler();
    
    /**
     * Returns the {@link ProgramHandler}
     * 
     * @return The {@link ProgramHandler}
     */
    ProgramHandler<?> getProgramHandler();
    
    /**
     * Returns the {@link GraphicsObjectHandler}
     * 
     * @return The {@link GraphicsObjectHandler}
     */
    GraphicsObjectHandler<?> getGraphicsObjectHandler();
    
    /**
     * Returns the {@link DataBufferHandler}
     * 
     * @return The {@link DataBufferHandler}
     */
    DataBufferHandler<?> getDataBufferHandler();
    
    /**
     * Returns the {@link TextureHandler}
     * 
     * @return The {@link TextureHandler}
     */
    TextureHandler<?> getTextureHandler();
    
    /**
     * Returns the {@link FrameBufferHandler}
     * 
     * @return The {@link FrameBufferHandler}
     */
    FrameBufferHandler<?> getFrameBufferHandler();
    
}
