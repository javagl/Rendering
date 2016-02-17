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
import de.javagl.rendering.core.DataBuffer;


/**
 * Abstract base implementation of a {@link DataBufferHandler}.
 * 
 * @param <U> The type of the internal representation of the
 * objects handled by this class  
 */
public abstract class AbstractDataBufferHandler<U> 
    extends AbstractReferenceHandler<DataBuffer, U> 
    implements DataBufferHandler<U> //, DataBufferListener
{
    /**
     * The manager for dirty ranges inside the data buffers
     */
    //protected DirtyRangeManager<DataBuffer> dirtyRangeManager;
    
    /**
     * Creates a new AbstractDataBufferHandler
     */
    protected AbstractDataBufferHandler()
    {
        //dirtyRangeManager = new DirtyRangeManager<DataBuffer>();
    }
    
    @Override
    protected final void handleChildren(DataBuffer t)
    {
        // No children
    }

    @Override
    protected final void releaseChildren(DataBuffer t)
    {
        // No children
    }
    
//    @Override
//    public void updateRequested(DataBuffer dataBuffer, int start, int length)
//    {
//        dirtyRangeManager.addDirtyRange(dataBuffer, start, length);
//    }
//    
//    /**
//     * Validate the given data buffer. If an update has been requested
//     * for the given data buffer, then this method will call 
//     * {@link #updateDataBuffer(DataBuffer, int, int)}
//     * with the respective range. 
//     *  
//     * @param dataBuffer The data buffer to validate
//     */
//    protected void validate(DataBuffer dataBuffer)
//    {
//        Range range = dirtyRangeManager.getDirtyRange(dataBuffer);
//        if (range != null)
//        {
//            updateDataBuffer(dataBuffer, range.start, range.length);
//            dirtyRangeManager.clearDirtyRange(dataBuffer);
//        }
//    }
}
