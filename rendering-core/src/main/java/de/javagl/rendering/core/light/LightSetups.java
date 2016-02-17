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

import javax.vecmath.Color4f;

/**
 * Utility methods for configuring {@link LightSetup}s
 */
public class LightSetups
{
    /**
     * Creates a new {@link LightSetup}
     * 
     * @return The new {@link LightSetup}
     */
    public static LightSetup create()
    {
        return new DefaultLightSetup();
    }
    
    
    /**
     * Add two default {@link Light}s to the given {@link LightSetup}: 
     * One bright, white directional light from the upper left front, 
     * and one slightly darker white directional light from the 
     * lower right back.  
     * 
     * @param lightSetup The {@link LightSetup} to configure
     * @throws IllegalArgumentException If the given {@link LightSetup}
     * can not store 2 additional lights.  
     */
    public static void addDefaultLights(LightSetup lightSetup)
    {
        if (lightSetup.getLights().size() + 2 > 
            lightSetup.getMaximumNumberOfLights())
        {
            throw new IllegalArgumentException(
                "The given LightSetup may only store "+
                lightSetup.getMaximumNumberOfLights()+
                ", but it already contains "+
                lightSetup.getLights().size()+
                ", can not add 2 default lights");
        }
        
        Light light0 = Lights.createDirectionalLight(-1, 1, 2);
        light0.setAmbientColor(new Color4f(0.3f, 0.3f, 0.3f, 1.0f));
        lightSetup.addLight(light0);

        Light light1 = Lights.createDirectionalLight(1, -1, -1);
        light1.setDiffuseColor(new Color4f(0.5f, 0.5f, 0.5f, 1.0f));
        light1.setSpecularColor(new Color4f(0.6f, 0.6f, 0.6f, 1.0f));
        lightSetup.addLight(light1);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private LightSetups()
    {
        // Private constructor to prevent instantiation
    }
}
