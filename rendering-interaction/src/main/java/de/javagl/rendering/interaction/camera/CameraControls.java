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


import java.util.Objects;

import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.core.view.View;
import de.javagl.rendering.interaction.Control;

/**
 * Methods to create {@link Control} instances that control the 
 * {@link Camera} of a {@link View}
 */
public class CameraControls
{
    /**
     * Creates a new {@link Control} that allows controlling the 
     * {@link Camera} in the given {@link View} an arcball style.<br>
     * <br>
     * <b>Note</b>: Many details about the behavior of the returned 
     * {@link Control} (like rotation-, translation- and zooming speed) 
     * are intentionally <b>not specified</b>.<br>
     * <br>
     * However, one can assume that the control allows a "reasonable"
     * (and reasonably "intuitive") default navigation. The current 
     * implementation (which may change arbitrarily!) is:
     * <ul>
     *   <li>left mouse drags to rotate the camera</li>
     *   <li>right mouse drags to move the camera</li>
     *   <li>
     *     mouse wheel to zoom, by moving the eye point towards or away 
     *     from the view point
     *   </li>
     *   <li>middle mouse click to reset the camera</li>
     * </ul>
     * 
     * @param view The {@link View} that provides the 
     * {@link View#getViewport() viewport} and the 
     * {@link View#getCamera() camera}
     * @return The {@link Control}
     */
    public static Control createDefaultArcballControl(View view)
    {
        Objects.requireNonNull(view, "The view may not be null");
        ArcballCameraBehavior arcballCameraBehavior = 
            new ArcballCameraBehavior(view);
        ArcballCameraControl arcballCameraControl = 
            new ArcballCameraControl(arcballCameraBehavior);
        return arcballCameraControl;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private CameraControls()
    {
        // Private constructor to prevent instantiation
    }
}
