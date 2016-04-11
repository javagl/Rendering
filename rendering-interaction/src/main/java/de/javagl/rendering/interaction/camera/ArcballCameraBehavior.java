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
import javax.vecmath.Quat4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.core.view.Rectangle;
import de.javagl.rendering.core.view.View;

/**
 * This class controls the movement of the {@link Camera} of a {@link View} 
 * in an Arcball style. <br> 
 * <br>
 * Usually this class will be controlled by a 
 * MouseListener/MouseMotionListener/MouseWheelListener
 * by calling the main public methods accordingly:
 * <ul>
 *   <li> 
 *     {@link #startArcballRotate(Point)}
 *      when the mouse is pressed to start a rotation
 *   </li>
 *   <li> 
 *      {@link #doArcballRotate(Point)}
 *      when the mouse is dragged to rotate the camera
 *   </li>
 *   <li> 
 *      {@link #startMovement(Point)}
 *      when the mouse is pressed to start a movement
 *   </li>
 *   <li> 
 *     {@link #startArcballRotate(Point)}
 *      when the mouse is pressed to start a rotation
 *   </li>
 *   <li> 
 *      {@link #doMovement(Point)}
 *      when the mouse is moved to move the camera
 *   </li>
 *   <li> 
 *      {@link #zoom(float)}
 *      when the mouse wheel is rotated to zoom
 *   </li>
 * </ul>
 * However, different applications of these methods may be implemented
 * by the client. <br>
 */
class ArcballCameraBehavior 
{
    /**
     * A factor to compute the zoom from a mouse rotation
     */
    private static final float ZOOMING_SPEED = 0.1f;
    
    /**
     * The view this behavior operates on
     */
    private final View view;

    /**
     * The initial eye position
     */
    private final Point3f initialEyePoint = new Point3f(0, 0, 5);
    
    /**
     * The initial view point
     */
    private final Point3f initialViewPoint = new Point3f(0, 0, 0);

    /**
     * The initial up vector
     */
    private final Vector3f initialUpVector = new Vector3f(0, 1, 0);

    /**
     * The initial FOV, in y direction, in degrees
     */
    private float initialFovDegrees = 60.0f;
    
    /**
     * The previous position
     */
    private final Point previousPosition = new Point();

    /**
     * The Quaternion describing the rotation when dragging started
     */
    private final Quat4f dragStartRotation = new Quat4f(0, 0, 0, 1);

    /**
     * The Quaternion describing the current rotation
     */
    private final Quat4f currentRotation = new Quat4f(0, 0, 0, 1);

    /**
     * The position in 3D space where dragging started
     */
    private final Vector3f dragStartPosition = new Vector3f();

    /**
     * The current position in 3D space
     */
    private final Vector3f currentDragPosition = new Vector3f();

    /**
     * Creates a new ArcballCameraBehavior for the {@link Camera} in 
     * the given {@link View}
     * 
     * @param view The {@link View}
     */
    ArcballCameraBehavior(View view)
    {
        this.view = view;
        reset();
    }

    /**
     * Returns the default eye point that is necessary to see the
     * whole height of the current viewport.
     * 
     * @return The default eye point
     */
//    public Point3f getDefaultViewEyePoint()
//    {
//        Point3f defaultViewEyePoint = new Point3f();
//        double halfFovRad = Math.toRadians(camera.getFovY() / 2);
//        defaultViewEyePoint.z = view.getViewport().height / 
//            (float) (2 * Math.tan(halfFovRad));
//        return defaultViewEyePoint;
//    }
    
    /**
     * Set the configuration that will be established when {@link #reset()} 
     * is called
     * 
     * @param eyePoint The initial eye point
     * @param viewPoint The initial view point
     * @param upVector The initial up vector
     * @param fovDegrees The FOV in degrees
     */
    void setInitialConfiguration(
        Tuple3f eyePoint, Tuple3f viewPoint, 
        Tuple3f upVector, float fovDegrees)
    {
        initialEyePoint.set(eyePoint);
        initialViewPoint.set(viewPoint);
        initialUpVector.set(upVector);
        initialFovDegrees = fovDegrees;
    }
    
    
    /**
     * Reset the camera to its initial configuration. This either is
     * the (unspecified) default configuration, or the one that was 
     * set with {@link #setInitialConfiguration}
     */
    public void reset()
    {
        //initialEyePoint.set(getDefaultViewEyePoint());
        Camera camera = view.getCamera();
        camera.setEyePoint(initialEyePoint);
        camera.setViewPoint(initialViewPoint);
        camera.setUpVector(initialUpVector);
        camera.setFovDegY(initialFovDegrees);
        currentRotation.set(0,0,0,1);
    }
    

    /**
     * Called when the arcball rotation starts at the given point
     * 
     * @param point The point where the arcball rotation starts, 
     * in screen coordinates
     */
    public void startArcballRotate(Point point)
    {
        mapOnArcball(point.x, point.y, dragStartPosition);
        dragStartRotation.set(currentRotation);
        //System.out.println("start " + dragStartPosition);
    }

