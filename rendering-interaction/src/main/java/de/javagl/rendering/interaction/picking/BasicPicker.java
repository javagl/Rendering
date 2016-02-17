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

package de.javagl.rendering.interaction.picking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.javagl.rendering.geometry.Ray;

/**
 * Basic implementation of a {@link Picker}, which may pick arbitrary 
 * {@link Pickable} objects, and inform {@link PickingListener}s about the 
 * {@link PickingResult}s.
 * 
 * @param <T> The type of the picking results
 */
class BasicPicker<T extends PickingResult<?>> implements Picker<T>
{
    /**
     * The list of {@link Pickable} objects
     */
    private final List<Pickable<? extends T>> pickables;
    
    /**
     * Whether only the closest object should cause the 
     * {@link PickingListener}s to be notified
     */
    private boolean pickOnlyClosest = true;
    
    /**
     * Whether the picking of the objects should be enforced, even
     * if their {@link Pickable#isPickable()} method returns 'false'
     */
    private boolean forcePicking = false;
    
    /**
     * The list of PickingListeners for this Picker
     */
    private final List<PickingListener<T>> pickingListeners;
    
    /**
     * Default constructor
     */
    BasicPicker()
    {
        this.pickables = new CopyOnWriteArrayList<Pickable<? extends T>>();
        this.pickingListeners = 
            new CopyOnWriteArrayList<PickingListener<T>>();
    }
    
    @Override
    public void pick(Ray ray)
    {
        if (pickOnlyClosest)
        {
            T closestPickingResult = null;
            for (Pickable<? extends T> pickable : pickables)
            {
                if (forcePicking || pickable.isPickable())
                {
                    T pickingResult = pickable.computePickingResult(ray);
                    if (closestPickingResult == null || 
                        closestPickingResult.getDistance() < 
                        pickingResult.getDistance())
                    {
                        closestPickingResult = pickingResult;
                    }
                }
            }
            if (closestPickingResult != null)
            {
                notifyPickingListeners(
                    Collections.singletonList(closestPickingResult));
            }
        }
        else
        {
            List<T> pickingResults = new ArrayList<T>();
            for (Pickable<? extends T> pickable : pickables)
            {
                if (forcePicking || pickable.isPickable())
                {
                    T pickingResult = pickable.computePickingResult(ray);
                    if (pickingResult != null)
                    {
                        pickingResults.add(pickingResult);
                    }
                }
            }
            Collections.sort(pickingResults, new Comparator<T>()
            {
                @Override
                public int compare(T t0, T t1)
                {
                    return Float.compare(t0.getDistance(), t1.getDistance());
                }
            });
            notifyPickingListeners(pickingResults);
        }
    }
    
    @Override
    public void addPickable(Pickable<? extends T> pickable)
    {
        pickables.add(pickable);
    }

    @Override
    public void removePickable(Pickable<? extends T> pickable)
    {
        pickables.remove(pickable);
    }

    @Override
    public void setPickOnlyClosest(boolean pickOnlyClosest)
    {
        this.pickOnlyClosest = pickOnlyClosest;
    }

    @Override
    public void setForcePicking(boolean forcePicking)
    {
        this.forcePicking = forcePicking;
    }
    
    /**
     * Notifies all {@link PickingListener}s about the given 
     * {@link PickingResult}s.
     * 
     * @param pickingResults The {@link PickingResult}s
     */
    protected final void notifyPickingListeners(List<T> pickingResults)
    {
        for (PickingListener<T> pickingListener : pickingListeners)
        {
            pickingListener.picked(pickingResults);
        }
    }
    

    @Override
    public void addPickingListener(PickingListener<T> pickingListener)
    {
        pickingListeners.add(pickingListener);
    }

    @Override
    public void removePickingListener(PickingListener<T> pickingListener)
    {
        pickingListeners.remove(pickingListener);
    }
    
    
}
