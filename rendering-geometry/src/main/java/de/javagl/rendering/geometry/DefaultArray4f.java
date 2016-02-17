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

import javax.vecmath.Point4f;
import javax.vecmath.Tuple4f;

import de.javagl.rendering.geometry.utils.ArrayUtils;

/**
 * Default implementation of a {@link MutableArray4f}
 */
final class DefaultArray4f implements MutableArray4f
{
    /**
     * The List backing this array
     */
    private final List<Tuple4f> list;
    
    /**
     * Creates a new, empty, DefaultArray4f
     */
    DefaultArray4f()
    {
        list = new ArrayList<Tuple4f>();        
    }

    /**
     * Creates a new, DefaultArray4f with the given size
     * 
     * @param size The size, in number of elements
     */
    DefaultArray4f(int size)
    {
        list = new ArrayList<Tuple4f>(size);
        for (int i=0; i<size; i++)
        {
            list.add(new Point4f());
        }
    }
    
    /**
     * Creates a DefaultArray4f that is backed by the given list
     * 
     * @param list The backing list
     */
    DefaultArray4f(List<Tuple4f> list)
    {
        this.list = list;
    }

    @Override
    public int getSize()
    {
        return list.size();
    }

    @Override
    public void get4f(int index, Tuple4f tuple)
    {
        tuple.set(list.get(index));
    }

    @Override
    public void set4f(int index, Tuple4f tuple)
    {
        list.get(index).set(tuple);
    }
    
    @Override
    public void get4f(FloatBuffer destination)
    {
        ArrayUtils.writeArrayToBuffer(this, destination);
    }

    @Override
    public void set4f(FloatBuffer source)
    {
        Tuple4f tuple = new Point4f();
        for (int i=0; i<getSize(); i++)
        {
            tuple.x = source.get();
            tuple.y = source.get();
            tuple.z = source.get();
            tuple.w = source.get();
            set4f(i, tuple);
        }
    }
    
    @Override
    public MutableArray4f subArray4f(int fromIndex, int toIndex)
    {
        return new DefaultArray4f(list.subList(fromIndex, toIndex));
    }
    
    /**
     * Add the given tuple to this array
     * 
     * @param tuple The tuple to add
     */
    public void add(Tuple4f tuple)
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
