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

/**
 * Interface describing a View, consisting of a {@link Camera}, a viewport 
 * configuration and a perspective transformation.
 */
public interface View
{
    /**
     * Returns the {@link Camera} of this {@link View}
     * 
     * @return The {@link Camera} 
     */
    Camera getCamera();

    /**
     * Returns a copy of the current projection matrix
     * 
     * @return The projection matrix
     */
    Matrix4f getProjectionMatrix();
    
    /**
     * Returns the current aspect ratio of this {@link View}
     * 
     * @return The aspect ratio
     */
    float getAspect();
    
    /**
     * Set the aspect ratio of this {@link View}
     * 
     * @param aspect The aspect ratio
     */
    void setAspect(float aspect);
    
    /**
     * Set the near plane of this {@link View}
     * 
     * @param near The near plane
     */
    void setNearClippingPlane(float near);
    
    /**
     * Return the near plane of this {@link View}
     * 
     * @return The near plane
     */
    float getNearClippingPlane();

    /**
     * Set the far plane of this {@link View}
     * 
     * @param far The far plane
     */
    void setFarClippingPlane(float far);

    /**
     * Return the far plane of this {@link View}
     * 
     * @return The far plane
     */
    float getFarClippingPlane();
    
    /**
     * Returns a the current viewport of this {@link View}
     * 
     * @return The viewport
     */
    Rectangle getViewport();
    
    /**
     * Set the current viewport of this {@link View}
     * 
     * @param viewport The viewport
     */
    void setViewport(Rectangle viewport);
    
    /**
     * Add the given {@link ViewListener} to this {@link View}
     * 
     * @param viewListener The {@link ViewListener} to add
     */
    void addViewListener(ViewListener viewListener);

    /**
     * Remove the given {@link ViewListener} from this {@link View}
     * 
     * @param viewListener The {@link ViewListener} to remove
     */
    void removeViewListener(ViewListener viewListener);
}
