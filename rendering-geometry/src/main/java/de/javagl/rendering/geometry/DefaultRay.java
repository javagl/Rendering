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

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


/**
 * Default implementation of a {@link Ray}
 */
class DefaultRay implements Ray
{
    /**
     * The origin of the ray
     */
    private final Point3f origin;
    
    /**
     * The direction of the ray.
     */
    private final Vector3f direction;

    /**
     * Creates a new DefaultRay with the given origin and
     * direction. The ray will contain copies of the given
     * values.
     * 
     * @param origin The origin
     * @param direction The direction
     */
    DefaultRay(Tuple3f origin, Tuple3f direction)
    {
        this.origin = new Point3f(origin);
        this.direction = new Vector3f(direction);
    }
    
    @Override
    public Point3f getOrigin()
    {
        return new Point3f(origin);
    }

    @Override
    public Vector3f getDirection()
    {
        return new Vector3f(direction);
    }

    @Override
    public String toString()
    {
        return "DefaultRay["+
            "origin="+origin+","+
            "direction="+direction+"]";
    }
}
