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
 * Interface for read-only arrays of Tuples.<br>
 * <br> 
 * Unless otherwise noted, none of the arguments of the methods in this
 * interface may be <code>null</code>.
 */
public interface Array4f
{
    /**
     * Returns the size of the array
     * 
     * @return The size of the array
     */
    int getSize();
    
    /**
     * Writes the array elements from the given index into
     * the given tuple
     * 
     * @param index The index
     * @param tuple The tuple which will store the data
     */
    void get4f(int index, Tuple4f tuple);
    
    /**
     * Write the contents of this array into the given buffer.
     * The buffer must have enough remaining elements, and its 
     * position will be advanced according to the elements 
     * that are written.
     * 
     * @param destination The destination buffer
     */
    void get4f(FloatBuffer destination);
    
    /**
     * Returns a view of the portion of this array between the 
     * specified fromIndex, inclusive, and toIndex, exclusive. 
     * 
     * @param fromIndex The start index (inclusive)
     * @param toIndex The end index (exclusive)
     * @return The sub-array
     */
    Array4f subArray4f(int fromIndex, int toIndex);
    
}
