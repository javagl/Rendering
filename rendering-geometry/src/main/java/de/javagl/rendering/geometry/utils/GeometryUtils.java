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

import javax.vecmath.Matrix4f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Point4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;

import de.javagl.rendering.geometry.Array2f;
import de.javagl.rendering.geometry.Array3f;
import de.javagl.rendering.geometry.Array4f;
import de.javagl.rendering.geometry.Geometry;
import de.javagl.rendering.geometry.IntArray;
import de.javagl.rendering.geometry.MutableArray3f;

/**
 * A class offering utility methods for {@link Geometry} instances.<br>
 * <br>
 * Unless otherwise noted, none of the arguments for these methods
 * may be <code>null</code>.
 */
public class GeometryUtils
{
    // Note: Due to the improved escape analysis in Java 1.7, 
    // the temporary variables might be omitted
    
    // Temporary variables
    private static final Tuple2f t2f0 = new Point2f();
    private static final Tuple2f t2f1 = new Point2f();
    private static final Tuple2f t2f2 = new Point2f();

    // Temporary variables
    private static final Tuple3f t3f0 = new Point3f();
    private static final Tuple3f t3f1 = new Point3f();
    private static final Tuple3f t3f2 = new Point3f();

    // Temporary variables
    private static final Tuple4f t4f0 = new Point4f();
    private static final Tuple4f t4f1 = new Point4f();
    private static final Tuple4f t4f2 = new Point4f();
    
    /**
     * Transform the given {@link MutableArray3f} with the given matrix
     * 
     * @param array The {@link MutableArray3f}
     * @param matrix The matrix
     */
    public static void transform(MutableArray3f array, Matrix4f matrix)
    {
        Point3f point = new Point3f();
        for (int i=0; i<array.getSize(); i++)
        {
            array.get3f(i, point);
            matrix.transform(point);
            array.set3f(i, point);
        }
    }

    /**
     * Three consecutive entries of the indices {@link IntArray} describe 
     * one triangle. The indices of the specified triangle are used to 
     * access the elements of the given {@link Array2f}. The values at 
     * these elements are combined according to the given coefficients. 
     * The result of this combination is returned in the given tuple.
     * <br>
     * <br>
     * This method is not thread safe!
     * 
     * @param indices The indices
     * @param triangleIndex The triangle index
     * @param array The array 
     * @param coefficients The coefficients
     * @param result The linear combination
     */
    public static void linearCombination(
        IntArray indices, int triangleIndex, 
        Array2f array, Tuple3f coefficients, 
        Tuple2f result)
    {
        int i0 = indices.get(triangleIndex*3+0);
        int i1 = indices.get(triangleIndex*3+1);
        int i2 = indices.get(triangleIndex*3+2);
        array.get2f(i0, t2f0);
        array.get2f(i1, t2f1);
        array.get2f(i2, t2f2);
        result.scale(coefficients.x, t2f0);
        result.scaleAdd(coefficients.y, t2f1, result);
        result.scaleAdd(coefficients.z, t2f2, result);
    }
    
    /**
     * Three consecutive entries of the indices {@link IntArray} describe 
     * one triangle. The indices of the specified triangle are used to 
     * access the elements of the given {@link Array3f}. The values at 
     * these elements are combined according to the given coefficients. 
     * The result of this combination is returned in the given tuple.
     * <br>
     * <br>
     * This method is not thread safe!
     * 
     * @param indices The indices
     * @param triangleIndex The triangle index
     * @param array The array 
     * @param coefficients The coefficients
     * @param result The linear combination
     */
    public static void linearCombination(
        IntArray indices, int triangleIndex, 
        Array3f array, Tuple3f coefficients,
        Tuple3f result)
    {
        int i0 = indices.get(triangleIndex*3+0);
        int i1 = indices.get(triangleIndex*3+1);
        int i2 = indices.get(triangleIndex*3+2);
        array.get3f(i0, t3f0);
        array.get3f(i1, t3f1);
        array.get3f(i2, t3f2);
        result.scale(coefficients.x, t3f0);
        result.scaleAdd(coefficients.y, t3f1, result);
        result.scaleAdd(coefficients.z, t3f2, result);
    }
    
    /**
     * Three consecutive entries of the indices {@link IntArray} describe 
     * one triangle. The indices of the specified triangle are used to 
     * access the elements of the given {@link Array4f}. The values at 
     * these elements are combined according to the given coefficients. 
     * The result of this combination is returned in the given tuple.
     * <br>
     * <br>
     * This method is not thread safe!
     * 
     * @param indices The indices
     * @param triangleIndex The triangle index
     * @param array The array 
     * @param coefficients The coefficients
     * @param result The linear combination
     */
    public static void linearCombination(
        IntArray indices, int triangleIndex, 
        Array4f array, Tuple4f coefficients,
        Tuple4f result)
    {
        int i0 = indices.get(triangleIndex*3+0);
        int i1 = indices.get(triangleIndex*3+1);
        int i2 = indices.get(triangleIndex*3+2);
        array.get4f(i0, t4f0);
        array.get4f(i1, t4f1);
        array.get4f(i2, t4f2);
        result.scale(coefficients.x, t4f0);
        result.scaleAdd(coefficients.y, t4f1);
        result.scaleAdd(coefficients.z, t4f2);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private GeometryUtils()
    {
        // Private constructor to prevent instantiation
    }
}
