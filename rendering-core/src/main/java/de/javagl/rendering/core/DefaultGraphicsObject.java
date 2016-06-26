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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Default implementation of a {@link GraphicsObject}.
 */
class DefaultGraphicsObject implements GraphicsObject
{
    /**
     * The {@link DataBuffer} containing the indices of this graphics object.
     * This may be <code>null</code> for non-indexed geometry.
     */
    private final DataBuffer indices;
    
    /**
     * The number of vertices that this object consists of.
     */
    private final int numVertices;
    
    /**
     * The {@link Mapping} from the {@link Attribute} instances to the 
     * {@link DataBuffer} holding the data for the {@link Attribute}.
     */
    private final Mapping<Attribute, DataBuffer> dataBuffers;
    
    /**
     * The list of {@link Attribute}s in this graphics object
     */
    private final List<Attribute> attributes;
    
    /**
     * Creates a new {@link GraphicsObject} with the given indices
     * and the given {@link Mapping} from the {@link Attribute}s to the 
     * {@link DataBuffer}s holding the data for the {@link Attribute}s
     * 
     * @param indices The indices of this object. This may be <code>null</code>
     * for non-indexed objects
     * @param numVertices The number of vertices that this object consists of
     * @param dataBuffers The {@link Mapping} from the 
     * {@link Attribute}s to the {@link DataBuffer}s 
     */
    DefaultGraphicsObject(
        DataBuffer indices, int numVertices, 
        Mapping<Attribute, DataBuffer> dataBuffers)
    {
        this.indices = indices;
        this.numVertices = numVertices;
        this.dataBuffers = dataBuffers;
        this.attributes = new ArrayList<Attribute>(dataBuffers.keySet());
    }

    
    @Override
    public DataBuffer getIndices()
    {
        return indices;
    }
    
    @Override
    public int getNumVertices()
    {
        return numVertices;
    }
    
    @Override
    public DataBuffer getDataBuffer(Attribute attribute)
    {
        return dataBuffers.get(attribute);
    }
    
    @Override
    public List<Attribute> getAttributes()
    {
        return Collections.unmodifiableList(attributes);
    }

    @Override
    public String toString()
    {
        return "DefaultGraphicsObject["+
            "indices="+indices+","+
            "dataBuffers="+dataBuffers+","+
            "attributes="+attributes+"]";
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(attributes, dataBuffers, indices);
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
        DefaultGraphicsObject other = (DefaultGraphicsObject)object;
        if (!Objects.equals(attributes,  other.attributes))
        {
            return false;
        }
        if (!Objects.equals(attributes,  other.attributes))
        {
            return false;
        }
        if (!Objects.equals(dataBuffers,  other.dataBuffers))
        {
            return false;
        }
        if (!Objects.equals(indices,  other.indices))
        {
            return false;
        }
        return true;
    }
    
}