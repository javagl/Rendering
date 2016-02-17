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

import java.util.logging.Logger;

import de.javagl.rendering.core.Attribute;
import de.javagl.rendering.core.DataBuffer;
import de.javagl.rendering.core.DataBufferType;
import de.javagl.rendering.core.GraphicsObject;

/**
 * Abstract base implementation of a {@link GraphicsObjectHandler}.
 *
 * @param <U> The type of the internal representation of the
 * objects handled by this class  
 */
public abstract class AbstractGraphicsObjectHandler<U> 
    extends AbstractReferenceHandler<GraphicsObject, U> 
    implements GraphicsObjectHandler<U>
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(AbstractGraphicsObjectHandler.class.getName());
    
    @Override
    protected final void handleChildren(GraphicsObject graphicsObject)
    {
        for (Attribute attribute : graphicsObject.getAttributes())
        {
            if (attribute.getType() == DataBufferType.FLOAT)
            {
                DataBuffer dataBuffer = 
                    graphicsObject.getDataBuffer(attribute);
                getDataBufferHandler().handle(dataBuffer);
            }
            else
            {
                logger.warning(
                    "Only float attributes are supported, "+
                    "found "+attribute.getType());
            }
        }
    }
    
    @Override
    public final void releaseChildren(GraphicsObject graphicsObject)
    {
        for (Attribute attribute : graphicsObject.getAttributes())
        {
            DataBuffer dataBuffer = graphicsObject.getDataBuffer(attribute);
            getDataBufferHandler().release(dataBuffer);
        }
    }
    
//    /**
//     * Validate the given GraphicsObject, by validating all the
//     * data buffers it contains.
//     */
//    protected void validate(GraphicsObject texture)
//    {
//        
//    }
//    
    
}
