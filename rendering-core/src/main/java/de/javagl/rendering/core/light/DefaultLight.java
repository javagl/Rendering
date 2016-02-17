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
import java.util.concurrent.CopyOnWriteArrayList;

import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;
import javax.vecmath.Vector3f;


/**
 * Default implementation of a {@link Light}.
 */
final class DefaultLight implements Light
{
    /**
     * The type of this light
     */
    private final LightType type;
    
    /**
     * The ambient color of this Light
     */
    private final Color4f ambient;

    /**
     * The diffuse color of this Light
     */
    private final Color4f diffuse;

    /**
     * The specular color of this Light
     */
    private final Color4f specular;

    /**
     * The position of this Light
     */
    private final Point3f position;
    
    /**
     * The direction of this Light
     */
    private final Vector3f direction;
    
    /**
     * The constant attenuation
     */
    private float constantAttenuation;

    /**
     * The linear attenuation
     */
    private float linearAttenuation = 0.0f;

    /**
     * The quadratic attenuation
     */
    private float quadraticAttenuation = 0.0f;
    
    /**
     * The spot cutoff angle, in degrees
     */
    private float spotCutoffDeg = 90.0f;
    
    /**
     * The spot attenuation exponent
     */
    private float spotExponent = 0.0f;
    
    /**
     * The list of {@link LightListener} instances
     */
    private final List<LightListener> lightListeners;
    
    /**
     * Creates a new DefaultLight with the given {@link LightType} <br>
     * The ambient color will be (0,0,0,1) <br>
     * The diffuse color will be (1,1,1,1) <br>
     * The specular color will be (1,1,1,1) <br>
     * The position will be (0,0,1) <br>
     * The direction will be (0,0,-1) <br>
     * The constant attenuation will be 1 <br>
     * The linear attenuation will be 0 <br>
     * The quadratic attenuation will be 0 <br>
     * The spot cutoff will be 90 degrees<br>
     * The spot exponent will be 0 <br>
     * 
     * @param type The type of this light
     */
    DefaultLight(LightType type)
    {
        this.type = type;
        
        this.ambient = new Color4f(0.2f, 0.2f, 0.2f, 1.0f);
        this.diffuse = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.specular = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.position = new Point3f(0.0f, 0.0f, 1.0f);
        this.direction = new Vector3f(0.0f, 0.0f, -1.0f);
        this.constantAttenuation = 1.0f;
        this.linearAttenuation = 0.0f;
        this.quadraticAttenuation = 0.0f;
        this.spotCutoffDeg = 90.0f;
        this.spotExponent = 0.0f;
        
        this.lightListeners = new CopyOnWriteArrayList<LightListener>();
    }

    @Override
    public LightType getType()
    {
        return type;
    }
    
    @Override
    public void setAmbientColor(Tuple4f ambient)
    {
        setAmbientColor(ambient.x, ambient.y, ambient.z, ambient.w);
    }
    
    @Override
    public void setAmbientColor(float r, float g, float b, float a)
    {
        if (!equals(ambient, r, g, b, a))
        {
            ambient.set(r, g, b, a);
            notifyLightChanged();
        }
    }

    @Override
    public Color4f getAmbientColor()
    {
        return new Color4f(ambient);
    }

    
    @Override
    public void setDiffuseColor(Tuple4f diffuse)
    {
        setDiffuseColor(diffuse.x, diffuse.y, diffuse.z, diffuse.w);
    }
    
    @Override
    public void setDiffuseColor(float r, float g, float b, float a)
    {
        if (!equals(diffuse, r, g, b, a))
        {
            diffuse.set(r, g, b, a);
            notifyLightChanged();
        }
    }

    @Override
    public Color4f getDiffuseColor()
    {
        return new Color4f(diffuse);
    }

    
    @Override
    public void setSpecularColor(Tuple4f specular)
    {
        setSpecularColor(specular.x, specular.y, specular.z, specular.w);
    }

    @Override
    public void setSpecularColor(float r, float g, float b, float a)
    {
        if (!equals(specular, r, g, b, a))
        {
            specular.set(r, g, b, a);
            notifyLightChanged();
        }
    }
    
