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

package de.javagl.rendering.core;


/**
 * A {@link GraphicsObject} that is rendered with a specific {@link Program} 
 * and possibly using specific {@link Texture}s. While a {@link GraphicsObject}
 * only summarizes the data of one object, a rendered object describes how
 * this data should be interpreted by the renderer. It offers a a 
 * {@link Mapping} of program input {@link Parameter}s to to the 
 * {@link Attribute}s of the {@link GraphicsObject}, and a {@link Mapping} 
 * from program sampler {@link Parameter}s to {@link Texture}s.
 */
public interface RenderedObject
{
    /**
     * Returns the {@link Program} which is used for rendering
     *  
     * @return The {@link Program} which is used for rendering
     */
    Program getProgram();
    
    /**
     * Returns the {@link GraphicsObject} which is rendered
     * 
     * @return The {@link GraphicsObject} which is rendered
     */
    GraphicsObject getGraphicsObject();
    
    /**
     * Returns the {@link Mapping} from the input {@link Parameter}s of
     * the {@link Program} to the {@link Attribute} of the 
     * {@link GraphicsObject}.
     * 
     * @return The {@link Attribute} {@link Mapping}
     */
    Mapping<Parameter, Attribute> getAttributeMapping();
    
    /**
     * Returns the {@link Mapping} of the input {@link Parameter}s of the
     * {@link Program} to the {@link Texture}s of the 
     * {@link GraphicsObject}.
     * 
     * @return The {@link Texture} {@link Mapping}
     */
    Mapping<Parameter, Texture> getTextureMapping();
    
}
