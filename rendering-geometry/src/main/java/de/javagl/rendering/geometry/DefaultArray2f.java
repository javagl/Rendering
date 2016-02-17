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
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2f;
import javax.vecmath.Tuple2f;

import de.javagl.rendering.geometry.utils.ArrayUtils;

/**
 * Default implementation of a {@link MutableArray2f}
 */
final class DefaultArray2f implements MutableArray2f
{
    /**
     * The List backing this array
     */
    private final List<Tuple2f> list;
    
    /**
     * Creates a new, empty, DefaultArray2f
     */
    DefaultArray2f()
    {
        list = new ArrayList<Tuple2f>();        
    }

    /**
     * Creates a new, DefaultArray2f with the given size
     * 
     * @param size The size, in number of elements
     */
    DefaultArray2f(int size)
    {
        list = new ArrayList<Tuple2f>(size);
        for (int i=0; i<size; i++)
        {
            list.add(new Point2f());
        }
    }

    /**
     * Creates a DefaultArray2f that is backed by the given list
     * 
     * @param list The backing list
     */
    DefaultArray2f(List<Tuple2f> list)
    {
        this.list = list;
    }
    
    @Override
    public int getSize()
    {
        return list.size();
    }

    @Override
    public void get2f(int index, Tuple2f tuple)
    {
        tuple.set(list.get(index));
    }

    @Override
    public void set2f(int index, Tuple2f tuple)
    {
        list.get(index).set(tuple);
    }
    
    @Override
    public void get2f(FloatBuffer destination)
    {
        ArrayUtils.writeArrayToBuffer(this, destination);
    }

    @Override
    public void set2f(FloatBuffer source)
    {
        Tuple2f tuple = new Point2f();
        for (int i=0; i<getSize(); i++)
        {
            tuple.x = source.get();
            tuple.y = source.get();
            set2f(i, tuple);
        }
    }
    
    @Override
    public MutableArray2f subArray2f(int fromIndex, int toIndex)
    {
        return new DefaultArray2f(list.subList(fromIndex, toIndex));
    }

    
    
    /**
     * Add the given tuple to this array
     * 
     * @param tuple The tuple to add
     */
    public void add(Tuple2f tuple)
    {
        list.add(tuple);
    }
    
    /**
     * Remove the value at the given index
     * 
     * @param index The index of the value to remove
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    
    /**
     * Clears this array
     */
    public void clear()
    {
        list.clear();
    }


}
