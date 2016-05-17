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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


/**
 * Default implementation of a {@link Camera}.
 */
class DefaultCamera implements Camera
{
    /**
     * The current eye position
     */
    private final Point3f eyePoint;

    /**
     * The current view point
     */
    private final Point3f viewPoint; 

    /**
     * The current up vector
     */
    private final Vector3f upVector;

    /**
     * The current FOV in y-direction, in degrees
     */
    private float fovDegree;

    /**
     * The list of listeners attached to this camera
     */
    private final List<CameraListener> cameraListeners;

    /**
     * Creates a new DefaultCamera with an eyePoint at (0,0,1),
     * a viewPoint of (0,0,0), an upVector of (0,1,0) and a
     * fov of 60 degrees.
     */
    DefaultCamera()
    {
        eyePoint = new Point3f(0, 0, 1);
        viewPoint = new Point3f(0, 0, 0); 
        upVector = new Vector3f(0, 1, 0);
        fovDegree = 60.0f;

        cameraListeners = new CopyOnWriteArrayList<CameraListener>();
    }

    @Override
    public Point3f getEyePoint()
    {
        return new Point3f(eyePoint);
    }

    @Override
    public Point3f getViewPoint()
    {
        return new Point3f(viewPoint);
    }

    @Override
    public Vector3f getUpVector()
    {
        return new Vector3f(upVector);
    }

    @Override
    public float getFovDegY()
    {
        return fovDegree;
    }

    @Override
    public void setEyePoint(Tuple3f point)
    {
        if (!eyePoint.equals(point))
        {
            eyePoint.set(point);
            notifyCameraChanged();
        }
    }

    @Override
    public void setViewPoint(Tuple3f point)
    {
        if (!viewPoint.equals(point))
        {
            viewPoint.set(point);
            notifyCameraChanged();
        }
    }

    @Override
    public void setUpVector(Tuple3f vector)
    {
        if (!upVector.equals(vector))
        {
            upVector.set(vector);
            notifyCameraChanged();
        }
    }
    
    @Override
    public void setFovDegY(float degrees)
    {
        if (fovDegree != degrees)
        {
            fovDegree = degrees;
            notifyCameraChanged();
        }
    }

    /**
     * Notify all listeners about a camera change
     */
    protected void notifyCameraChanged()
    {
        for (CameraListener cameraListener : cameraListeners)
        {
            cameraListener.cameraChanged(this);
        }
    }
    
    @Override
    public void addCameraListener(CameraListener cameraListener)
    {
        cameraListeners.add(cameraListener);
    }

    @Override
    public void removeCameraListener(CameraListener cameraListener)
    {
        cameraListeners.remove(cameraListener);
    }

}