    /**
     * Called when the arcball rotation continues to the given point
     * 
     * @param point The current mouse position, screen coordinates
     */
    public void doArcballRotate(Point point)
    {
        mapOnArcball(point.x, point.y, currentDragPosition);
        float dot = dragStartPosition.dot(currentDragPosition);
        Vector3f tmp = new Vector3f();
        tmp.cross(dragStartPosition, currentDragPosition);

        //System.out.println("currentRotation "+currentRotation);

        Quat4f q = new Quat4f(tmp.x, tmp.y, tmp.z, dot);
        currentRotation.mul(q, dragStartRotation);

        updateRotation();
    }

    /**
     * Update the eyePoint and upVector according to the current rotation
     */
    private void updateRotation()
    {
        Camera camera = view.getCamera();

        Matrix4f currentMatrix = new Matrix4f();
        currentMatrix.set(currentRotation);
        currentMatrix.transpose();
        
        //System.out.println(currentMatrix);
        
        Point3f currentEyePoint = camera.getEyePoint();
        Point3f currentViewPoint = camera.getViewPoint();
        float viewToEyeDistance = currentViewPoint.distance(currentEyePoint);
        
        Vector3f viewToEye = new Vector3f();
        viewToEye.sub(initialEyePoint, initialViewPoint);
        viewToEye.normalize();
        viewToEye.scale(viewToEyeDistance);
        currentMatrix.transform(viewToEye);
        
        Vector3f upVector = camera.getUpVector();
        currentMatrix.transform(initialUpVector, upVector);
        camera.setUpVector(upVector);
        
        Point3f eyePoint = new Point3f();
        eyePoint.add(camera.getViewPoint(), viewToEye);
        camera.setEyePoint(eyePoint);
    }

    /**
     * Maps the given point onto the arcball
     * 
     * @param x The x-screen coordinate of the point
     * @param y The y-screen coordinate of the point
     * @param mappedPoint The point on the arcball in 3D
     */
    private void mapOnArcball(int x, int y, Vector3f mappedPoint)
    {
        Vector2f temp = new Vector2f();
        Rectangle viewport = view.getViewport();
        temp.x = ((float)x / (viewport.getWidth() / 2)) - 1.0f;
        temp.y = -(((float)y / (viewport.getHeight() / 2)) - 1.0f);
        float length = temp.length();
        if (length > 1.0f)
        {
            mappedPoint.x = temp.x / length;
            mappedPoint.y = temp.y / length;
            mappedPoint.z = 0.0f;
        }
        else
        {
            mappedPoint.x = temp.x;
            mappedPoint.y = temp.y;
            mappedPoint.z = (float) Math.sqrt(1.0f - length);
        }
    }

    /**
     * Called when the movement starts at the given point
     * 
     * @param point The point where the movement starts, in screen coordinates
     */
    public void startMovement(Point point)
    {
        previousPosition.setLocation(point);
    }

    /**
     * Called when the movement continues to the given point
     * 
     * @param point The current mouse position, screen coordinates
     */
    public void doMovement(Point point)
    {
        Camera camera = view.getCamera();
        
        Vector3f delta = new Vector3f();
        delta.x = previousPosition.x - point.x;
        delta.y = point.y - previousPosition.y;
        
        Rectangle viewport = view.getViewport();
        delta.x /= viewport.getWidth();
        delta.y /= viewport.getHeight();
        
        Point3f currentEyePoint = camera.getEyePoint();
        Point3f currentViewPoint = camera.getViewPoint();
        float viewToEyeDistance = currentViewPoint.distance(currentEyePoint);
        delta.scale(viewToEyeDistance);

        Matrix4f currentMatrix = new Matrix4f();
        currentMatrix.setIdentity();
        currentMatrix.set(currentRotation);
        currentMatrix.transpose();
        currentMatrix.transform(delta);

        Point3f eyePoint = camera.getEyePoint();
        eyePoint.add(delta);
        camera.setEyePoint(eyePoint);
        
        Point3f viewPoint = camera.getViewPoint();
        viewPoint.add(delta);
        camera.setViewPoint(viewPoint);
        
        previousPosition.setLocation(point);
    }
    
    
    /**
     * Zoom by the given amount. This will move the eye position
     * towards or away from the view position.
     * 
     * @param amount The zoom amount
     */
    public void zoom(float amount)
    {
        Camera camera = view.getCamera();
        
        Vector3f delta = new Vector3f();
        Point3f currentEyePoint = camera.getEyePoint();
        Point3f currentViewPoint = camera.getViewPoint();
        float viewToEyeDistance = currentViewPoint.distance(currentEyePoint);
        delta.z = -amount * viewToEyeDistance * ZOOMING_SPEED;
        
        delta.z = Math.max(delta.z, - viewToEyeDistance + 0.001f);
        
        Matrix4f currentMatrix = new Matrix4f();
        currentMatrix.setIdentity();
        currentMatrix.set(currentRotation);
        currentMatrix.transpose();
        currentMatrix.transform(delta);

        Point3f eyePoint = camera.getEyePoint();
        eyePoint.add(delta);
        camera.setEyePoint(eyePoint);
        
    }


}
