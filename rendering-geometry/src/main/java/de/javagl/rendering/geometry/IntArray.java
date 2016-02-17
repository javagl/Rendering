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

import java.nio.IntBuffer;

/**
 * Read-only interface for an int array.
 */
public interface IntArray
{
    /**
     * Returns the size of this array
     * 
     * @return The size of this array
     */
    int getSize();

    /**
     * Returns the value at the given index
     * 
     * @param index The index
     * @return The value at the given index
     */
    int get(int index);
    
    /**
     * Write the contents of this array into the given buffer.
     * The buffer must have enough remaining elements, and its 
     * position will be advanced according to the elements 
     * that are written.
     * 
     * @param destination The destination buffer
     */
    void get(IntBuffer destination);
    
    /**
     * Returns a view of the portion of this array between the 
     * specified fromIndex, inclusive, and toIndex, exclusive. 
     * 
     * @param fromIndex The start index (inclusive)
     * @param toIndex The end index (exclusive)
     * @return The sub-array
     */
    IntArray subArray(int fromIndex, int toIndex);
    
}
