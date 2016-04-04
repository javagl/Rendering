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

package de.javagl.rendering.core.utils;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Point4f;
import javax.vecmath.SingularMatrixException;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.view.Rectangle;

/**
 * Matrix utility methods
 */
public class MatrixUtils
{
    /**
     * Write the contents of the given matrix into the given buffer
     * in column-major order and returns it. It is assumed that the 
     * given buffer has enough space for the matrix. If the given 
     * buffer is <code>null</code>, a new buffer will be created 
     * and returned. The position of the buffer will afterwards
     * be the same as before this call. 
     * 
     * @param matrix The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the matrix data
     */
    public static FloatBuffer writeMatrixToBuffer(
        Matrix3f matrix, FloatBuffer buffer)
    {
        FloatBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createFloatBuffer(9);
        }
        int oldPosition = result.position();
        result.put(matrix.m00);
        result.put(matrix.m10);
        result.put(matrix.m20);
        result.put(matrix.m01);
        result.put(matrix.m11);
        result.put(matrix.m21);
        result.put(matrix.m02);
        result.put(matrix.m12);
        result.put(matrix.m22);
        result.position(oldPosition);
        return result;
    }
    
    
    /**
     * Write the contents of the given matrix into the given buffer
     * in column-major order and returns it. It is assumed that the 
     * given buffer has enough space for the matrix. If the given 
     * buffer is <code>null</code>, a new buffer will be created 
     * and returned. The position of the buffer will afterwards
     * be the same as before this call.
     * 
     * @param matrix The matrix
     * @param buffer The buffer to write to
     * @return The given buffer, now containing the matrix data
     */
    public static FloatBuffer writeMatrixToBuffer(
        Matrix4f matrix, FloatBuffer buffer)
    {
        FloatBuffer result = buffer;
        if (result == null)
        {
            result = BufferUtils.createFloatBuffer(16);
        }
        int oldPosition = result.position();
        result.put(matrix.m00);
        result.put(matrix.m10);
        result.put(matrix.m20);
        result.put(matrix.m30);
        result.put(matrix.m01);
        result.put(matrix.m11);
        result.put(matrix.m21);
        result.put(matrix.m31);
        result.put(matrix.m02);
        result.put(matrix.m12);
        result.put(matrix.m22);
        result.put(matrix.m32);
        result.put(matrix.m03);
        result.put(matrix.m13);
        result.put(matrix.m23);
        result.put(matrix.m33);
        result.position(oldPosition);
        return result;
    }


    
    /**
     * Write the contents of the given buffer into the given matrix
     * in column-major order and returns it. It is assumed that the 
     * given buffer has enough remaining data to fill the matrix. 
     * If the given matrix is <code>null</code>, a new matrix is 
     * created and returned. The position of the buffer will 
     * afterwards be the same as before this call.
     * 
     * @param buffer The buffer to read from
     * @param matrix The matrix to fill
     * @return The given matrix, now containing the buffer data
     */
    public static Matrix4f writeBufferToMatrix(
        FloatBuffer buffer, Matrix4f matrix)
    {
        Matrix4f result = matrix;
        if (result == null)
        {
            result = new Matrix4f();
        }
        int oldPosition = buffer.position();
        result.m00 = buffer.get();
        result.m10 = buffer.get();
        result.m20 = buffer.get();
        result.m30 = buffer.get();
        result.m01 = buffer.get();
        result.m11 = buffer.get();
        result.m21 = buffer.get();
        result.m31 = buffer.get();
        result.m02 = buffer.get();
        result.m12 = buffer.get();
        result.m22 = buffer.get();
        result.m32 = buffer.get();
        result.m03 = buffer.get();
        result.m13 = buffer.get();
        result.m23 = buffer.get();
        result.m33 = buffer.get();
        buffer.position(oldPosition);
        return result;
    }

    /**
     * Write the contents of the given buffer into the given matrix
     * in column-major order and returns it. It is assumed that the 
     * given buffer has enough remaining data to fill the matrix. 
     * If the given matrix is <code>null</code>, a new matrix is 
     * created and returned. The position of the buffer will 
     * afterwards be the same as before this call. 
     * 
     * @param buffer The buffer to read from
     * @param matrix The matrix to fill
     * @return The given matrix, now containing the buffer data
     */
    public static Matrix3f writeBufferToMatrix(
        FloatBuffer buffer, Matrix3f matrix)
    {
        Matrix3f result = matrix;
        if (result == null)
        {
            result = new Matrix3f();
        }
        int oldPosition = buffer.position();
        result.m00 = buffer.get();
        result.m10 = buffer.get();
        result.m20 = buffer.get();
        result.m01 = buffer.get();
        result.m11 = buffer.get();
        result.m21 = buffer.get();
        result.m02 = buffer.get();
        result.m12 = buffer.get();
        result.m22 = buffer.get();
        buffer.position(oldPosition);
        return result;
    }

    /**
     * Set the given matrix to be a perspective matrix with the given
     * parameters, and return the matrix. If the matrix is <code>null</code>,
     * a new matrix is created.
     * 
     * @param fovDegY The FOV in y-direction, in degrees
     * @param aspect The aspect ratio
     * @param zNear The near clipping plane.
     * @param zFar The far clipping plane
     * @param m The (optional) matrix to fill.
     * @return The matrix m, filled appropriately
     */
    public static Matrix4f setPerspective(
        double fovDegY, double aspect, double zNear, double zFar, Matrix4f m)
    {
        Matrix4f result = m;
        if (result == null)
        {
            result = new Matrix4f();
        }
        double radians = Math.toRadians(fovDegY / 2);
        double deltaZ = zFar - zNear;
        double sine = Math.sin(radians);
        if ((deltaZ == 0) || (sine == 0) || (aspect == 0))
        {
            return result;
        }
        double cotangent = Math.cos(radians) / sine;

        result.setIdentity();

        result.m00 = (float)(cotangent / aspect);
        result.m11 = (float)cotangent;
        result.m22 = (float)(-(zFar + zNear) / deltaZ);
        result.m32 = -1;
        result.m23 = (float)(-2 * zNear * zFar / deltaZ);
        result.m33 = 0;
        
        return result;
    }
    
    /**
     * Set the given matrix to be a look-at-transformation matrix, 
     * and returns it. If the given matrix is <code>null</code>, 
     * a new matrix is created.
     * 
     * @param eye The eye position
     * @param center The view center
     * @param up The up vector
     * @param m The (optional) matrix to fill
     * @return The matrix, filled appropriately
     */
    public static Matrix4f setLookAt(
        Tuple3f eye, Tuple3f center, Tuple3f up, Matrix4f m)
    {
        Matrix4f result = m;
        if (result == null)
        {
            result = new Matrix4f();
        }

        Vector3f forward = new Vector3f();
        Vector3f side = new Vector3f();
        Vector3f upV = new Vector3f(up);

        forward.sub(center, eye);
        
        forward.normalize();
        side.cross(forward, upV);
        side.normalize();
        upV.cross(side, forward);

        result.setIdentity();
        
        result.m00 = side.x;
        result.m01 = side.y;
        result.m02 = side.z;

        result.m10 = upV.x;
        result.m11 = upV.y;
        result.m12 = upV.z;

        result.m20 = -forward.x;
        result.m21 = -forward.y;
        result.m22 = -forward.z;

        return result;
    }
    
    
    /**
     * Unprojects the given point (in screen coordinates) based on the
     * given modelview (camera) and projection matrix and the viewport. 
     * 
     * @param point The point
     * @param modelview The modelview matrix
     * @param projection The projection matrix
     * @param viewport The current viewport
     * @return The unprojected point
     */
    public static Point3f unProject(
        Tuple3f point, Matrix4f modelview, 
        Matrix4f projection, Rectangle viewport)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.mul(projection, modelview);
        try
        {
            matrix.invert();
        }
        catch (SingularMatrixException e)
        {
            return new Point3f();
        }
        
        Point4f temp = new Point4f(point.x, point.y, point.z, 1);
        temp.x = (temp.x - viewport.getX()) / viewport.getWidth();
        temp.y = (temp.y - viewport.getY()) / viewport.getHeight();
        temp.x = temp.x * 2 - 1;
        temp.y = temp.y * 2 - 1;
        temp.z = temp.z * 2 - 1;

        matrix.transform(temp);
        Point3f result = new Point3f(temp.x, temp.y, temp.z);
        result.scale(1.0f / temp.w);
        return result;
    }
    
    /**
     * Creates and returns a matrix that is the inverse of
     * the given matrix. Returns an identity matrix if the
     * given matrix can not be inverted.
     * 
     * @param matrix The matrix to invert
     * @return The inverse of the given matrix
     */
    public static Matrix4f inverse(Matrix4f matrix)
    {
        return inverse(matrix, new Matrix4f());
    }
    
    /**
     * Sets the given result matrix to be the inverse of the given matrix.
     * Sets it to identity if the given matrix can not be inverted.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param matrix The matrix to invert
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f inverse(Matrix4f matrix, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        try
        {
            localResult.invert(matrix);
        }
        catch (SingularMatrixException e)
        {
            localResult.setIdentity();
        }
        return localResult;
    }
    
    /**
     * Creates and returns a matrix that is the transpose of
     * the given matrix. 
     * 
     * @param matrix The matrix to transpose
     * @return The transpose of the given matrix
     */
    public static Matrix4f transposed(Matrix4f matrix)
    {
        return transposed(matrix, new Matrix4f());
    }
    
    /**
     * Transposes the given matrix, and store the result in the given result 
     * matrix. 
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param matrix The matrix to transpose
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f transposed(Matrix4f matrix, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.transpose(matrix);
        return localResult;
    }
    
    
    
    /**
     * Creates and returns a scaling matrix.
     * 
     * @param s The scaling
     * @return The matrix
     */
    public static Matrix4f scaling(double s)
    {
        return scaling(s, new Matrix4f());
    }
    
    /**
     * Set the given result matrix to be a matrix describing the uniform scale
     * about the given factor. 
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param s The scaling
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f scaling(double s, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.setIdentity();
        localResult.setScale((float)s);
        return result;
    }
    
    
    /**
     * Creates and returns a rotation matrix.
     * 
     * @param angleRad The angle, about the x-axis, in radians
     * @return The matrix
     */
    public static Matrix4f rotX(double angleRad)
    {
        return rotX(angleRad, new Matrix4f());
    }
    
    /**
     * Set the given matrix to be a rotation matrix about the given angle.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param angleRad The angle, about the x-axis, in radians
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f rotX(double angleRad, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.setIdentity();
        localResult.rotX((float)angleRad);
        return localResult;
    }
    
    /**
     * Creates and returns a rotation matrix.
     * 
     * @param angleRad The angle, about the y-axis, in radians
     * @return The matrix
     */
    public static Matrix4f rotY(double angleRad)
    {
        return rotY(angleRad, new Matrix4f());
    }
    
    /**
     * Set the given matrix to be a rotation matrix about the given angle.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param angleRad The angle, about the y-axis, in radians
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f rotY(double angleRad, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.setIdentity();
        localResult.rotY((float)angleRad);
        return localResult;
    }

    /**
     * Creates and returns a rotation matrix.
     * 
     * @param angleRad The angle, about the z-axis, in radians
     * @return The matrix
     */
    public static Matrix4f rotZ(double angleRad)
    {
        return rotZ(angleRad, new Matrix4f());
    }
    
    /**
     * Set the given matrix to be a rotation matrix about the given angle.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param angleRad The angle, about the z-axis, in radians
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f rotZ(double angleRad, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.setIdentity();
        localResult.rotZ((float)angleRad);
        return localResult;
    }
    
    /**
     * Creates and returns a translation matrix.
     * 
     * @param t The translation
     * @return The matrix
     */
    public static Matrix4f translation(Tuple3f t)
    {
        return translation(t.x, t.y, t.z);
    }
    
    /**
     * Set the given matrix to be a translation matrix about the given vector.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param t The translation
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f translation(Tuple3f t, Matrix4f result)
    {
        return translation(t.x, t.y, t.z, result);
    }
    
    /**
     * Creates and returns a translation matrix.
     * 
     * @param dx The x-translation
     * @param dy The y-translation
     * @param dz The z-translation
     * @return The matrix
     */
    public static Matrix4f translation(double dx, double dy, double dz)
    {
        return translation(dx, dy, dz, new Matrix4f());
    }
    
    /**
     * Set the given matrix to be a translation matrix about the given vector.
     * If the given result matrix is <code>null</code>, then a new matrix will 
     * be created and returned.
     * 
     * @param dx The x-translation
     * @param dy The y-translation
     * @param dz The z-translation
     * @param result The matrix that will store the result
     * @return The result matrix
     */
    public static Matrix4f translation(
        double dx, double dy, double dz, Matrix4f result)
    {
        Matrix4f localResult = result == null ? new Matrix4f() : result;
        localResult.setIdentity();
        localResult.m03 = (float)dx;
        localResult.m13 = (float)dy;
        localResult.m23 = (float)dz;
        return result;
    }
    
    /**
     * Creates and returns an identity matrix
     * 
     * @return An identity matrix
     */
    public static Matrix4f identity()
    {
        Matrix4f result = new Matrix4f();
        result.setIdentity();
        return result;
    }
    
    
    /**
     * Returns the product of the given matrices
     * 
     * @param matrices The matrices
     * @return The product of the matrices
     */
    public static Matrix4f mul(Matrix4f ... matrices)
    {
        Matrix4f result = identity();
        for (Matrix4f m : matrices)
        {
            result.mul(m);
        }
        return result;
    }

    
    /**
     * Private constructor to prevent instantiation
     */
    private MatrixUtils()
    {
        // Private constructor to prevent instantiation
    }
    
}



