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
import java.nio.Buffer;

/**
 * Abstraction of a general buffer, with an associated {@link DataBufferType}.
 * <br>
 * TODO This interface may be extended with a listener infrastructure!
 */
public interface DataBuffer
{
    /**
     * Returns the Buffer that contains the actual data
     * 
     * @return The Buffer that contains the actual data
     */
    Buffer getBuffer();
    
    /**
     * Returns the {@link DataBufferType} of the data contained in
     * this buffer
     * 
     * @return The {@link DataBufferType}
     */
    DataBufferType getType();
    
//    /**
//     * Will inform all registered {@link DataBufferListener}s
//     * that the specified range of
//     * this buffer has to be updated.
//     * 
//     * @param start The start of the range
//     * @param length The length of the range
//     */
//    void requestUpdate(int start, int length);
//    
//    /**
//     * Add the given listener to this buffer
//     * 
//     * @param dataBufferListener The listener to add
//     */
//    void addDataBufferListener(
//        DataBufferListener dataBufferListener);
//    
//    /**
//     * Remove the given listener from this buffer
//     * 
//     * @param dataBufferListener The listener to remove
//     */
//    void removeDataBufferListener(
//        DataBufferListener dataBufferListener);
}
