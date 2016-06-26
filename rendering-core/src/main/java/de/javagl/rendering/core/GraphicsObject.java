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


/**
 * A general graphically represented object. It contains a set of (triangle) 
 * indices, and an arbitrary set of {@link Attribute}s. Each attribute 
 * is mapped to a {@link DataBuffer} that contains the data for the
 * attribute.
 */
public interface GraphicsObject
{
    /**
     * Returns the {@link DataBuffer} containing the indices. This may
     * be <code>null</code> for non-indexed objects.<br>
     * <br>
     * If it is not <code>null</code>, then it can be assumed that the 
     * {@link DataBuffer#getBuffer() buffer} of the returned {@link DataBuffer} 
     * is either a <code>ByteBuffer</code>, <code>ShortBuffer</code> or an 
     * <code>IntBuffer</code>, depending on the 
     * {@link DataBuffer#getType() type}
     * 
     * @return The buffer containing the indices 
     */
    DataBuffer getIndices();
    
    /**
     * Returns the number of vertices that this object consists of
     * 
     * @return The number of vertices
     */
    int getNumVertices();

    /**
     * Returns the {@link DataBuffer} for the given {@link Attribute},
     * or <code>null</code> if this graphics object does not have the 
     * given {@link Attribute}.
     * 
     * @param attribute The {@link Attribute}
     * @return The {@link DataBuffer} for the {@link Attribute}
     */
    DataBuffer getDataBuffer(Attribute attribute);

    /**
     * Returns the (unmodifiable) list of {@link Attribute}s of this 
     * graphics object.
     * 
     * @return The {@link Attribute}s of this graphics object
     */
    List<Attribute> getAttributes();
}