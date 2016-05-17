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


import java.awt.Point;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.core.view.CameraUtils;
import de.javagl.rendering.core.view.Cameras;
import de.javagl.rendering.core.view.Rectangle;
import de.javagl.rendering.core.view.View;

/**
 * This class controls the movement of the {@link Camera} of a {@link View} 
 * in a Trackball style. <br> 
 * <br>
 */
class TrackballCameraBehavior implements CameraBehavior 
{
    /**
     * A factor to compute the zoom from a mouse wheel rotation
     */
    private static final float ZOOMING_SPEED = 0.1f;
    
    /**
     * A factor to compute the rotation from a mouse movement
     */
    private static final float ROTATION_SPEED = 1.0f;
    
    /**
     * The view this behavior operates on
     */
    private final View view;

    /**
     * A {@link Camera} instance storing the initial camera configuration
     */
    private final Camera initialCamera;
    
    /**
     * The previous (mouse) position
     */
    private final Point previousPosition = new Point();

    /**
     * Creates a new TrackballCameraBehavior for the {@link Camera} in 
     * the given {@link View}
     * 
     * @param view The {@link View}
     */
    TrackballCameraBehavior(View view)
    {
        this.view = view;
        this.initialCamera = Cameras.create();
        Camera camera = view.getCamera();
        set(initialCamera, camera);
    }
    
    /**
     * Set the given target {@link Camera} to have the same configuration as
     * the given source {@link Camera}
     * 
     * @param target The target {@link Camera}
     * @param source The source {@link Camera}
     */
    private static void set(Camera target, Camera source)
    {
        target.setEyePoint(source.getEyePoint());
        target.setViewPoint(source.getViewPoint());
        target.setUpVector(source.getUpVector());
        target.setFovDegY(source.getFovDegY());
    }

    @Override
    public void reset()
    {
        Camera camera = view.getCamera();
        set(camera, initialCamera);
    }

    @Override
    public void startRotate(Point point)
    {
        previousPosition.setLocation(point);
    }

    @Override
    public void doRotate(Point point)
    {
        Camera camera = view.getCamera();

        int dx = point.x - previousPosition.x;
        int dy = point.y - previousPosition.y;
        
        Rectangle viewport = view.getViewport();
        float relDx = (float)dx / viewport.getWidth(); 
        float relDy = (float)dy / viewport.getHeight(); 
        
        Matrix4f rotX = MatrixUtils.rotX(-relDy * ROTATION_SPEED * Math.PI * 2);
        Matrix4f rotY = MatrixUtils.rotY(-relDx * ROTATION_SPEED * Math.PI * 2);
        
        Matrix4f rotation = CameraUtils.computeRotation(camera);
        rotation.mul(rotX);
        rotation.mul(rotY);
        
        Vector3f initialEyeToViewDirection = 
            CameraBehaviorUtils.computeEyeToViewVector(initialCamera);
        initialEyeToViewDirection.normalize();
        
        float currentEyeToViewDistance = 
            CameraBehaviorUtils.computeEyeToViewDistance(camera);
        
        rotation.transform(initialEyeToViewDirection);
        Point3f newEyePoint = new Point3f();
        newEyePoint.scaleAdd(-currentEyeToViewDistance, 
            initialEyeToViewDirection, camera.getViewPoint());
        
        Vector3f upVector = initialCamera.getUpVector();
        rotation.transform(upVector);
        
        camera.setEyePoint(newEyePoint);
        camera.setUpVector(upVector);
        
        previousPosition.setLocation(point);
    }

    @Override
    public void startMovement(Point point)
    {
        previousPosition.setLocation(point);
    }

    @Override
    public void doMovement(Point point)
    {
        Camera camera = view.getCamera();
        
        Vector3f delta = new Vector3f();
        delta.x = previousPosition.x - point.x;
        delta.y = point.y - previousPosition.y;
        
        Rectangle viewport = view.getViewport();
        delta.x /= viewport.getWidth();
        delta.y /= viewport.getHeight();
        
        float eyeToViewDistance = 
            CameraBehaviorUtils.computeEyeToViewDistance(camera);
        delta.scale(eyeToViewDistance);
        
        Matrix4f rotation = CameraUtils.computeRotation(camera);
        rotation.transform(delta);

        CameraBehaviorUtils.translate(camera, delta);
        
        previousPosition.setLocation(point);
    }
    
    @Override
    public void zoom(float amount)
    {
        Camera camera = view.getCamera();
        CameraBehaviorUtils.zoom(camera, amount * ZOOMING_SPEED);
    }

    @Override
    public void translateZ(float amount)
    {
        Camera camera = view.getCamera();
        CameraBehaviorUtils.translateZ(camera, amount * ZOOMING_SPEED);
    }


}
