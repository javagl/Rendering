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

package de.javagl.rendering.core.material;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.vecmath.Color4f;
import javax.vecmath.Tuple4f;

/**
 * Default implementation of a {@link Material}
 */
final class DefaultMaterial implements Material
{
    /**
     * The ambient color
     */
    private final Color4f ambient;
    
    /**
     * The diffuse color
     */
    private final Color4f diffuse;
    
    /**
     * The specular color
     */
    private final Color4f specular;
    
    /**
     * The emission color
     */
    private final Color4f emission;
    
    /**
     * The shininess
     */
    private float shininess;
    
    /**
     * The list of {@link MaterialListener}s that are informed when
     * a property of this material changes
     */
    private final List<MaterialListener> materialListeners;
    
    /**
     * Creates a new default material. <br>
     * The ambient  color will be (0.2, 0.2, 0.2, 1.0) <br>
     * The diffuse  color will be (0.8, 0.8, 0.8, 1.0) <br>
     * The specular color will be (0.0, 0.0, 0.0, 1.0) <br>
     * The emission color will be (0.0, 0.0, 0.0, 1.0) <br>
     * The shininess will be 0<br>
     */
    DefaultMaterial()
    {
        this.ambient  = new Color4f(0.2f, 0.2f, 0.2f, 1.0f);
        this.diffuse  = new Color4f(0.8f, 0.8f, 0.8f, 1.0f);
        this.specular = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
        this.emission = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
        this.shininess = 0;
        
        this.materialListeners = new CopyOnWriteArrayList<MaterialListener>();
    }

    @Override
    public void setAmbientColor(Tuple4f color)
    {
        setAmbientColor(color.x, color.y, color.z, color.w);
    }
    
    @Override
    public void setAmbientColor(float r, float g, float b, float a)
    {
        if (!equals(ambient, r, g, b, a))
        {
            ambient.set(r, g, b, a);
            notifyMaterialChanged();
        }
    }

    @Override
    public Color4f getAmbientColor()
    {
        return new Color4f(ambient);
    }

    
    @Override
    public void setDiffuseColor(Tuple4f color)
    {
        setDiffuseColor(color.x, color.y, color.z, color.w);
    }

    @Override
    public void setDiffuseColor(float r, float g, float b, float a)
    {
        if (!equals(diffuse, r, g, b, a))
        {
            diffuse.set(r, g, b, a);
            notifyMaterialChanged();
        }
    }
    
    @Override
    public Color4f getDiffuseColor()
    {
        return new Color4f(diffuse);
    }



    @Override
    public void setSpecularColor(Tuple4f color)
    {
        setSpecularColor(color.x, color.y, color.z, color.w);
    }

    @Override
    public void setSpecularColor(float r, float g, float b, float a)
    {
        if (!equals(specular, r, g, b, a))
        {
            specular.set(r, g, b, a);
            notifyMaterialChanged();
        }
    }
    
    @Override
    public Color4f getSpecularColor()
    {
        return new Color4f(specular);
    }
    


    @Override
    public void setEmissionColor(Tuple4f color)
    {
        setEmissionColor(color.x, color.y, color.z, color.w);
    }

    @Override
    public void setEmissionColor(float r, float g, float b, float a)
    {
        if (!equals(emission, r, g, b, a))
        {
            emission.set(r, g, b, a);
            notifyMaterialChanged();
        }
    }
    
    @Override
    public Color4f getEmissionColor()
    {
        return new Color4f(emission);
    }
    


    @Override
    public void setShininess(float shininess)
    {
        if (this.shininess != shininess)
        {
            this.shininess = shininess;
            notifyMaterialChanged();
        }
    }

    @Override
    public float getShininess()
    {
        return shininess;
    }
    
    @Override
    public void addMaterialListener(MaterialListener materialListener)
    {
        materialListeners.add(materialListener);
    }

    @Override
    public void removeMaterialListener(MaterialListener materialListener)
    {
        materialListeners.add(materialListener);
    }
    

    /**
     * Notify all listeners about a material change
     */
    protected final void notifyMaterialChanged()
    {
        for (MaterialListener materialListener : materialListeners)
        {
            materialListener.materialChanged(this);
        }
    }
    
    @Override
    public String toString()
    {
        return "DefaultMaterial" +
            "[ambient=" + ambient + "," +
            "diffuse=" + diffuse + "," +
            "specular=" + specular + "," +
            "emission=" + emission + "," +
            "shininess=" + shininess + "]";
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

}
