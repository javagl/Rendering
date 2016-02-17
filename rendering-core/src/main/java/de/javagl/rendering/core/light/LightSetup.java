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

package de.javagl.rendering.core.light;

import java.util.List;

import javax.vecmath.Color4f;
import javax.vecmath.Tuple4f;

/**
 * This interface describes the lighting setup. It consists of a global 
 * ambient light and a collection of {@link Light}s.
 */
public interface LightSetup
{
    /**
     * Returns the global ambient light color
     * 
     * @return The global ambient light color
     */
    Color4f getAmbient();

    /**
     * Set the global ambient light color
     * 
     * @param color The global ambient light color
     */
    void setAmbient(Tuple4f color);
    
    /**
     * Returns the maximum number of {@link Light}s that
     * may be contained in this setup
     * 
     * @return The maximum number of lights
     */
    int getMaximumNumberOfLights();
    
    /**
     * Returns the number of {@link Light}s that are currently contained
     * in this setup
     * 
     * @return The number of lights
     */
    int getNumberOfLights();

    /**
     * Add the given Light to this setup. 
     * 
     * @param light The light to add
     * @throws IllegalStateException If the maximum number
     * of lights is exceeded, as specified by 
     * {@link #getMaximumNumberOfLights()}
     */
    void addLight(Light light);

    /**
     * Removes the {@link Light} with the given Index from 
     * this setup.
     * 
     * @param index The index of the Light to remove.
     */
    void removeLight(int index);

    /**
     * Removes the given {@link Light} from this setup.
     * 
     * @param light The Light to remove.
     */
    void removeLight(Light light);

    /**
     * Removes all lights from this LightSetup
     */
    void clear();

    /**
     * Returns an unmodifiable copy of the current list
     * of lights contained in this setup.
     * 
     * @return The current list of lights
     */
    List<Light> getLights();
    
    /**
     * Add the given {@link LightSetupListener} to be informed about
     * changes in this {@link LightSetup}
     * 
     * @param lightSetupListener The listener to add
     */
    void addLightSetupListener(LightSetupListener lightSetupListener);

    /**
     * Remove the given {@link LightSetupListener}
     * 
     * @param lightSetupListener The listener to remove
     */
    void removeLightSetupListener(LightSetupListener lightSetupListener);

}