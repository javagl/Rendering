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
 * A simple implementation of a {@link BoundingBox}
 */
class DefaultBoundingBox implements BoundingBox
{
    /**
     * The minimum x value
     */
    private final float minX;

    /**
     * The minimum y value
     */
    private final float minY;

    /**
     * The minimum z value
     */
    private final float minZ;

    /**
     * The maximum x value
     */
    private final float maxX;

    /**
     * The maximum y value
     */
    private final float maxY;

    /**
     * The maximum z value
     */
    private final float maxZ;

    /**
     * Creates a new {@link BoundingBox} with the given size
     * 
     * @param minX The minimum x value
     * @param minY The minimum y value
     * @param minZ The minimum z value
     * @param maxX The maximum x value
     * @param maxY The maximum y value
     * @param maxZ The maximum z value
     */
    DefaultBoundingBox(
        float minX, float minY, float minZ, 
        float maxX, float maxY, float maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    /**
     * Creates a new {@link BoundingBox} with the given minimum
     * and maximum point
     * 
     * @param min The minimum point
     * @param max The maximum point
     */
    DefaultBoundingBox(Tuple3f min, Tuple3f max)
    {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    @Override
    public float getMinX()
    {
        return minX;
    }

    @Override
    public float getMinY()
    {
        return minY;
    }

    @Override
    public float getMinZ()
    {
        return minZ;
    }

    @Override
    public float getMaxX()
    {
        return maxX;
    }

    @Override
    public float getMaxY()
    {
        return maxY;
    }

    @Override
    public float getMaxZ()
    {
        return maxZ;
    }

    @Override
    public Point3f getMin()
    {
        return new Point3f(minX, minY, minZ);
    }

    @Override
    public Point3f getMax()
    {
        return new Point3f(maxX, maxY, maxZ);
    }

    @Override
    public float getSizeX()
    {
        return maxX-minX;
    }

    @Override
    public float getSizeY()
    {
        return maxY-minY;
    }
    
    @Override
    public float getSizeZ()
    {
        return maxZ-minZ;
    }
    
    @Override
    public Vector3f getSize()
    {
        return new Vector3f(
            maxX - minX,
            maxY - minY,
            maxZ - minZ);
    }

    @Override
    public Point3f getCenter()
    {
        return new Point3f(
            (minX + maxX) * 0.5f,
            (minY + maxY) * 0.5f,
            (minZ + maxZ) * 0.5f);
    }
    
    @Override
    public String toString()
    {
        return "BoundingBox[" +
        	"(" + minX + "," + minY + "," + minZ + ")-" +
        	"(" + maxX + "," + maxY + "," + maxZ + ")]";
    }

}
