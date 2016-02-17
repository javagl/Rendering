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

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

/**
 * Methods for creating {@link Ray} instances
 */
public class Rays
{
    /**
     * Creates a new {@link Ray} that contains a copy of 
     * the given origin and direction.
     * 
     * @param origin The origin
     * @param direction The direction
     * @return The new {@link Ray}
     */
    public static Ray create(Tuple3f origin, Tuple3f direction)
    {
        return new DefaultRay(origin, direction);
    }
    
    /**
     * Create a copy of the given {@link Ray}
     * 
     * @param ray The {@link Ray} to copy
     * @return The copy
     */
    public static Ray copy(Ray ray)
    {
        return create(ray.getOrigin(), ray.getDirection());
    }
    
    /**
     * Returns a copy of the given {@link Ray}, where the direction
     * is normalized.
     * 
     * @param ray The input {@link Ray}
     * @return The {@link Ray} with the normalized direction
     */
    public static Ray normalize(Ray ray)
    {
        Vector3f d = ray.getDirection();
        d.normalize();
        DefaultRay result = new DefaultRay(ray.getOrigin(), d);
        return result;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Rays()
    {
        // Private constructor to prevent instantiation
    }

}
