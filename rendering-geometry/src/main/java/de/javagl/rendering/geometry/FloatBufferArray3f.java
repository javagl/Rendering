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

package de.javagl.rendering.geometry;

import java.nio.FloatBuffer;

import javax.vecmath.Tuple3f;

/**
 * This class offers a {@link MutableArray3f} view on a FloatBuffer.
 */
final class FloatBufferArray3f implements MutableArray3f
{
    /**
     * The backing buffer
     */
    private final FloatBuffer buffer;
    
    /**
     * Creates a new FloatBufferArray3f, backed by the given buffer. 
     * 
     * @param buffer The buffer. The capacity should be a multiple of 3.
     */
    FloatBufferArray3f(FloatBuffer buffer)
    {
        this.buffer = buffer;
    }
    

    /**
     * Creates a new FloatBufferArray3f, backed by the given array. 
     * 
     * @param array The array. The length should be a multiple of 3.
     */
    FloatBufferArray3f(float array[])
    {
        this(array, 0, array.length);
    }
    
    /**
     * Creates a new FloatArray3f, backed by the specified
     * portion of the given array. 
     * 
     * @param array The array
     * @param offset The offset inside the given array
     * @param length The length of the portion of the array.
     * This should be a multiple of 3.
     */
    FloatBufferArray3f(float array[], int offset, int length)
    {
        FloatBuffer b = FloatBuffer.wrap(array);
        b.position(offset);
        b.limit(offset+length);
        this.buffer = b.slice();
    }

    @Override
    public int getSize()
    {
        return buffer.capacity()/3;
    }

    @Override
    public void get3f(int index, Tuple3f tuple)
    {
        int i = index + index + index;
        tuple.x = buffer.get(i++);
        tuple.y = buffer.get(i++);
        tuple.z = buffer.get(i++);
    }

    @Override
    public void set3f(int index, Tuple3f tuple)
    {
        int i = index + index + index;
        buffer.put(i++, tuple.x);
        buffer.put(i++, tuple.y);
        buffer.put(i++, tuple.z);
    }
    
    @Override
    public void get3f(FloatBuffer destination)
    {
        int oldPosition = buffer.position();
        destination.put(buffer);
        buffer.position(oldPosition);
    }

    @Override
    public void set3f(FloatBuffer source)
    {
        int oldPosition = buffer.position();
        int oldSourceLimit = source.limit();
        source.limit(source.position() + buffer.remaining());
        buffer.put(source);
        buffer.position(oldPosition);
        source.limit(oldSourceLimit);
    }

    @Override
    public MutableArray3f subArray3f(int fromIndex, int toIndex)
    {
        int oldPosition = buffer.position();
        int oldLimit = buffer.limit();
        buffer.limit(toIndex * 3);
        buffer.position(fromIndex * 3);
        MutableArray3f result = new FloatBufferArray3f(buffer.slice());
        buffer.limit(oldLimit);
        buffer.position(oldPosition);
        return result;
    }

}
