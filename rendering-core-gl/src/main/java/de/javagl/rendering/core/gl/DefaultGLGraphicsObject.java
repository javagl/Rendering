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

package de.javagl.rendering.core.gl;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import de.javagl.rendering.core.Attribute;
import de.javagl.rendering.core.DataBuffer;


/**
 * Default implementation of a {@link GLGraphicsObject}
 */
class DefaultGLGraphicsObject implements GLGraphicsObject
{

    /**
     * The GLDataBuffer for the indices
     */
    private final GLDataBuffer indicesDataBuffer;
    
    /**
     * The number of vertices
     */
    private final int numVertices;
    
    /**
     * The unmodifiable map from {@link Attribute}s 
     * to the {@link GLDataBuffer} for the respective attribute
     */
    private final Map<Attribute, GLDataBuffer> dataBuffers;
    
    /**
     * Creates a new DefaultGLGraphicsObject with the given indices
     * and mapping from {@link Attribute}s to {@link DataBuffer}s.
     * 
     * @param indicesDataBuffer The indices data buffer. This may be 
     * <code>null</code> for non-indexed geometry
     * @param numVertices The number of vertices
     * @param dataBuffers The attribute mapping
     */
    DefaultGLGraphicsObject(GLDataBuffer indicesDataBuffer,
        int numVertices,
        Map<Attribute, GLDataBuffer> dataBuffers)
    {
        this.indicesDataBuffer = indicesDataBuffer;
        this.numVertices = numVertices;
        this.dataBuffers = Collections.unmodifiableMap(
            new LinkedHashMap<Attribute, GLDataBuffer>(dataBuffers));
    }

    @Override
    public GLDataBuffer getIndicesGLDataBuffer()
    {
        return indicesDataBuffer;
    }
    
    @Override
    public int getNumVertices()
    {
        return numVertices;
    }
    
    @Override
    public GLDataBuffer getGLDataBuffer(Attribute attribute)
    {
        return dataBuffers.get(attribute);
    }

    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataBuffers == null) ? 0 : dataBuffers.hashCode());
        result = prime * result + ((indicesDataBuffer == null) ? 0 : indicesDataBuffer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultGLGraphicsObject other = (DefaultGLGraphicsObject)obj;
        if (dataBuffers == null)
        {
            if (other.dataBuffers != null)
                return false;
        }
        else if (!dataBuffers.equals(other.dataBuffers))
            return false;
        if (indicesDataBuffer == null)
        {
            if (other.indicesDataBuffer != null)
                return false;
        }
        else if (!indicesDataBuffer.equals(other.indicesDataBuffer))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "DefaultGLGraphicsObject[" +
        		"indicesDataBuffer=" + indicesDataBuffer + "," +
        		"dataBuffers=" + dataBuffers + "]";
    }


}