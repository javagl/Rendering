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

import java.util.Objects;

/**
 * Default implementation of a {@link RenderedObject}.
 */
class DefaultRenderedObject implements RenderedObject
{
    /**
     * The {@link Program} which should used for rendering
     */
    private final Program program;
    
    /**
     * The {@link GraphicsObject} that should be rendered
     */
    private final GraphicsObject graphicsObject;
    
    /**
     * The {@link Mapping} from {@link Program} {@link Parameter}s to 
     * {@link Attribute}s of the {@link GraphicsObject}.
     */
    private final Mapping<Parameter, Attribute> attributeMapping;
    
    /**
     * The mapping from {@link Program} {@link Parameter}s to 
     * {@link Texture}s.
     */
    private final Mapping<Parameter, Texture> textureMapping;

    /**
     * The cached hash code of this object
     */
    private int hashCode = 0;
    
    /**
     * Creates a new rendered object, which uses the given {@link Program} 
     * to render the given {@link GraphicsObject} using the given
     * {@link Attribute} {@link Mapping}.
     * 
     * @param program The {@link Program}
     * @param graphicsObject The {@link GraphicsObject}
     * @param attributeMapping The {@link Attribute} {@link Mapping}
     */
    DefaultRenderedObject(
        Program program, GraphicsObject graphicsObject, 
        Mapping<Parameter, Attribute> attributeMapping)
    {
        this(program, graphicsObject, attributeMapping, 
            Mappings.<Parameter, Texture>create());
    }
    
    /**
     * Creates a new rendered object, which uses the given {@link Program} 
     * to render the given {@link GraphicsObject} using the given
     * {@link Attribute} and {@link Texture} {@link Mapping}.
     * 
     * @param program The {@link Program}
     * @param graphicsObject The {@link GraphicsObject}
     * @param attributeMapping The {@link Attribute} {@link Mapping}
     * @param textureMapping The {@link Texture} {@link Mapping}
     */
    DefaultRenderedObject(
        Program program, GraphicsObject graphicsObject, 
        Mapping<Parameter, Attribute> attributeMapping, 
        Mapping<Parameter, Texture> textureMapping)
    {
        this.program = program;
        this.graphicsObject = graphicsObject;
        this.attributeMapping = attributeMapping;
        this.textureMapping = textureMapping;
        
        this.hashCode = Objects.hash(
            attributeMapping, graphicsObject, program, textureMapping);
    }

    @Override
    public Program getProgram()
    {
        return program;
    }
    
    @Override
    public GraphicsObject getGraphicsObject()
    {
        return graphicsObject;
    }

    @Override
    public Mapping<Parameter, Attribute> getAttributeMapping()
    {
        return attributeMapping;
    }

    @Override
    public Mapping<Parameter, Texture> getTextureMapping()
    {
        return textureMapping;
    }
    
    @Override
    public String toString()
    {
        return "DefaultRenderedObject["+
            "program="+program+","+
            "graphicsObject="+graphicsObject+","+
            "attributeMapping="+attributeMapping+","+
            "textureMapping="+textureMapping+"]";
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (getClass() != object.getClass())
        {
            return false;
        }
        DefaultRenderedObject other = (DefaultRenderedObject)object;
        if (attributeMapping == null)
        {
            if (other.attributeMapping != null)
            {
                return false;
            }
        }
        else if (!attributeMapping.equals(other.attributeMapping))
        {
            return false;
        }
        if (graphicsObject == null)
        {
            if (other.graphicsObject != null)
            {
                return false;
            }
        }
        else if (!graphicsObject.equals(other.graphicsObject))
        {
            return false;
        }
        if (program == null)
        {
            if (other.program != null)
            {
                return false;
            }
        }
        else if (!program.equals(other.program))
        {
            return false;
        }
        if (textureMapping == null)
        {
            if (other.textureMapping != null)
            {
                return false;
            }
        }
        else if (!textureMapping.equals(other.textureMapping))
        {
            return false;
        }
        return true;
    }
    
}
