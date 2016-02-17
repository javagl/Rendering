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
 * Interface describing a bounding box. All methods if this interface
 * return a copy of the internal state. Instances of bounding boxes
 * may be created with the {@link BoundingBoxes} class.
 */
public interface BoundingBox
{
    /**
     * Returns the minimum point of this bounding box.
     * 
     * @return The minimum point of this bounding box
     */
    Point3f getMin();

    /**
     * Returns the minimum x value of this bounding box
     * 
     * @return The minimum x value of this bounding box
     */
    float getMinX();

    /**
     * Returns the minimum y value of this bounding box
     * 
     * @return The minimum y value of this bounding box
     */
    float getMinY();

    /**
     * Returns the minimum z value of this bounding box
     * 
     * @return The minimum z value of this bounding box
     */
    float getMinZ();

    /**
     * Returns the maximum x value of this bounding box
     * 
     * @return The maximum x value of this bounding box
     */
    float getMaxX();

    /**
     * Returns the maximum y value of this bounding box
     * 
     * @return The maximum y value of this bounding box
     */
    float getMaxY();

    /**
     * Returns the maximum z value of this bounding box
     * 
     * @return The maximum z value of this bounding box
     */
    float getMaxZ();

    /**
     * Returns the maximum point of this bounding box.
     * 
     * @return The maximum point of this bounding box
     */
    Point3f getMax();

    /**
     * Returns the size of this bounding box in x direction
     *  
     * @return The size of this bounding box in x direction
     */
    float getSizeX();

    /**
     * Returns the size of this bounding box in y direction
     *  
     * @return The size of this bounding box in y direction
     */
    float getSizeY();

    /**
     * Returns the size of this bounding box in z direction
     *  
     * @return The size of this bounding box in z direction
     */
    float getSizeZ();
    
    /**
     * Returns the size of this bounding box
     * 
     * @return The size of this bounding box
     */
    Vector3f getSize();

    /**
     * Returns the center point of this bounding box.
     * 
     * @return The center of this bounding box
     */
    Point3f getCenter();

}