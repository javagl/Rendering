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

import javax.vecmath.Color4f;
import javax.vecmath.Tuple4f;

/**
 * Interface describing material properties. All methods will store copies 
 * of the given arguments or return copies of the internal state, 
 * respectively. Instances of materials may be created with the
 * {@link Materials} class.
 */
public interface Material
{
    /**
     * Set the ambient color of this {@link Material}
     * 
     * @param color The ambient color
     */
    void setAmbientColor(Tuple4f color);
    
    /**
     * Set the ambient color of this {@link Material}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setAmbientColor(float r, float g, float b, float a);

    /**
     * Returns the ambient color of this {@link Material}
     * 
     * @return The ambient color
     */
    Color4f getAmbientColor();
    

    /**
     * Set the diffuse color of this {@link Material}
     * 
     * @param color The diffuse color
     */
    void setDiffuseColor(Tuple4f color);

    /**
     * Set the diffuse color of this {@link Material}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setDiffuseColor(float r, float g, float b, float a);
    
    /**
     * Returns the diffuse color of this {@link Material}
     * 
     * @return The diffuse color
     */
    Color4f getDiffuseColor();
    
    /**
     * Set the specular color of this {@link Material}
     * 
     * @param color The specular color
     */
    void setSpecularColor(Tuple4f color);

    /**
     * Set the specular color of this {@link Material}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setSpecularColor(float r, float g, float b, float a);
    
    /**
     * Returns the specular color of this {@link Material}
     * 
     * @return The specular color
     */
    Color4f getSpecularColor();

    
    /**
     * Set the emission color of this {@link Material}
     * 
     * @param color The emission color
     */
    void setEmissionColor(Tuple4f color);

    /**
     * Set the emission color of this {@link Material}
     * 
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    void setEmissionColor(float r, float g, float b, float a);

    /**
     * Returns the emission color of this {@link Material}
     * 
     * @return The emission color
     */
    Color4f getEmissionColor();
    
    
    /**
     * Set the shininess of this {@link Material}. The given value
     * will be clamped to the range [0,128]
     * 
     * @param shininess The shininess
     */
    void setShininess(float shininess);
    
    /**
     * Returns the shininess of this {@link Material}. 
     * 
     * @return The shininess
     */
    float getShininess();
    
    /**
     * Add the given {@link MaterialListener} to be informed when a property
     * of this material changes
     * 
     * @param materialListener The {@link MaterialListener}
     */
    void addMaterialListener(MaterialListener materialListener);
    
    /**
     * Remove the given {@link MaterialListener} from this material
     * 
     * @param materialListener The {@link MaterialListener}
     */
    void removeMaterialListener(MaterialListener materialListener);
    
}
