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

import de.javagl.rendering.core.GraphicsObject;
import de.javagl.rendering.core.Mapping;
import de.javagl.rendering.core.Parameter;
import de.javagl.rendering.core.Program;
import de.javagl.rendering.core.RenderedObject;
import de.javagl.rendering.core.Texture;

/**
 * Abstract base implementation of a {@link RenderedObjectHandler}.
 *
 * @param <U> The type of the internal representation of the
 * objects handled by this class  
 */
public abstract class AbstractRenderedObjectHandler<U> 
    extends AbstractReferenceHandler<RenderedObject, U> 
    implements RenderedObjectHandler<U>
{
    @Override
    public final void handleChildren(RenderedObject renderedObject)
    {
        Program program = renderedObject.getProgram();
        getProgramHandler().handle(program);
        
        Mapping<Parameter, Texture> textureMapping = 
            renderedObject.getTextureMapping();
        for (Texture texture : textureMapping.values())
        {
            getTextureHandler().handle(texture);
        }
        
        GraphicsObject graphicsObject = renderedObject.getGraphicsObject();
        getGraphicsObjectHandler().handle(graphicsObject);
    }
    
    @Override
    public final void releaseChildren(RenderedObject renderedObject)
    {
        getProgramHandler().release(renderedObject.getProgram());
        Mapping<Parameter, Texture> textureMapping = 
            renderedObject.getTextureMapping();
        for (Texture texture : textureMapping.values())
        {
            getTextureHandler().release(texture);
        }
        getGraphicsObjectHandler().release(renderedObject.getGraphicsObject());
    }
    

}
