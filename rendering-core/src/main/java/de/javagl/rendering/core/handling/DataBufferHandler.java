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
 * A class that is a {@link Handler} for 
 * {@link DataBuffer} instances.
 * 
 * @param <U> The type of the internal representation of the
 * objects handled by this class. 
 */
public interface DataBufferHandler<U> extends Handler<DataBuffer, U>
{
    /**
     * Updates the specified range of the internal representation 
     * of the given {@link DataBuffer}.
     * 
     * @param dataBuffer The {@link DataBuffer}
     * @param start The start of the range to update
     * @param length The length of the range to update
     */
    void updateDataBuffer(DataBuffer dataBuffer, int start, int length);

    /**
     * Update the internal representation of the given {@link DataBuffer}
     * 
     * @param dataBuffer The {@link DataBuffer}
     */
    void updateDataBuffer(DataBuffer dataBuffer);
    
}
