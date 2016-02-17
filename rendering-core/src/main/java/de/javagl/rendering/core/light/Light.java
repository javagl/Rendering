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
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;
import javax.vecmath.Vector3f;

/**
 * This interface describes a Light. Default {@link Light} instances
 * may be created with the {@link Lights} class. Lights may be added
 * to the {@link LightSetup}. <br>
 * <br>
 * All methods will store copies of the given arguments, or return
 * copies of the internal state, respectively. 
 */
public interface Light
{
    /**
     * Returns the {@link LightType} of this {@link Light}
     * 
     * @return The {@link LightType} 
     */
    LightType getType();
    
    /**
     * Set the ambient color of this {@link Light}
     * 
     * @param ambient The ambient color
     */
    void setAmbientColor(Tuple4f ambient);

    /**
     * Set the ambient color of this {@link Light}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setAmbientColor(float r, float g, float b, float a);
    
    /**
     * Returns the ambient color of this {@link Light}. 
     * 
     * @return The ambient color
     */
    Color4f getAmbientColor();

    
    /**
     * Set the diffuse color of this {@link Light}
     * 
     * @param diffuse The diffuse color
     */
    void setDiffuseColor(Tuple4f diffuse);
    
    /**
     * Set the diffuse color of this {@link Light}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setDiffuseColor(float r, float g, float b, float a);

    /**
     * Returns the diffuse color of this {@link Light}.
     * 
     * @return The diffuse color 
     */
    Color4f getDiffuseColor();

    
    /**
     * Set the specular color of this {@link Light}
     * 
     * @param specular The specular color
     */
    void setSpecularColor(Tuple4f specular);

    /**
     * Set the specular color of this {@link Light}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setSpecularColor(float r, float g, float b, float a);
    
    /**
     * Returns the specular color of this {@link Light}.
     * 
     * @return The specular color 
     */
    Color4f getSpecularColor();
    
    
    /**
     * Set the position of this {@link Light}. 
     * For {@link LightType#DIRECTIONAL directional} lights, 
     * this value has no effect.
     * 
     * @param position The position
     */
    void setPosition(Tuple3f position);

    /**
     * Set the position of this {@link Light}. 
     * For {@link LightType#DIRECTIONAL directional} lights, 
     * this value has no effect.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    void setPosition(float x, float y, float z);
    
    /**
     * Returns the position of this {@link Light}. 
     *
     * @return The position 
     */
    Point3f getPosition();

    
    /**
     * Set the direction of this {@link Light}
     * For {@link LightType#POINT point} lights, 
     * this value has no effect.
     * 
     * @param direction The direction
     */
    void setDirection(Tuple3f direction);
    
    /**
     * Set the direction of this {@link Light}
     * For {@link LightType#POINT point} lights, 
     * this value has no effect.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    void setDirection(float x, float y, float z);
    
    /**
     * Returns the direction of this {@link Light}.
     * 
     * @return The direction 
     */
    Vector3f getDirection();
    

    /**
     * Set the constant attenuation of this {@link Light}
     * 
     * @param constant The attenuation
     */
    void setConstantAttenuation(float constant);

    /**
     * Returns the constant attenuation of this {@link Light}.
     * 
     * @return The constant attenuation
     */
    float getConstantAttenuation();
    
    /**
     * Set the linear attenuation of this {@link Light}
     * 
     * @param linear The attenuation
     */
    void setLinearAttenuation(float linear);

    /**
     * Returns the linear attenuation of this {@link Light}.
     * 
     * @return The linear attenuation
     */
    float getLinearAttenuation();
    
    /**
     * Set the quadratic attenuation of this {@link Light}
     * 
     * @param quadratic The attenuation
     */
    void setQuadraticAttenuation(float quadratic);

    /**
     * Returns the quadratic attenuation of this {@link Light}.
     * 
     * @return The quadratic attenuation
     */
    float getQuadraticAttenuation();
    
    /**
     * Set the spot cutoff angle of this {@link Light}, in degrees. 
     * The given value will be clamped to be in the range [0,90].
     *  
     * This value will only be used for 
     * {@link LightType#SPOT spot} lights.
     * 
     * @param cutoffDeg The cutoff, in degrees
     */
    void setSpotCutoffDeg(float cutoffDeg);

    /**
     * Returns the cutoff angle of this {@link Light}, in degrees. The
     * value will be in the range [0,90] 
     * 
     * @return The cutoff angle, in degrees.
     */
    float getSpotCutoffDeg();
    
    /**
     * Set the spot exponent of this {@link Light}. The light is 
     * attenuated from the center to the edge of the spot cone. 
     * Higher exponents result in more focused lights.
     * 
     * This value will only be used for 
     * {@link LightType#SPOT spot} lights.
     * 
     * @param spotExponent The spot exponent
     */
    void setSpotExponent(float spotExponent);
    
    /**
     * Returns the spot light attenuation exponent. 
     * 
     * @return The spot attenuation exponent.
     */
    float getSpotExponent();
    
    /**
     * Add the given {@link LightListener} to be informed about 
     * modifications of this {@link Light}.
     *  
     * @param lightListener The listener to add
     */
    void addLightListener(LightListener lightListener);

    /**
     * Remove the given {@link LightListener} 
     *  
     * @param lightListener The listener to remove
     */
    void removeLightListener(LightListener lightListener);
    
}
