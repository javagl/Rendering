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

package de.javagl.rendering.core.view;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.utils.MatrixUtils;

/**
 * A class containing utility methods for {@link Camera} operations.
 */
public class CameraUtils
{
    /**
     * Returns the view matrix for the given {@link Camera}. The orientation 
     * described by the matrix will be taken from the view point, eye 
     * point and up vector, and the translation will be computed from 
     * the eye point. <br> 
     * <br>
     * This method returns the inverse of the matrix obtained with
     * {@link #computeCameraMatrix(Camera)}. 
     * 
     * @param camera The {@link Camera}
     * @return The view matrix
     */
    public static Matrix4f computeViewMatrix(Camera camera)
    {
        Point3f eye = camera.getEyePoint();
        Point3f center = camera.getViewPoint();
        Vector3f up = camera.getUpVector();
        Matrix4f lookAtMatrix = MatrixUtils.setLookAt(eye, center, up, null);

        Vector3f negEye = new Vector3f(eye);
        negEye.negate();
        Matrix4f translationMatrix = MatrixUtils.translation(negEye);

        Matrix4f viewMatrix = MatrixUtils.mul(
            lookAtMatrix, translationMatrix);

        return viewMatrix;
    }
    
    /**
     * Compute the rotation matrix that describes the orientation of 
     * the given {@link Camera}, based on its eye point, view point
     * and up vector
     * 
     * @param camera The {@link Camera}
     * @return The rotation matrix
     */
    public static Matrix4f computeRotation(Camera camera)
    {
        Point3f eye = camera.getEyePoint();
        Point3f view = camera.getViewPoint();
        Vector3f up = camera.getUpVector();

        Vector3f z = new Vector3f();
        z.sub(eye, view);
        z.normalize();
        Vector3f x = new Vector3f();
        x.cross(up, z);
        x.normalize();
        Vector3f y = new Vector3f();
        y.cross(z, x);
        y.normalize();

        Matrix4f rotation = new Matrix4f();
        rotation.setIdentity();
        rotation.setColumn(0, x.x, x.y, x.z, 0.0f);
        rotation.setColumn(1, y.x, y.y, y.z, 0.0f);
        rotation.setColumn(2, z.x, z.y, z.z, 0.0f);
        return rotation;
    }
    
    
    /**
     * Returns the camera matrix for the given {@link Camera}. The orientation 
     * described by the matrix will be taken from the view point, eye 
     * point and up vector, and the translation will be computed from 
     * the eye point. <br> 
     * <br>
     * This method returns the inverse of the matrix obtained with
     * {@link #computeViewMatrix(Camera)}. 
     * 
     * @param camera The {@link Camera}
     * @return The camera matrix
     */
    public static Matrix4f computeCameraMatrix(Camera camera)
    {
        Matrix4f viewMatrix = computeViewMatrix(camera);
        Matrix4f cameraMatrix = MatrixUtils.inverse(viewMatrix);
        return cameraMatrix;
    }


    /**
     * Private constructor to prevent instantiation
     */
    private CameraUtils()
    {
        // Private constructor to prevent instantiation
    }
}
