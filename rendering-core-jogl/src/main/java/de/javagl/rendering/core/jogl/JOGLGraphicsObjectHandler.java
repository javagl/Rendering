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

package de.javagl.rendering.core.jogl;

import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jogamp.opengl.GL3;

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
 * Implementation of a {@link GraphicsObjectHandler} using JOGL.
 */
class JOGLGraphicsObjectHandler 
    extends AbstractGraphicsObjectHandler<GLGraphicsObject>
    implements GraphicsObjectHandler<GLGraphicsObject>
{
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * The {@link DataBufferHandler}
     */
    private final JOGLDataBufferHandler dataBufferHandler;
    
    /**
     * The map from (Index) {@link DataBuffer}s to 
     * the VBO IDs. 
     */
    private final Map<DataBuffer, Integer> indicesBuffers;
    
    /**
     * Creates a new JOGLGraphicsObjectHandler
     */
    JOGLGraphicsObjectHandler()
    {
        this.dataBufferHandler = new JOGLDataBufferHandler();
        this.indicesBuffers = new HashMap<DataBuffer, Integer>();
    }

    /**
     * Set the current GL instance
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        this.gl = gl;
        dataBufferHandler.setGL(gl);
    }
    
    @Override
    protected GLGraphicsObject handleInternal(GraphicsObject graphicsObject)
    {
        GLDataBuffer indicesDataBuffer = 
            initIndices(graphicsObject.getIndices());

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
            DefaultGL.createGLGraphicsObject(
                indicesDataBuffer, dataBuffers);
        return glGraphicsObject;
    }
    
    /**
     * Initializes the indices. Creates a new element array buffer and
     * buffers the indices that may be obtained with getIndices().
     */
    private GLDataBuffer initIndices(DataBuffer dataBuffer)
    {
        Integer indicesVBO = indicesBuffers.get(dataBuffer);
        Buffer buffer = dataBuffer.getBuffer();
        if (indicesVBO == null)
        {
            int vboArray[] = {0};
            gl.glGenBuffers(1, vboArray, 0);
            indicesVBO = vboArray[0];
            
            gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
    
            if (dataBuffer.getType() != DataBufferType.UNSIGNED_BYTE &&
                dataBuffer.getType() != DataBufferType.UNSIGNED_SHORT &&
                dataBuffer.getType() != DataBufferType.UNSIGNED_INT)
            {
                ErrorHandler.handle(
                    "Data buffer type not supported for indices: "+
                    dataBuffer.getType()+" (must always be an unsigned type!)");
            }
            
            final int elementSize = dataBuffer.getType().getSize();
            gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                buffer.capacity() * elementSize, buffer, GL_STATIC_DRAW);
    
            gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            
            indicesBuffers.put(dataBuffer, indicesVBO);
        }
        GLDataBuffer glDataBuffer = 
            DefaultGL.createGLDataBuffer(
                indicesVBO, GLType.of(dataBuffer.getType()), 
                buffer.capacity(), 0, 0);
        return glDataBuffer;
    }
    
    @Override
    protected void releaseInternal(
        GraphicsObject graphicsObject, GLGraphicsObject glGraphicsObject)
    {
        int buffers[] = 
            {glGraphicsObject.getIndicesGLDataBuffer().getVBO()};
        gl.glDeleteBuffers(1, buffers, 0);
        indicesBuffers.remove(graphicsObject.getIndices());
    }
    

    @Override
    public DataBufferHandler<GLDataBuffer> getDataBufferHandler()
    {
        return dataBufferHandler;
    }


    
    
}