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

package de.javagl.rendering.core.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Point2f;
import javax.vecmath.Point2i;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Point4f;
import javax.vecmath.Point4i;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;

/**
 * Tuple utility methods
 */
public class TupleUtils
{
    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 2, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static FloatBuffer writeTupleToBuffer(
        Tuple2f tuple, FloatBuffer buffer)
    {
        FloatBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createFloatBuffer(2);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        return result;
    }

    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 3, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static FloatBuffer writeTupleToBuffer(
        Tuple3f tuple, FloatBuffer buffer)
    {
        FloatBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createFloatBuffer(3);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        result.put(tuple.z);
        return result;
    }
    

    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 4, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static FloatBuffer writeTupleToBuffer(
        Tuple4f tuple, FloatBuffer buffer)
    {
        FloatBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createFloatBuffer(4);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        result.put(tuple.z);
        result.put(tuple.w);
        return result;
    }
    
    
    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 2, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static IntBuffer writeTupleToBuffer(
        Tuple2i tuple, IntBuffer buffer)
    {
        IntBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createIntBuffer(2);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        return result;
    }

    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 3, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static IntBuffer writeTupleToBuffer(
        Tuple3i tuple, IntBuffer buffer)
    {
        IntBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createIntBuffer(3);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        result.put(tuple.z);
        return result;
    }
    

    /**
     * Write the contents of the given tuple into the given buffer and 
     * returns it. It is assumed that the given buffer has enough space 
     * for the tuple. If the given buffer is <code>null</code>, a new 
     * buffer will be created and returned. The position of the buffer 
     * will be advanced by 4, according to the elements that are written.
     * 
     * @param tuple The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the tuple data
     */
    public static IntBuffer writeTupleToBuffer(
        Tuple4i tuple, IntBuffer buffer)
    {
        IntBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createIntBuffer(4);
        }
        result.put(tuple.x);
        result.put(tuple.y);
        result.put(tuple.z);
        result.put(tuple.w);
        return result;
    }
    
    
    
    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 2, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple2f writeBufferToTuple(
        FloatBuffer buffer, Tuple2f tuple)
    {
        Tuple2f result = tuple;
        if (result == null)
        {
            result = new Point2f();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        return result;
    }

    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 3, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple3f writeBufferToTuple(
        FloatBuffer buffer, Tuple3f tuple)
    {
        Tuple3f result = tuple;
        if (result == null)
        {
            result = new Point3f();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        result.z = buffer.get();
        return result;
    }

    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 4, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple4f writeBufferToTuple(
        FloatBuffer buffer, Tuple4f tuple)
    {
        Tuple4f result = tuple;
        if (result == null)
        {
            result = new Point4f();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        result.z = buffer.get();
        result.w = buffer.get();
        return result;
    }
    
    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 2, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple2i writeBufferToTuple(
        IntBuffer buffer, Tuple2i tuple)
    {
        Tuple2i result = tuple;
        if (result == null)
        {
            result = new Point2i();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        return result;
    }

    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 3, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple3i writeBufferToTuple(
        IntBuffer buffer, Tuple3i tuple)
    {
        Tuple3i result = tuple;
        if (result == null)
        {
            result = new Point3i();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        result.z = buffer.get();
        return result;
    }

    /**
     * Write the contents of the given buffer into the given tuple and returns 
     * it. It is assumed that the given buffer has enough remaining data to 
     * fill the tuple. If the given tuple is <code>null</code>, a new tuple 
     * is created and returned. The position of the buffer will be advanced 
     * by 4, according to the elements that are read.
     * 
     * @param buffer The buffer to read from
     * @param tuple The tuple to fill
     * @return The given tuple, now containing the buffer data
     */
    public static Tuple4i writeBufferToTuple(
        IntBuffer buffer, Tuple4i tuple)
    {
        Tuple4i result = tuple;
        if (result == null)
        {
            result = new Point4i();
        }
        result.x = buffer.get();
        result.y = buffer.get();
        result.z = buffer.get();
        result.w = buffer.get();
        return result;
    }

    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple2f[] deepCopy(Tuple2f ... array)
    {
        Tuple2f result[] = new Tuple2f[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point2f(array[i]);
        }
        return result;
    }
    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple3f[] deepCopy(Tuple3f ... array)
    {
        Tuple3f result[] = new Tuple3f[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point3f(array[i]);
        }
        return result;
    }
    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple4f[] deepCopy(Tuple4f ... array)
    {
        Tuple4f result[] = new Tuple4f[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point4f(array[i]);
        }
        return result;
    }
    
    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple2i[] deepCopy(Tuple2i ... array)
    {
        Tuple2i result[] = new Tuple2i[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point2i(array[i]);
        }
        return result;
    }
    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple3i[] deepCopy(Tuple3i ... array)
    {
        Tuple3i result[] = new Tuple3i[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point3i(array[i]);
        }
        return result;
    }
    
    /**
     * Returns a deep copy of the given array. The given array may not
     * be <code>null</code>, and may not contain <code>null</code> elements.
     * 
     * @param array The array
     * @return The deep copy
     */
    public static Tuple4i[] deepCopy(Tuple4i ... array)
    {
        Tuple4i result[] = new Tuple4i[array.length];
        for (int i=0; i<array.length; i++)
        {
            result[i] = new Point4i(array[i]);
        }
        return result;
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private TupleUtils()
    {
        // Private constructor to prevent instantiation
    }
    
}



