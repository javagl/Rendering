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

import de.javagl.rendering.core.Renderer;
import de.javagl.rendering.core.handling.DataBufferHandler;
import de.javagl.rendering.core.handling.FrameBufferHandler;
import de.javagl.rendering.core.handling.GraphicsObjectHandler;
import de.javagl.rendering.core.handling.ProgramHandler;
import de.javagl.rendering.core.handling.RenderedObjectHandler;
import de.javagl.rendering.core.handling.TextureHandler;

/**
 * The GLRenderer is a specialized {@link Renderer} that returns
 * handlers whose internal representations of the objects are
 * the corresponding GL-objects.
 */
public interface GLRenderer extends Renderer
{
    @Override
    RenderedObjectHandler<GLRenderedObject> getRenderedObjectHandler();
    
    @Override
    ProgramHandler<GLProgram> getProgramHandler();
    
    @Override
    GraphicsObjectHandler<GLGraphicsObject> getGraphicsObjectHandler();
    
    @Override
    DataBufferHandler<GLDataBuffer> getDataBufferHandler();
    
    @Override
    TextureHandler<GLTexture> getTextureHandler();
    
    @Override
    FrameBufferHandler<GLFrameBuffer> getFrameBufferHandler();
}
