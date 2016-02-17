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

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

import de.javagl.rendering.geometry.utils.ArrayUtils;

/**
 * Default implementation of a {@link MutableArray3f}
 */
final class DefaultArray3f implements MutableArray3f
{
    /**
     * The List backing this array
     */
    private final List<Tuple3f> list;
    
    /**
     * Creates a new, empty, DefaultArray3f
     */
    DefaultArray3f()
    {
        list = new ArrayList<Tuple3f>();        
    }

    /**
     * Creates a new, DefaultArray3f with the given size
     * 
     * @param size The size, in number of elements
     */
    DefaultArray3f(int size)
    {
        list = new ArrayList<Tuple3f>(size);
        for (int i=0; i<size; i++)
        {
            list.add(new Point3f());
        }
    }
    
    /**
     * Creates a DefaultArray3f that is backed by the given list
     * 
     * @param list The backing list
     */
    DefaultArray3f(List<Tuple3f> list)
    {
        this.list = list;
    }

    @Override
    public int getSize()
    {
        return list.size();
    }

    @Override
    public void get3f(int index, Tuple3f tuple)
    {
        tuple.set(list.get(index));
    }

    @Override
    public void set3f(int index, Tuple3f tuple)
    {
        list.get(index).set(tuple);
    }
    
    @Override
    public void get3f(FloatBuffer destination)
    {
        ArrayUtils.writeArrayToBuffer(this, destination);
    }

    @Override
    public void set3f(FloatBuffer source)
    {
        Tuple3f tuple = new Point3f();
        for (int i=0; i<getSize(); i++)
        {
            tuple.x = source.get();
            tuple.y = source.get();
            tuple.z = source.get();
            set3f(i, tuple);
        }
    }
    
    @Override
    public MutableArray3f subArray3f(int fromIndex, int toIndex)
    {
        return new DefaultArray3f(list.subList(fromIndex, toIndex));
    }
    
    /**
     * Add the given tuple to this array
     * 
     * @param tuple The tuple to add
     */
    public void add(Tuple3f tuple)
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
