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
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DYNAMIC_DRAW;
import static com.jogamp.opengl.GL.GL_WRITE_ONLY;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


import java.nio.ShortBuffer;

import com.jogamp.opengl.GL3;

import de.javagl.rendering.core.DataBuffer;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLDataBuffer;
import de.javagl.rendering.core.gl.GLType;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.handling.AbstractDataBufferHandler;
import de.javagl.rendering.core.handling.DataBufferHandler;


/**
 * Implementation of a {@link DataBufferHandler} using JOGL.
 */
class JOGLDataBufferHandler 
    extends AbstractDataBufferHandler<GLDataBuffer> 
    implements DataBufferHandler<GLDataBuffer>
{
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * Set the current GL instance
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        this.gl = gl;
    }
    
    @Override
    protected GLDataBuffer handleInternal(DataBuffer dataBuffer)
    {
        //dataBuffer.addDataBufferListener(this);
        
        int vboArray[] = {0};
        gl.glGenBuffers(1, vboArray, 0);
        int vbo = vboArray[0];
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo);

        int size = dataBuffer.getBuffer().capacity();
        int typeSize = dataBuffer.getType().getSize();
        gl.glBufferData(GL_ARRAY_BUFFER, size * typeSize,
            dataBuffer.getBuffer(), GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        GLDataBuffer glDataBuffer = 
            DefaultGL.createGLDataBuffer(
                vbo, GLType.of(dataBuffer.getType()), size, 0, 0);
        return glDataBuffer;
    }

    @Override
    protected void releaseInternal(
        DataBuffer dataBuffer, GLDataBuffer glDataBuffer)
    {
        int buffer[] = {glDataBuffer.getVBO()};
        gl.glDeleteBuffers(1, buffer, 0);
        
        //dataBuffer.removeDataBufferListener(this);
    }

    
    @Override
    public void updateDataBuffer(DataBuffer dataBuffer, int start, int length)
    {
        executeDataBufferUpdate(dataBuffer);
    }

    @Override
    public void updateDataBuffer(DataBuffer dataBuffer)
    {
        executeDataBufferUpdate(dataBuffer);
    }
    
    /**
     * Updates the given {@link DataBuffer}
     * 
     * @param dataBuffer The {@link DataBuffer}
     */
    private void executeDataBufferUpdate(DataBuffer dataBuffer)
    {
        GLDataBuffer glDataBuffer = getInternal(dataBuffer);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, glDataBuffer.getVBO());
        ByteBuffer mappedByteBuffer = gl.glMapBuffer(GL_ARRAY_BUFFER, 
            GL_WRITE_ONLY);

        updateDataBuffer(mappedByteBuffer, dataBuffer);
        
        gl.glUnmapBuffer(GL_ARRAY_BUFFER);
    }
    
    /**
     * Update the given mapped buffer based on the contents
     * of the given {@link DataBuffer}
     * 
     * @param mappedByteBuffer The mapped buffer
     * @param dataBuffer The {@link DataBuffer}
     */
    private void updateDataBuffer(
        ByteBuffer mappedByteBuffer, DataBuffer dataBuffer)
    {
        switch (dataBuffer.getType())
        {
            case BYTE:
            {
                dataBuffer.getBuffer().rewind();
                ByteBuffer data = (ByteBuffer)dataBuffer.getBuffer();
                int oldPosition = data.position();
                mappedByteBuffer.put(data);
                data.position(oldPosition);
                break;
            }

            case SHORT:
            {
                ShortBuffer shortBuffer = mappedByteBuffer.asShortBuffer();
                dataBuffer.getBuffer().rewind();
                ShortBuffer data = (ShortBuffer)dataBuffer.getBuffer();
                int oldPosition = data.position();
                shortBuffer.put(data);
                data.position(oldPosition);
                break;
            }
            
            case INT:
            {
                IntBuffer intBuffer = mappedByteBuffer.asIntBuffer();
                dataBuffer.getBuffer().rewind();
                IntBuffer data = (IntBuffer)dataBuffer.getBuffer();
                int oldPosition = data.position();
                intBuffer.put(data);
                data.position(oldPosition);
                break;
            }
                
            case FLOAT:
            {
                FloatBuffer floatBuffer = mappedByteBuffer.asFloatBuffer();
                dataBuffer.getBuffer().rewind();
                FloatBuffer data = (FloatBuffer)dataBuffer.getBuffer();
                int oldPosition = data.position();
                floatBuffer.put(data);
                data.position(oldPosition);
                break;
            }
                
            default:
                ErrorHandler.handle(
                    "Type "+dataBuffer.getType()+" may not be updated");
        }
    }
    
}
