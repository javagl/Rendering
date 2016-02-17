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

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.geometry.Array3f;
import de.javagl.rendering.geometry.Geometry;
import de.javagl.rendering.geometry.IntArray;
import de.javagl.rendering.geometry.Ray;
import de.javagl.rendering.geometry.Rays;
import de.javagl.rendering.geometry.utils.Intersection.RayTriangle;

/**
 * Implementation of the {@link Pickable} interface that allows picking
 * a {@link Geometry}.
 * 
 * @param <T> The type of the Geometry 
 */
class PickableGeometry<T extends Geometry> implements 
    Pickable<TrianglePickingResult<T>>
{
    /**
     * Whether this object is pickable
     */
    private boolean pickable = true;
    
    /**
     * The actual geometry that is picked
     */
    private final T geometry;
    
    /**
     * Creates a new PickableGeometry for the given Geometry
     * 
     * @param geometry The Geometry to pick
     */
    PickableGeometry(T geometry)
    {
        this.geometry = geometry;
    }
    
    /**
     * Set the flag which indicates whether this object is pickable
     * 
     * @param pickable Whether this object is pickable
     */
    public void setPickable(boolean pickable)
    {
        this.pickable = pickable;
    }
    
    
    @Override
    public boolean isPickable()
    {
        return pickable;
    }

    @Override
    public TrianglePickingResult<T> computePickingResult(Ray ray)
    {
        return computePickingResult(geometry, ray);
    }

    /**
     * Computes the {@link TrianglePickingResult} of intersecting
     * the given {@link Geometry} with the given {@link Ray}. The
     * ray is assumed to have a <i>normalized</i> direction. 
     *
     * @param geometry The {@link Geometry}
     * @param ray The {@link Ray}
     * @return The {@link TrianglePickingResult}. If there is no 
     * intersection, then <code>null</code> is returned.
     */
    private static <T extends Geometry> TrianglePickingResult<T> 
        computePickingResult(T geometry, Ray ray)
    {
        Point3f p0 = new Point3f();
        Point3f p1 = new Point3f();
        Point3f p2 = new Point3f();

        Point3f rayOrigin = ray.getOrigin();
        Vector3f rayDirection = ray.getDirection();
        
        Point3f result = new Point3f();
        Point3f closestResult = new Point3f(0,0,Float.POSITIVE_INFINITY);
        Array3f vertices = geometry.getVertices();
        IntArray indices = geometry.getIndices();
        int closestTriangleIndex = -1;
        int numTriangles = indices.getSize() / 3;
        for (int i = 0; i < numTriangles; i++)
        {
            int vi0 = indices.get(i * 3 + 0);
            int vi1 = indices.get(i * 3 + 1);
            int vi2 = indices.get(i * 3 + 2);

            vertices.get3f(vi0, p0);
            vertices.get3f(vi1, p1);
            vertices.get3f(vi2, p2);

            boolean intersect = RayTriangle.intersect(
                rayOrigin, rayDirection, p0, p1, p2, result);
            if (intersect)
            {
                if (result.z < closestResult.z)
                {
                    closestResult.set(result);
                    closestTriangleIndex = i;
                }
            }
        }
        
        if (closestTriangleIndex >= 0)
        {
            Point3f barycentricCoordinates = new Point3f();
            barycentricCoordinates.x = (1-closestResult.x-closestResult.y);
            barycentricCoordinates.y = closestResult.x;
            barycentricCoordinates.z = closestResult.y;
            return PickingResults.create(geometry, Rays.copy(ray), 
                closestResult.z, closestTriangleIndex, 
                barycentricCoordinates);
        }
        return null;
    }

}
