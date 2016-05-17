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
package de.javagl.rendering.interaction.camera;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.view.Camera;

/**
 * Utility methods related to camera behaviors
 */
class CameraBehaviorUtils
{
    /**
     * Computes the eye-to-view vector (non-normalized viewing direction)
     * of the given {@link Camera}
     * 
     * @param camera The {@link Camera}
     * @return The eye-to-view vector
     */
    static Vector3f computeEyeToViewVector(Camera camera)
    {
        Vector3f eyeToView = new Vector3f();
        eyeToView.sub(camera.getViewPoint(), camera.getEyePoint());
        return eyeToView;
    }

    /**
     * Computes the eye-to-view distance of the given {@link Camera}
     * 
     * @param camera The {@link Camera}
     * @return The eye-to-view vector
     */
    static float computeEyeToViewDistance(Camera camera)
    {
        Point3f viewPoint = camera.getViewPoint();
        Point3f eyePoint = camera.getEyePoint();
        return eyePoint.distance(viewPoint);
    }
    
    /**
     * Zoom the given {@link Camera} by the given amount, by translating
     * the eye point along the viewing direction
     * 
     * @param camera The {@link Camera}
     * @param amount The amount
     */
    static void zoom(Camera camera, float amount)
    {
        Vector3f eyeToView = computeEyeToViewVector(camera);
        float eyeToViewDistance = eyeToView.length();
        final float minDistance = 0.0001f;
        if (amount > 0 && eyeToViewDistance <= minDistance)
        {
            return;
        }
        Vector3f delta = new Vector3f(eyeToView);
        delta.scale(amount);
        Point3f eyePoint = camera.getEyePoint();
        eyePoint.add(delta);
        camera.setEyePoint(eyePoint);
    }

    /**
     * Translate the given {@link Camera} by the given amount along its
     * viewing direction
     * 
     * @param camera The {@link Camera}
     * @param amount The amount
     */
    static void translateZ(Camera camera, float amount)
    {
        Vector3f delta = computeEyeToViewVector(camera);
        delta.scale(amount);
        translate(camera, delta);
    }

    /**
     * Translate the given {@link Camera} by the given delta
     * 
     * @param camera The camera
     * @param delta The delta
     */
    static void translate(Camera camera, Tuple3f delta)
    {
        Point3f eyePoint = camera.getEyePoint();
        eyePoint.add(delta);
        camera.setEyePoint(eyePoint);
        
        Point3f viewPoint = camera.getViewPoint();
        viewPoint.add(delta);
        camera.setViewPoint(viewPoint);
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private CameraBehaviorUtils()
    {
        // Private constructor to prevent instantiation
    }
    
}
