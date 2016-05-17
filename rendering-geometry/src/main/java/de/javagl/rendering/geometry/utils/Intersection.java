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

package de.javagl.rendering.geometry.utils;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

/**
 * Static utility classes containing methods for intersection 
 * computations. <br>
 * <br>
 * Unless otherwise noted, none of the arguments of these methods
 * may be <code>null</code>.<br>
 * <br>
 * The methods in these classes are NOT thread-safe!
 */
@SuppressWarnings("javadoc")
public class Intersection
{
    /**
     * A class containing a method for ray-triangle intersections
     */
    public static class RayTriangle
    {
        /**
         * An epsilon for floating point computations
         */
        private static final float EPSILON = 1e-7f;
        
        // Note: Due to the improved escape analysis in Java 1.7, 
        // the temporary variables might be omitted

        // Temporary variables
        private static final Vector3f tempEdge0 = new Vector3f();
        private static final Vector3f tempEdge1 = new Vector3f();

        // Temporary variables
        private static final Vector3f tempVector3f0 = new Vector3f();
        private static final Vector3f tempVector3f1 = new Vector3f();
        private static final Vector3f tempVector3f2 = new Vector3f();
        
        /**
         * Computes the intersection position of a ray and a triangle
         * 
         * @param rayOrigin Origin of the ray
         * @param normalizedRayDirection The <strong>normalized</strong> 
         * direction of the ray
         * @param p0 Point 0 of the triangle
         * @param p1 Point 1 of the triangle
         * @param p2 Point 2 of the triangle
         * @param result If there is an intersection, this afterwards
         * contains the intersection position (u,v,t) where (u,v) are 
         * coordinates describing the intersection point S as
         * S = u*(p1-p0)+v*(p2-p0) 
         * and t is the distance of the intersection point S along the ray.
         * The (u,v,t) tuple may be converted to barycentric coordinates
         * as follows:
         * bary.x = (1-u-v);
         * bary.y = u;
         * bary.z = v;
         * @return Whether there was an intersection
         */
        public static boolean intersect(
            Tuple3f rayOrigin, Vector3f normalizedRayDirection, 
            Tuple3f p0, Tuple3f p1, Tuple3f p2, Tuple3f result)
        {
            tempEdge0.sub(p1, p0);
            tempEdge1.sub(p2, p0);
            tempVector3f0.cross(normalizedRayDirection, tempEdge1);
            float det = tempEdge0.dot(tempVector3f0);
            if (det > -EPSILON && det < EPSILON)
            {
                return false;
            }
            float invDet = 1.0f / det;
            tempVector3f1.sub(rayOrigin, p0);
            float u = invDet * tempVector3f1.dot(tempVector3f0);
            if (u < 0.0f || u > 1.0f)
            {
                return false;
            }
            tempVector3f2.cross(tempVector3f1, tempEdge0);
            float v = invDet * normalizedRayDirection.dot(tempVector3f2);
            if (v < 0.0f || v + u > 1.0f)
            {
                return false;
            }
            float t = invDet * tempEdge1.dot(tempVector3f2);
            result.set(u,v,t);
            return true;
        }
        
        /**
         * Private constructor to prevent instantiation
         */
        private RayTriangle()
        {
            // Private constructor to prevent instantiation
        }
        
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private Intersection()
    {
        // Private constructor to prevent instantiation
    }
    
}