    @Override
    public Color4f getSpecularColor()
    {
        return new Color4f(specular);
    }
    
    
    @Override
    public void setPosition(Tuple3f position)
    {
        setPosition(position.x, position.y, position.z);
    }
    
    @Override
    public void setPosition(float x, float y, float z)
    {
        if (!equals(position, x, y, z))
        {
            position.set(x, y, z);
            notifyLightChanged();
        }
    }

    @Override
    public Point3f getPosition()
    {
        return new Point3f(position);
    }
    
    @Override
    public void setDirection(Tuple3f direction)
    {
        setDirection(direction.x, direction.y, direction.z);
    }
    
    @Override
    public void setDirection(float x, float y, float z)
    {
        if (!equals(direction, x, y, z))
        {
            direction.set(x, y, z);
            notifyLightChanged();
        }
    }
    
    @Override
    public Vector3f getDirection()
    {
        return new Vector3f(direction);
    }
    

    @Override
    public void setConstantAttenuation(float constant)
    {
        if (this.constantAttenuation != constant)
        {
            this.constantAttenuation = constant;
            notifyLightChanged();
        }
    }

    @Override
    public float getConstantAttenuation()
    {
        return constantAttenuation;
    }

    
    @Override
    public void setLinearAttenuation(float linear)
    {
        if (this.linearAttenuation != linear)
        {
            this.linearAttenuation = linear;
            notifyLightChanged();
        }
    }
    
    @Override
    public float getLinearAttenuation()
    {
        return linearAttenuation;
    }
    
    
    @Override
    public void setQuadraticAttenuation(float quadratic)
    {
        if (this.quadraticAttenuation != quadratic)
        {
            this.quadraticAttenuation = quadratic;
            notifyLightChanged();
        }
    }

    @Override
    public float getQuadraticAttenuation()
    {
        return quadraticAttenuation;
    }

    
    @Override
    public void setSpotCutoffDeg(float cutoffDeg)
    {
        float clampedCutoffDeg = Math.max(0, Math.min(90, cutoffDeg));
        if (this.spotCutoffDeg != clampedCutoffDeg)
        {
            this.spotCutoffDeg = clampedCutoffDeg;
            notifyLightChanged();
        }
    }

    @Override
    public float getSpotCutoffDeg()
    {
        return spotCutoffDeg;
    }
    
    @Override
    public void setSpotExponent(float spotExponent)
    {
        if (this.spotExponent != spotExponent)
        {
            this.spotExponent = spotExponent;
            notifyLightChanged();
        }
    }

    @Override
    public float getSpotExponent()
    {
        return spotExponent;
    }
    



    /**
     * Notify all listeners about a light change
     */
    protected final void notifyLightChanged()
    {
        for (LightListener lightListener : lightListeners)
        {
            lightListener.lightChanged(this);
        }
    }
    

    @Override
    public void addLightListener(LightListener lightListener)
    {
        lightListeners.add(lightListener);
    }

    @Override
    public void removeLightListener(LightListener lightListener)
    {
        lightListeners.remove(lightListener);
    }

    @Override
    public String toString()
    {
        return "DefaultLight[" +
            "position=" + position + 
            ",direction=" + direction + 
            ",type=" + type + 
            ",ambient=" + ambient + 
            ",diffuse=" + diffuse + 
            ",specular=" + specular + 
            ",constantAttenuation=" + constantAttenuation + 
            ",linearAttenuation=" + linearAttenuation + 
            ",quadraticAttenuation=" + quadraticAttenuation + 
            ",spotCutoffDeg=" + spotCutoffDeg + 
            ",spotExponent=" + spotExponent + 
            "]";
    }
    
    /**
     * Returns whether the given tuple contains the given values
     * 
     * @param tuple The tuple
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @param w The w value
     * @return Whether the tuple contains the given values
     */
    private static boolean equals(
        Tuple4f tuple, float x, float y, float z, float w)
    {
        return tuple.x == x && tuple.y == y && tuple.z == z && tuple.w == w;
    }

    /**
     * Returns whether the given tuple contains the given values
     * 
     * @param tuple The tuple
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @return Whether the tuple contains the given values
     */
    private static boolean equals(
        Tuple3f tuple, float x, float y, float z)
    {
        return tuple.x == x && tuple.y == y && tuple.z == z;
    }
    
    
}
