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

package de.javagl.rendering.core.lwjgl;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.javagl.rendering.core.Attribute;
import de.javagl.rendering.core.DataBuffer;
import de.javagl.rendering.core.DataBufferType;
import de.javagl.rendering.core.GraphicsObject;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLDataBuffer;
import de.javagl.rendering.core.gl.GLGraphicsObject;
import de.javagl.rendering.core.gl.GLType;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.handling.AbstractGraphicsObjectHandler;
import de.javagl.rendering.core.handling.DataBufferHandler;
import de.javagl.rendering.core.handling.GraphicsObjectHandler;


/**
 * Implementation of a {@link GraphicsObjectHandler} using LWJGL
 */
class LWJGLGraphicsObjectHandler 
    extends AbstractGraphicsObjectHandler<GLGraphicsObject>
    implements GraphicsObjectHandler<GLGraphicsObject>
{
    /**
     * The {@link DataBufferHandler}
     */
    private final LWJGLDataBufferHandler dataBufferHandler;
    
    /**
     * The map from (Index) {@link DataBuffer}s to 
     * the VBO IDs. 
     */
    private final Map<DataBuffer, Integer> indicesBuffers;
    
    /**
     * Creates a new LWJGLGraphicsObjectHandler
     */
    LWJGLGraphicsObjectHandler()
    {
        this.indicesBuffers = new HashMap<DataBuffer, Integer>();
        this.dataBufferHandler = new LWJGLDataBufferHandler();
    }
    
    @Override
    public GLGraphicsObject handleInternal(GraphicsObject graphicsObject)
    {
        GLDataBuffer indicesDataBuffer = null;
        DataBuffer indices = graphicsObject.getIndices();
        if (indices != null)
        {
            indicesDataBuffer = initIndices(indices);
        }

        Map<Attribute, GLDataBuffer> dataBuffers = 
            new LinkedHashMap<Attribute, GLDataBuffer>();
        for (Attribute attribute : graphicsObject.getAttributes())
        {
            if (attribute.getType() == DataBufferType.FLOAT)
            {
                DataBuffer dataBuffer = 
                    graphicsObject.getDataBuffer(attribute);
                GLDataBuffer glDataBuffer = 
                    dataBufferHandler.getInternal(dataBuffer);
                dataBuffers.put(attribute, glDataBuffer);
            }
            else
            {
                ErrorHandler.handle(
                    "Only float attributes are supported, "+
                    "found "+attribute.getType());
            }
        }
        GLGraphicsObject glGraphicsObject = 
            DefaultGL.createGLGraphicsObject(indicesDataBuffer, 
                graphicsObject.getNumVertices(), dataBuffers);
        return glGraphicsObject;
    }
    
    /**
     * Initializes the indices. Creates a new element array buffer and
     * buffers the indices that may be obtained with getIndices().
     * 
     * @param dataBuffer The {@link DataBuffer} containing the indices
     * @return The {@link GLDataBuffer} for the indices
     */
    private GLDataBuffer initIndices(DataBuffer dataBuffer)
    {
        Integer indicesVBO = indicesBuffers.get(dataBuffer);
        Buffer buffer = dataBuffer.getBuffer();
        if (indicesVBO == null)
        {
            indicesVBO = glGenBuffers();
            
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
            
            if (dataBuffer.getType() == DataBufferType.UNSIGNED_BYTE)
            {
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                    (ByteBuffer)buffer, GL_STATIC_DRAW);
            }
            else if (dataBuffer.getType() == DataBufferType.UNSIGNED_SHORT)
            {
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                    (ShortBuffer)buffer, GL_STATIC_DRAW);
            }
            else if (dataBuffer.getType() == DataBufferType.UNSIGNED_INT)
            {
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                    (IntBuffer)buffer, GL_STATIC_DRAW);
            }
            else
            {
                ErrorHandler.handle(
                    "Data buffer type not supported for indices: "+
                    dataBuffer.getType()+" (must always be an unsigned type!)");
            }

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            
            indicesBuffers.put(dataBuffer, indicesVBO);
        }
        return DefaultGL.createGLDataBuffer(
            indicesVBO, GLType.of(dataBuffer.getType()), 
            buffer.capacity(), 0, 0);
    }

    
    @Override
    public void releaseInternal(
        GraphicsObject graphicsObject, GLGraphicsObject glGraphicsObject)
    {
        GLDataBuffer indicesDataBuffer = 
            glGraphicsObject.getIndicesGLDataBuffer();
        if (indicesDataBuffer != null)
        {
            glDeleteBuffers(indicesDataBuffer.getVBO());
            indicesBuffers.remove(graphicsObject.getIndices());
        }
    }

    @Override
    public DataBufferHandler<GLDataBuffer> getDataBufferHandler()
    {
        return dataBufferHandler;
    }
    
}
