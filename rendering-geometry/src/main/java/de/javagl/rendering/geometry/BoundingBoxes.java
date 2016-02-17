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

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

/**
 * Methods for creating {@link BoundingBox} instances
 */
public class BoundingBoxes
{
    /**
     * Creates a new {@link BoundingBox} with the given minimum and
     * maximum point. 
     * 
     * @param minX The minimum x value
     * @param minY The minimum y value
     * @param minZ The minimum z value
     * @param maxX The maximum x value
     * @param maxY The maximum y value
     * @param maxZ The maximum z value
     * @return The new {@link BoundingBox}
     */
    public static BoundingBox create(
        float minX, float minY, float minZ, 
        float maxX, float maxY, float maxZ)
    {
        return new DefaultBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Creates a new {@link BoundingBox} that is initialized with the 
     * given minimum and maximum point.
     * 
     * @param min The minimum point
     * @param max The maximum point
     * @return The new {@link BoundingBox}
     */
    public static BoundingBox create(Tuple3f min, Tuple3f max)
    {
        return create(min.x, min.y, min.z, max.x, max.y, max.z);
    }
    
    
    /**
     * Combines the given {@link BoundingBox} with the given point
     * 
     * @param other The other {@link BoundingBox} 
     * @param point The point that the {@link BoundingBox} 
     * should be combined with
     * @return The new {@link BoundingBox}
     */
    public static BoundingBox combine(BoundingBox other, Tuple3f point)
    {
        return combine(other, point.x, point.y, point.z);
    }

    /**
     * Combines the given {@link BoundingBox} with the specified point
     * 
     * @param other The other {@link BoundingBox} 
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point 
     * @param z The z-coordinate of the point
     * @return The new {@link BoundingBox}
     */
    public static BoundingBox combine(
        BoundingBox other, float x, float y, float z)
    {
        return new DefaultBoundingBox(
            Math.min(other.getMinX(), x),
            Math.min(other.getMinY(), y),
            Math.min(other.getMinZ(), z),
            Math.max(other.getMaxX(), x),
            Math.max(other.getMaxY(), y),
            Math.max(other.getMaxZ(), z));
    }

    /**
     * Combines the given {@link BoundingBox} instances
     *  
     * @param b0 The first {@link BoundingBox} 
     * @param b1 The second {@link BoundingBox}
     * @return The new {@link BoundingBox} 
     */
    public static BoundingBox combine(BoundingBox b0, BoundingBox b1)
    {
        return new DefaultBoundingBox(
            Math.min(b0.getMinX(), b1.getMinX()),
            Math.min(b0.getMinY(), b1.getMinY()),
            Math.min(b0.getMinZ(), b1.getMinZ()),
            Math.max(b0.getMaxX(), b1.getMaxX()),
            Math.max(b0.getMaxY(), b1.getMaxY()),
            Math.max(b0.getMaxZ(), b1.getMaxZ()));
    }
    
    
    
    /**
     * Computes the {@link BoundingBox} of the given {@link Geometry}
     * 
     * @param geometry The {@link Geometry}
     * @return The {@link BoundingBox}
     */
    public static BoundingBox computeBoundingBox(Geometry geometry)
    {
        Tuple3f min = new Point3f();
        Tuple3f max = new Point3f();
        Tuple3f vertex = new Point3f();
        Array3f vertices = geometry.getVertices();
        for (int i=0; i<vertices.getSize(); i++)
        {
            vertices.get3f(i, vertex);
            min(min, min, vertex);
            max(max, max, vertex);
        }
        return create(min, max);
    }

    /**
     * Computes the {@link BoundingBox} of the given {@link Geometry} 
     * when it is transformed with the given matrix.
     * 
     * @param geometry The {@link Geometry}
     * @param matrix The matrix
     * @return The {@link BoundingBox}
     */
    public static BoundingBox computeBoundingBox(
        Geometry geometry, Matrix4f matrix)
    {
        Tuple3f min = new Point3f();
        Tuple3f max = new Point3f();
        Point3f vertex = new Point3f();
        Array3f vertices = geometry.getVertices();
        for (int i=0; i<vertices.getSize(); i++)
        {
            vertices.get3f(i, vertex);
            matrix.transform(vertex);
            min(min, min, vertex);
            max(max, max, vertex);
        }
        return create(min, max);
    }
    
    /**
     * Set the minimum tuple to the element-wise minimum of the given ones
     * 
     * @param min The minimum tuple
     * @param t0 The first other tuple
     * @param t1 The second other tuple
     */
    private static void min(Tuple3f min, Tuple3f t0, Tuple3f t1)
    {
        min.x = Math.min(t0.x, t1.x);
        min.y = Math.min(t0.y, t1.y);
        min.z = Math.min(t0.z, t1.z);
    }
    
    /**
     * Set the maximum tuple to the element-wise maximum of the given ones
     * 
     * @param max The maximum tuple
     * @param t0 The first other tuple
     * @param t1 The second other tuple
     */
    private static void max(Tuple3f max, Tuple3f t0, Tuple3f t1)
    {
        max.x = Math.max(t0.x, t1.x);
        max.y = Math.max(t0.y, t1.y);
        max.z = Math.max(t0.z, t1.z);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private BoundingBoxes()
    {
        // Private constructor to prevent instantiation
    }
}
