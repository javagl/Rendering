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


import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

/**
 * Interface describing a camera.<br>
 * <br>
 * All mutating methods may be assumed to make copies of the input
 * parameters, and all querying methods may be assumed to return
 * a copy of the internal state.
 */
public interface Camera
{
    /**
     * Set the eye point of the camera 
     *  
     * @param point The eye point of the camera
     */
    void setEyePoint(Tuple3f point);
    
    /**
     * Set the view point of the camera 
     *  
     * @param point The view point of the camera
     */
    void setViewPoint(Tuple3f point);
    
    /**
     * Set the up vector of the camera
     * 
     * @param vector The up vector of the camera
     */
    void setUpVector(Tuple3f vector);
    
    /**
     * Set the field-of-view (FOV) of the camera (in y-direction) to 
     * the given angle (in degrees)
     * 
     * @param degrees The FOV
     */
    void setFovDegY(float degrees);

    /**
     * Return the eye point of the camera
     * 
     * @return The eye point of the camera
     */
    Point3f getEyePoint();
    
    /**
     * Return the view point of the camera
     * 
     * @return The view point of the camera
     */
    Point3f getViewPoint();
    
    /**
     * Return the up vector of the camera
     * 
     * @return The up vector of the camera
     */
    Vector3f getUpVector();
    
    /**
     * Return the angle of the field-of-view (FOV) of the camera 
     * (in y-direction), in degrees
     * 
     * @return The FOV
     */
    float getFovDegY();
    
    /**
     * Add the given listener which will be notified when a 
     * property of this camera changes
     * 
     * @param cameraListener The listener to add
     */
    void addCameraListener(CameraListener cameraListener);

    /**
     * Remove the given listener from this camera
     * 
     * @param cameraListener The listener to remove
     */
    void removeCameraListener(CameraListener cameraListener);
    
}
