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
import javax.vecmath.Tuple3f;

import de.javagl.rendering.geometry.Ray;

/**
 * Implementation of the {@link PickingResult} interface which may be
 * used to describe the result of picking an object which consists of
 * triangles.
 *
 * @param <T> The type of the picked object
 */
class DefaultTrianglePickingResult<T> implements TrianglePickingResult<T>
{
    /**
     * The picked object
     */
    private final T pickedObject;
    
    /**
     * The (normalized) {@link Ray} that caused this picking result
     */
    private final Ray ray;
    
    /**
     * The distance of the picked object along the picking ray
     */
    private final float distance;
    
    /**
     * The index of the triangle that was picked.
     */
    private final int triangleIndex;
    
    /**
     * The barycentric coordinates of the point where the picking
     * ray intersected the triangle.
     */
    private final Tuple3f barycentricCoordinates;

    /**
     * Creates a new TrianglePickingResult with the given parameters.
     * 
     * @param pickedObject The picked object
     * @param ray The <b>normalized</b> {@link Ray} that caused this picking 
     * result. A reference to the given ray will be stored.
     * @param distance The distance of the picked object along the picking ray
     * @param triangleIndex The index of the triangle that was picked.
     * @param barycentricCoordinates The barycentric coordinates of the 
     * point where the picking ray intersected the triangle.
     */
    DefaultTrianglePickingResult(
        T pickedObject, Ray ray, float distance, int triangleIndex, 
        Tuple3f barycentricCoordinates)
    {
        this.pickedObject = pickedObject;
        this.ray = ray;
        this.distance = distance;
        this.triangleIndex = triangleIndex;
        this.barycentricCoordinates = new Point3f(barycentricCoordinates);
    }
    
    @Override
    public Ray getRay()
    {
        return ray;
    }
    
    @Override
    public float getDistance()
    {
        return distance;
    }

    @Override
    public T getPickedObject()
    {
        return pickedObject;
    }
    
    @Override
    public int getTriangleIndex()
    {
        return triangleIndex;
    }
    
    @Override
    public Tuple3f getBarycentricCoordinates()
    {
        return new Point3f(barycentricCoordinates);
    }
    
    @Override
    public String toString()
    {
        return "TrianglePickingResult["+
            "pickedObject="+pickedObject+","+
            "distance="+distance+","+
            "triangleIndex="+triangleIndex+","+
            "barycentricCoordinates="+barycentricCoordinates+"]";
    }
}
