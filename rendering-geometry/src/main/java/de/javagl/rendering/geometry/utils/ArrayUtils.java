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

package de.javagl.rendering.geometry.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Point4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;

import de.javagl.rendering.geometry.Array2f;
import de.javagl.rendering.geometry.Array3f;
import de.javagl.rendering.geometry.Array4f;
import de.javagl.rendering.geometry.IntArray;

/**
 * This class contains utility methods for handling {@link Array2f},
 * {@link Array3f} and {@link Array4f} objects. Unless otherwise
 * noted, none of the arguments to these methods may be <code>null</code>.
 */
public class ArrayUtils
{
    /**
     * Converts the given {@link IntArray} into a direct IntBuffer
     * 
     * @param array The input array
     * @return The buffer
     */
    public static IntBuffer toBuffer(IntArray array)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(array.getSize());
        writeArrayToBuffer(array, buffer);
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Writes the given {@link IntArray} into a IntBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written.   
     * 
     * @param array The input array
     * @param buffer The buffer
     */
    public static void writeArrayToBuffer(IntArray array, IntBuffer buffer)
    {
        for (int i=0; i<array.getSize(); i++)
        {
            buffer.put(array.get(i));
        }
    }
    
    
    /**
     * Converts the given {@link Array2f} into a direct FloatBuffer
     * 
     * @param array The input array
     * @return The buffer
     */
    public static FloatBuffer toBuffer(Array2f array)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.getSize()*2);
        writeArrayToBuffer(array, buffer);
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Writes the given {@link Array2f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param buffer The buffer
     */
    public static void writeArrayToBuffer(Array2f array, FloatBuffer buffer)
    {
        writeArrayToBuffer(array, 0, array.getSize(), buffer);
    }

    /**
     * Writes the given {@link Array2f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param offset The offset (in number of elements) in the array
     * @param length The length (in number of elements) of the segment to write
     * @param buffer The buffer
     * @throws IllegalArgumentException If the given offset or length
     * is negative
     */
    public static void writeArrayToBuffer(
        Array2f array, int offset, int length, FloatBuffer buffer)
    {
        if (offset < 0)
        {
            throw new IllegalArgumentException(
                "The offset may not be negative, but is "+offset);
        }
        if (length < 0)
        {
            throw new IllegalArgumentException(
                "The length may not be negative, but is "+length);
        }
        Tuple2f tuple = new Point2f();
        for (int i=offset; i<offset+length; i++)
        {
            array.get2f(i, tuple);
            buffer.put(tuple.x);
            buffer.put(tuple.y);
        }
    }

    
    
    
    /**
     * Converts the given {@link Array3f} into a direct FloatBuffer
     * 
     * @param array The input array
     * @return The buffer
     */
    public static FloatBuffer toBuffer(Array3f array)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.getSize()*3);
        writeArrayToBuffer(array, buffer);
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Writes the given {@link Array3f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param buffer The buffer
     */
    public static void writeArrayToBuffer(Array3f array, FloatBuffer buffer)
    {
        writeArrayToBuffer(array, 0, array.getSize(), buffer);
    }
    
    /**
     * Writes the given {@link Array3f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param offset The offset (in number of elements) in the array
     * @param length The length (in number of elements) of the segment to write
     * @param buffer The buffer
     * @throws IllegalArgumentException If the given offset or length
     * is negative
     */
    public static void writeArrayToBuffer(
        Array3f array, int offset, int length, FloatBuffer buffer)
    {
        if (offset < 0)
        {
            throw new IllegalArgumentException(
                "The offset may not be negative, but is "+offset);
        }
        if (length < 0)
        {
            throw new IllegalArgumentException(
                "The length may not be negative, but is "+length);
        }
        Tuple3f tuple = new Point3f();
        for (int i=offset; i<offset+length; i++)
        {
            array.get3f(i, tuple);
            buffer.put(tuple.x);
            buffer.put(tuple.y);
            buffer.put(tuple.z);
        }
    }
    
    
    
    /**
     * Converts the given {@link Array4f} into a direct FloatBuffer
     * 
     * @param array The input array
     * @return The buffer
     */
    public static FloatBuffer toBuffer(Array4f array)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.getSize()*4);
        writeArrayToBuffer(array, buffer);
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Writes the given {@link Array4f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param buffer The buffer
     */
    public static void writeArrayToBuffer(Array4f array, FloatBuffer buffer)
    {
        writeArrayToBuffer(array, 0, array.getSize(), buffer);
    }
    
    /**
     * Writes the given {@link Array4f} into a FloatBuffer. The given buffer 
     * must have enough remaining space. Its position will be advanced
     * according to the data that is written. 
     * 
     * @param array The input array
     * @param offset The offset (in number of elements) in the array
     * @param length The length (in number of elements) of the segment to write
     * @param buffer The buffer
     * @throws IllegalArgumentException If the given offset or length
     * is negative
     */
    public static void writeArrayToBuffer(
        Array4f array, int offset, int length, FloatBuffer buffer)
    {
        if (offset < 0)
        {
            throw new IllegalArgumentException(
                "The offset may not be negative, but is "+offset);
        }
        if (length < 0)
        {
            throw new IllegalArgumentException(
                "The length may not be negative, but is "+length);
        }
        Tuple4f tuple = new Point4f();
        for (int i=offset; i<offset+length; i++)
        {
            array.get4f(i, tuple);
            buffer.put(tuple.x);
            buffer.put(tuple.y);
            buffer.put(tuple.z);
            buffer.put(tuple.w);
        }
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private ArrayUtils()
    {
        // Private constructor to prevent instantiation
    }
    
    
}
