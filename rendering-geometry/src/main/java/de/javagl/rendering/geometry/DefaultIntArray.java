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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of an {@link IntArray}
 */
final class DefaultIntArray implements MutableIntArray
{
    /**
     * The List backing this IntArray
     */
    private final List<Integer> list;

    /**
     * Creates a new, empty DefaultIntArray
     */
    DefaultIntArray()
    {
        this.list = new ArrayList<Integer>();        
    }

    /**
     * Creates a new DefaultIntArray with the given size
     * 
     * @param size The size
     */
    DefaultIntArray(int size)
    {
        this.list = new ArrayList<Integer>(Collections.nCopies(size, 0));        
    }

    /**
     * Creates a DefaultIntArray that is backed by the given list
     * 
     * @param list The list
     */
    private DefaultIntArray(List<Integer> list)
    {
        this.list = list;
    }

    @Override
    public int getSize()
    {
        return list.size();
    }

    @Override
    public int get(int index)
    {
        return list.get(index);
    }
    
    /**
     * Add the given value to this array
     * 
     * @param value The value to add
     */
    public void add(int value)
    {
        list.add(value);
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

    @Override
    public void get(IntBuffer destination)
    {
        for (int i=0; i<list.size(); i++)
        {
            destination.put(list.get(i));
        }
    }

    @Override
    public MutableIntArray subArray(int fromIndex, int toIndex)
    {
        return new DefaultIntArray(list.subList(fromIndex, toIndex));
    }
    
    @Override
    public void set(int index, int value)
    {
        list.set(index, value);
    }

    @Override
    public void set(IntBuffer source)
    {
        for (int i=0; i<list.size(); i++)
        {
            set(i, source.get());
        }
    }
    

}
