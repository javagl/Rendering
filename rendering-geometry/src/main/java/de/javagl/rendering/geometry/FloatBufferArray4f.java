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

import javax.vecmath.Tuple4f;

/**
 * This class offers a {@link MutableArray4f} view on a FloatBuffer.
 */
final class FloatBufferArray4f implements MutableArray4f
{
    /**
     * The backing buffer
     */
    private final FloatBuffer buffer;
    
    /**
     * Creates a new FloatBufferArray4f, backed by the given buffer. 
     * 
     * @param buffer The buffer. The capacity should be a multiple of 4.
     */
    FloatBufferArray4f(FloatBuffer buffer)
    {
        this.buffer = buffer;
    }
    

    /**
     * Creates a new FloatBufferArray4f, backed by the given array. 
     * 
     * @param array The array. The length should be a multiple of 4.
     */
    FloatBufferArray4f(float array[])
    {
        this(array, 0, array.length);
    }
    
    /**
     * Creates a new FloatArray4f, backed by the specified
     * portion of the given array. 
     * 
     * @param array The array
     * @param offset The offset inside the given array
     * @param length The length of the portion of the array.
     * This should be a multiple of 4.
     */
    FloatBufferArray4f(float array[], int offset, int length)
    {
        FloatBuffer b = FloatBuffer.wrap(array);
        b.position(offset);
        b.limit(offset + length);
        this.buffer = b.slice();
    }

    @Override
    public int getSize()
    {
        return buffer.capacity()/4;
    }

    @Override
    public void get4f(int index, Tuple4f tuple)
    {
        int i = index << 2;
        tuple.x = buffer.get(i++);
        tuple.y = buffer.get(i++);
        tuple.z = buffer.get(i++);
        tuple.w = buffer.get(i++);
    }

    @Override
    public void set4f(int index, Tuple4f tuple)
    {
        int i = index << 2;
        buffer.put(i++, tuple.x);
        buffer.put(i++, tuple.y);
        buffer.put(i++, tuple.z);
        buffer.put(i++, tuple.w);
    }
    
    @Override
    public void get4f(FloatBuffer destination)
    {
        int oldPosition = buffer.position();
        destination.put(buffer);
        buffer.position(oldPosition);
    }

    @Override
    public void set4f(FloatBuffer source)
    {
        int oldPosition = buffer.position();
        int oldSourceLimit = source.limit();
        source.limit(source.position() + buffer.remaining());
        buffer.put(source);
        buffer.position(oldPosition);
        source.limit(oldSourceLimit);
    }

    @Override
    public MutableArray4f subArray4f(int fromIndex, int toIndex)
    {
        int oldPosition = buffer.position();
        int oldLimit = buffer.limit();
        buffer.limit(toIndex * 4);
        buffer.position(fromIndex * 4);
        MutableArray4f result = new FloatBufferArray4f(buffer.slice());
        buffer.limit(oldLimit);
        buffer.position(oldPosition);
        return result;
    }

}
