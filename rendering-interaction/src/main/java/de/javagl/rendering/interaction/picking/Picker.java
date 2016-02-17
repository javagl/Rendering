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

import de.javagl.rendering.geometry.Ray;
import de.javagl.rendering.geometry.Rays;

/**
 * Interface for all classes that may be used to pick objects. 
 * It may be used to intersect {@link Pickable} objects with a 
 * {@link Ray} and inform {@link PickingListener} instances
 * about the objects that have been hit by the {@link Ray}. 
 *
 * @param <T> The type of the {@link PickingResult}
 */
public interface Picker<T extends PickingResult<?>>
{
    /**
     * Add the given object to this {@link Picker}
     * 
     * @param pickable The {@link Pickable} object to add
     */
    void addPickable(Pickable<? extends T> pickable);

    /**
     * Remove the given object from this {@link Picker}
     * 
     * @param pickable The {@link Pickable} object to remove
     */
    void removePickable(Pickable<? extends T> pickable);
    
    /**
     * Set the flag which indicates whether only the closest
     * {@link Pickable} object that was picked will cause the 
     * listeners to be notified.
     * 
     * @param pickOnlyClosest Whether only the closest objects are picked
     */
    void setPickOnlyClosest(boolean pickOnlyClosest);
    
    /**
     * Set the flag which indicates whether all {@link Pickable} objects 
     * should be picked, even when their {@link Pickable#isPickable()} 
     * method returns <code>false</code>.
     * 
     * @param forcePicking Whether picking should be enforced
     */
    void setForcePicking(boolean forcePicking);

    /**
     * Pick with the given {@link Ray}. This will intersect all 
     * {@link Pickable} objects of this {@link Picker} with the 
     * given {@link Ray}, and inform the
     * {@link PickingListener PickingListeners}
     * about the intersections that have been found.<br>
     * <br>
     * <u>It is assumed that the direction of the given picking {@link Ray}
     * is <strong>normalized</strong></u>! The {@link Rays#normalize(Ray)}
     * method may be used to create a normalized instance of a {@link Ray}.
     * 
     * @param ray The ray to intersect with the objects. 
     */
    void pick(Ray ray);
    
    /**
     * Add the given {@link PickingListener} to be informed about picking 
     * that is done using this {@link Picker}.
     * 
     * @param pickingListener The {@link PickingListener} to add
     */
    void addPickingListener(PickingListener<T> pickingListener);

    /**
     * Remove the given {@link PickingListener} from this {@link Picker}.
     * 
     * @param pickingListener The {@link PickingListener} to remove
     */
    void removePickingListener(PickingListener<T> pickingListener);
}
