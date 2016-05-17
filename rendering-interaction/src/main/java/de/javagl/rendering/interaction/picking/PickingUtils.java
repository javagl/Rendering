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

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.core.view.CameraUtils;
import de.javagl.rendering.core.view.Rectangle;
import de.javagl.rendering.core.view.View;
import de.javagl.rendering.geometry.Ray;
import de.javagl.rendering.geometry.Rays;

/**
 * Utility methods for picking
 */
public class PickingUtils
{
    
    /**
     * Compute a picking {@link Ray} from the given screen coordinates
     * based on the given {@link View}.
     * 
     * @param view The {@link View}
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The picking {@link Ray}.
     */
    public static Ray computePickingRay(View view, int x, int y)
    {
        Matrix4f viewMatrix = 
            CameraUtils.computeViewMatrix(view.getCamera());
        Matrix4f projectionMatrix = view.getProjectionMatrix();
        Rectangle viewport = view.getViewport();
        return computePickingRay(
            viewMatrix, projectionMatrix, viewport, x, y);
    }


    /**
     * Compute a picking {@link Ray} given the view matrix, projection 
     * matrix, viewport and position. The view matrix is the inverse
     * of the camera matrix, which contains the rotation and translation 
     * of the camera. The projectionMatrix contains only the perspective 
     * projection transformation. The viewport describes the rectangle 
     * that the picking occurs in. The x and y coordinates are the 
     * position where the picking ray should start, in screen coordinates.
     * 
     * @param viewMatrix The view matrix
     * @param projectionMatrix The projection matrix
     * @param viewport The viewport
     * @param x The x position
     * @param y The y position
     * @return The picking ray
     */
    public static Ray computePickingRay(
        Matrix4f viewMatrix, Matrix4f projectionMatrix, 
        Rectangle viewport, int x, int y)
    {
        int fy = viewport.getHeight() - y;
        Point3f p0 = MatrixUtils.unProject(
            new Point3f(x,fy,0), viewMatrix, projectionMatrix, viewport); 
        Point3f p1 = MatrixUtils.unProject(
            new Point3f(x,fy,1), viewMatrix, projectionMatrix, viewport); 
    
        Point3f rayOrigin = p0;
        Vector3f rayDirection = new Vector3f();
        rayDirection.sub(p1, p0);
        rayDirection.normalize();
    
        Ray ray = Rays.create(rayOrigin, rayDirection);
        //System.out.println("Ray "+ray);
        return ray;
    }


    /**
     * Private constructor to prevent instantiation
     */
    private PickingUtils()
    {
        // Private constructor to prevent instantiation
    }
}
