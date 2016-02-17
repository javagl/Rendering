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
import javax.vecmath.Vector3f;

/**
 * Interface describing a ray. All methods of this interface return copies
 * of the internal state. Instances of rays may be created with
 * the {@link Rays} class. 
 */
public interface Ray
{
    /**
     * Returns the origin of the ray. 
     * 
     * @return The origin of the ray
     */
    Point3f getOrigin();
    
    /**
     * Returns the direction of the ray. This direction may
     * not be assumed to be normalized. A ray with a normalized 
     * direction may be created with {@link Rays#normalize(Ray)}.
     * 
     * @return The direction of the ray.
     */
    Vector3f getDirection();
}
