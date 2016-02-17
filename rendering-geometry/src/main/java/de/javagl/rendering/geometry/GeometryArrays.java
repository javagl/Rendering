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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Methods for creating geometry array instances. Unless otherwise noted,
 * none of the arguments of these methods may be <code>null</code>.
 */
public class GeometryArrays
{
    /**
     * Returns a new {@link MutableIntArray} with the given size
     * 
     * @param size The size of the new array, in number of elements
     * @return The new {@link MutableIntArray}
     * @throws IllegalArgumentException If the given size is negative
     */
    public static MutableIntArray createMutableIntArray(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size may not be negative, but is "+size);
        }
        return new DefaultIntArray(size);
    }
    
    
    /**
     * Returns a new {@link MutableArray2f} with the given size
     * 
     * @param size The size of the new array, in number of elements
     * @return The new {@link MutableArray2f}
     * @throws IllegalArgumentException If the given size is negative
     */
    public static MutableArray2f createMutableArray2f(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size may not be negative, but is "+size);
        }
        return new DefaultArray2f(size);
    }
    
    /**
     * Returns a new {@link MutableArray3f} with the given size
     * 
     * @param size The size of the new array, in number of elements
     * @return The new {@link MutableArray2f}
     * @throws IllegalArgumentException If the given size is negative
     */
    public static MutableArray3f createMutableArray3f(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size may not be negative, but is "+size);
        }
        return new DefaultArray3f(size);
    }
    
    /**
     * Returns a new {@link MutableArray4f} with the given size
     * 
     * @param size The size of the new array, in number of elements
     * @return The new {@link MutableArray4f}
     * @throws IllegalArgumentException If the given size is negative
     */
    public static MutableArray4f createMutableArray4f(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size may not be negative, but is "+size);
        }
        return new DefaultArray4f(size);
    }
    
    
    /**
     * Creates a {@link IntArray} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. 
     * @return The {@link IntArray}
     */
    public static IntArray asIntArray(ByteBuffer buffer)
    {
        return new ByteBufferIntArray(buffer);
    }
    
    /**
     * Creates a {@link IntArray} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. 
     * @return The {@link IntArray}
     */
    public static IntArray asIntArray(ShortBuffer buffer)
    {
        return new ShortBufferIntArray(buffer);
    }
    
    /**
     * Creates a {@link IntArray} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. 
     * @return The {@link IntArray}
     */
    public static IntArray asIntArray(IntBuffer buffer)
    {
        return new IntBufferIntArray(buffer);
    }
    
    /**
     * Creates a {@link MutableArray2f} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. The capacity muse be a multiple of 2.
     * @return The {@link MutableArray2f}
     * @throws IllegalArgumentException If the capacity of the buffer
     * is not a multiple of 2
     */
    public static MutableArray2f asMutableArray2f(FloatBuffer buffer)
    {
        if (buffer.capacity() % 2 != 0)
        {
            throw new IllegalArgumentException(
                "The capacity of the buffer must be a multiple " + 
                "of 2, but is "+buffer.capacity());
        }
        return new FloatBufferArray2f(buffer);
    }

    /**
     * Creates a {@link MutableArray3f} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. The capacity should be a multiple of 3.
     * @return The {@link MutableArray3f}
     * @throws IllegalArgumentException If the capacity of the buffer
     * is not a multiple of 3
     */
    public static MutableArray3f asMutableArray3f(FloatBuffer buffer)
    {
        if (buffer.capacity() % 3 != 0)
        {
            throw new IllegalArgumentException(
                "The capacity of the buffer must be a multiple " + 
                "of 3, but is "+buffer.capacity());
        }
        return new FloatBufferArray3f(buffer);
    }

    /**
     * Creates a {@link MutableArray4f} that is backed by the given buffer. 
     * 
     * @param buffer The buffer. The capacity should be a multiple of 4.
     * @return The {@link MutableArray4f}
     * @throws IllegalArgumentException If the capacity of the buffer
     * is not a multiple of 4
     */
    public static MutableArray4f asMutableArray4f(FloatBuffer buffer)
    {
        if (buffer.capacity() % 4 != 0)
        {
            throw new IllegalArgumentException(
                "The capacity of the buffer must be a multiple " + 
                "of 4, but is "+buffer.capacity());
        }
        return new FloatBufferArray4f(buffer);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private GeometryArrays()
    {
        // Private constructor to prevent instantiation
    }
}
