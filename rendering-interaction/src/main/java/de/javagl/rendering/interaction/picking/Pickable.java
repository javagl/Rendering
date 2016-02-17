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
 * Interface for all objects that may be picked with a {@link Picker}. 
 * These objects may be intersected with a {@link Ray}, and return a
 * {@link PickingResult} describing the intersection of the ray and 
 * the object.
 * 
 * @param <T> The type of the {@link PickingResult}
 */
public interface Pickable<T extends PickingResult<?>>
{
    /**
     * Whether this object is pickable
     * 
     * @return Whether this object is pickable
     */
    boolean isPickable();
    
    /**
     * Compute the result that describes the intersection of the given
     * {@link Ray} and this object, or <code>null</code> if there is no 
     * intersection. <br> 
     * <br>
     * <u>It is assumed that the direction of the given picking {@link Ray}
     * is <strong>normalized</strong></u>! The {@link Rays#normalize(Ray)}
     * method may be used to create a normalized instance of a {@link Ray}.
     * 
     * @param ray The picking {@link Ray}
     * @return The {@link PickingResult}
     */
    T computePickingResult(Ray ray);
}
