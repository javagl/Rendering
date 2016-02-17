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

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Methods for creating {@link Light} instances
 */
public class Lights
{
    /**
     * Create a a point {@link Light}.<br>
     * The ambient color will be (0,0,0,1) <br>
     * The diffuse color will be (1,1,1,1) <br>
     * The specular color will be (1,1,1,1) <br>
     * The position will be (0,0,1) <br>
     * The constant attenuation will be 1 <br>
     * The linear attenuation will be 0 <br>
     * The quadratic attenuation will be 0 <br>
     * 
     * @param x The x coordinate of the position
     * @param y The y coordinate of the position
     * @param z The z coordinate of the position
     * @return The {@link Light}
     */
    public static Light createPointLight(
        float x, float y, float z)
    {
        DefaultLight light = new DefaultLight(LightType.POINT);
        light.setPosition(new Point3f(x, y, z));
        return light;
    }
    
    /**
     * Create a directional {@link Light}.<br>
     * The ambient color will be (0,0,0,1) <br>
     * The diffuse color will be (1,1,1,1) <br>
     * The specular color will be (1,1,1,1) <br>
     * 
     * @param dx The x coordinate of the direction
     * @param dy The y coordinate of the direction 
     * @param dz The z coordinate of the direction 
     * @return The {@link Light}
     */
    public static Light createDirectionalLight(
        float dx, float dy, float dz)
    {
        Light light = new DefaultLight(LightType.DIRECTIONAL);
        light.setDirection(new Vector3f(dx, dy, dz));
        return light;
    }
    
    /**
     * Create a spot {@link Light}.<br>
     * The ambient color will be (0,0,0,1) <br>
     * The diffuse color will be (1,1,1,1) <br>
     * The specular color will be (1,1,1,1) <br>
     * The constant attenuation will be 1 <br>
     * The linear attenuation will be 0 <br>
     * The quadratic attenuation will be 0 <br>
     * 
     * @param x The x coordinate of the position
     * @param y The y coordinate of the position
     * @param z The z coordinate of the position
     * @param dx The x coordinate of the direction
     * @param dy The y coordinate of the direction 
     * @param dz The z coordinate of the direction 
     * @param spotCutoffDeg The spot cutoff, in degrees
     * @param spotExponent The spot exponent
     * @return The {@link Light}
     */
    public static Light createSpotLight(
        float x, float y, float z, float dx, float dy, float dz, 
        float spotCutoffDeg, float spotExponent)
    {
        Light light = new DefaultLight(LightType.SPOT);
        light.setPosition(new Point3f(x, y, z));
        light.setDirection(new Vector3f(dx, dy, dz));
        light.setSpotCutoffDeg(spotCutoffDeg);
        light.setSpotExponent(spotExponent);
        return light;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Lights()
    {
        // Private constructor to prevent instantiation
    }

}
