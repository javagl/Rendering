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

import javax.vecmath.Matrix4f;

import de.javagl.rendering.core.utils.MatrixUtils;

/**
 * Default implementation of a {@link View}
 */
class DefaultView implements View
{
    /**
     * The camera
     */
    private final Camera camera;

    /**
     * The current projection matrix
     */
    private final Matrix4f projectionMatrix;
    
    /**
     * The near clipping plane. 
     */
    private float nearClippingPlane;
    
    /**
     * The far clipping plane.
     */
    private float farClippingPlane;
    
    /**
     * The current aspect ratio
     */
    private float aspect;
    
    /**
     * The current viewport
     */
    private Rectangle viewport;
    
    /**
     * Whether the matrices have to be updated
     */
    private boolean updateRequired = true;

    /**
     * The List of {@link ViewListener} instances for this view 
     */
    private final List<ViewListener> viewListeners;
    
    /**
     * Creates a new DefaultView
     */
    DefaultView()
    {
        camera = Cameras.create();
        projectionMatrix = new Matrix4f();
        nearClippingPlane = 0.1f;
        farClippingPlane = 10000.0f;
        viewport = Rectangles.create(0, 0, 1, 1);
        aspect = 1.0f;
        viewListeners = new CopyOnWriteArrayList<ViewListener>();
    }
    
    
    /**
     * Update the projection matrix, if necessary
     */
    private void validate()
    {
        if (!updateRequired)
        {
            return;
        }
        
        MatrixUtils.setPerspective(
            camera.getFovDegY(), aspect, 
            nearClippingPlane, farClippingPlane, projectionMatrix);
        updateRequired = false;
    }
    
    
    @Override
    public Camera getCamera()
    {
        return camera;
    }
    
    
    @Override
    public Matrix4f getProjectionMatrix()
    {
        validate();
        return new Matrix4f(projectionMatrix);
    }

    @Override
    public void setAspect(float newAspect)
    {
        if (newAspect != aspect)
        {
            this.aspect = newAspect;
            updateRequired = true;
            notifyAspectChanged();
        }
    }

    @Override
    public float getAspect()
    {
        return aspect;
    }

    
    @Override
    public void setNearClippingPlane(float near)
    {
        if (this.nearClippingPlane != near)
        {
            this.nearClippingPlane = near;
            updateRequired = true;
            notifyClippingPlanesChanged();
        }
    }


    @Override
    public float getNearClippingPlane()
    {
        return nearClippingPlane;
    }


    @Override
    public void setFarClippingPlane(float far)
    {
        if (this.farClippingPlane != far)
        {
            this.farClippingPlane = far;
            updateRequired = true;
            notifyClippingPlanesChanged();
        }
    }


    @Override
    public float getFarClippingPlane()
    {
        return farClippingPlane;
    }
    

    @Override
    public Rectangle getViewport()
    {
        return viewport;
    }


    @Override
    public void setViewport(Rectangle viewport)
    {
        if (this.viewport.getX() != viewport.getX() ||
            this.viewport.getY() != viewport.getY() ||           
            this.viewport.getWidth() != viewport.getWidth() ||           
            this.viewport.getHeight() != viewport.getHeight())
        {
            this.viewport = Rectangles.create(viewport);
            notifyViewportChanged();
        }
    }

    /**
     * Notify all listeners about a change
     */
    protected void notifyClippingPlanesChanged()
    {
        for (ViewListener viewListener : viewListeners)
        {
            viewListener.clippingPlanesChanged(this);
        }
    }
    
    /**
     * Notify all listeners about a change
     */
    protected void notifyAspectChanged()
    {
        for (ViewListener viewListener : viewListeners)
        {
            viewListener.aspectChanged(this);
        }
    }
    

    /**
     * Notify all listeners about a change
     */
    protected void notifyViewportChanged()
    {
        for (ViewListener viewListener : viewListeners)
        {
            viewListener.viewportChanged(this);
        }
    }
    

    @Override
    public void addViewListener(ViewListener viewListener)
    {
        viewListeners.add(viewListener);
    }


    @Override
    public void removeViewListener(ViewListener viewListener)
    {
        viewListeners.remove(viewListener);
    }



}
