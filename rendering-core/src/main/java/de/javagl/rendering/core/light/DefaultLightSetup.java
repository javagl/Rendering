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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.vecmath.Color4f;
import javax.vecmath.Tuple4f;

/**
 * Default implementation of a {@link LightSetup} 
 */
final class DefaultLightSetup implements LightSetup
{
    /**
     * The maximum number of Lights that are supported
     */
    static final int MAXIMUM_NUMBER_OF_LIGHTS = 8;
    
    /**
     * The Lights of this LightSetup
     */
    private final List<Light> lights;
   
    /**
     * The ambient color of this light setup
     */
    private final Color4f ambient; 
    
    /**
     * The list of {@link LightSetupListener}s 
     */
    private final List<LightSetupListener> lightSetupListeners;
    
    /**
     * Creates a new, empty light setup, with a default ambient
     * light of (0.2, 0.2, 0.2, 1.0).
     */
    DefaultLightSetup()
    {
        this.lights = new CopyOnWriteArrayList<Light>();
        this.ambient = new Color4f(0.2f, 0.2f, 0.2f, 1.0f);
        this.lightSetupListeners = 
            new CopyOnWriteArrayList<LightSetupListener>();
    }
    
    @Override
    public Color4f getAmbient()
    {
        return new Color4f(ambient);
    }

    @Override
    public void setAmbient(Tuple4f color)
    {
        this.ambient.set(color);
        notifyLightSetupListeners(
            Collections.<Light>emptyList(), 
            Collections.<Light>emptyList());
    }
    
    @Override
    public int getMaximumNumberOfLights()
    {
        return MAXIMUM_NUMBER_OF_LIGHTS;
    }
    
    @Override
    public int getNumberOfLights()
    {
        return lights.size();
    }
    
    @Override
    public void addLight(Light light)
    {
       lights.add(light);
       notifyLightSetupListeners(
           Arrays.asList(light), Collections.<Light>emptyList());
    }

    @Override
    public void removeLight(int index)
    {
        Light light = lights.remove(index);
        notifyLightSetupListeners(
            Collections.<Light>emptyList(), Arrays.asList(light));
    }
    
    @Override
    public void removeLight(Light light)
    {
        lights.remove(light);
        notifyLightSetupListeners(
            Collections.<Light>emptyList(), Arrays.asList(light));
    }
    
    @Override
    public void clear()
    {
        List<Light> oldLights = Collections.unmodifiableList(
            new ArrayList<Light>(lights));
        lights.clear();
        notifyLightSetupListeners(
            Collections.<Light>emptyList(), oldLights);
    }

    @Override
    public List<Light> getLights()
    {
        return Collections.unmodifiableList(new ArrayList<Light>(lights));
    }
    
    @Override
    public void addLightSetupListener(LightSetupListener lightSetupListener)
    {
        lightSetupListeners.add(lightSetupListener);
    }

    @Override
    public void removeLightSetupListener(LightSetupListener lightSetupListener)
    {
        lightSetupListeners.remove(lightSetupListener);
    }

    /**
     * Notify all registered {@link LightSetupListener}s that the given
     * {@link Light}s have been added or removed.  
     * 
     * @param addedLights The added {@link Light}s
     * @param removedLights The removed {@link Light}s.
     */
    private void notifyLightSetupListeners(
        List<Light> addedLights, List<Light> removedLights)
    {
        for (LightSetupListener lightSetupListener : lightSetupListeners)
        {
            lightSetupListener.lightSetupChanged(
                this, addedLights, removedLights);
        }
    }

    @Override
    public String toString()
    {
        return "DefaultLightSetup[" +
       		"ambient=" + ambient + "," +
       		"lights=" + lights + "]";
    }
}
