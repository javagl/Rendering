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

import javax.vecmath.Tuple2f;

/**
 * Interface for a mutable {@link Array2f}
 */
public interface MutableArray2f extends Array2f
{
    /**
     * Set the contents of this array at the given index
     * to the values of the given tuple
     * 
     * @param index The index
     * @param tuple The tuple to read the data from. 
     */
    void set2f(int index, Tuple2f tuple);

    /**
     * Set the contents of this array to the contents of the given buffer.
     * The buffer must have enough remaining elements, and its position 
     * will be advanced according to the elements that are read.
     * 
     * @param source The source buffer
     */
    void set2f(FloatBuffer source);
 
    @Override
    MutableArray2f subArray2f(int fromIndex, int toIndex);
    
}
