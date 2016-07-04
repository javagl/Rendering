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
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import de.javagl.rendering.core.DataBuffer;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLDataBuffer;
import de.javagl.rendering.core.gl.GLType;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.handling.AbstractDataBufferHandler;
import de.javagl.rendering.core.handling.DataBufferHandler;



/**
 * Implementation of a {@link DataBufferHandler} using LWJGL
 */
class LWJGLDataBufferHandler 
    extends AbstractDataBufferHandler<GLDataBuffer> 
    implements DataBufferHandler<GLDataBuffer>
{
    @Override
    public GLDataBuffer handleInternal(DataBuffer dataBuffer)
    {
        //dataBuffer.addDataBufferListener(this);
        
        int vbo = glGenBuffers();
        
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        switch (dataBuffer.getType())
        {
            case BYTE:
            case UNSIGNED_BYTE:
            {
                ByteBuffer buffer = (ByteBuffer)dataBuffer.getBuffer();
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
                break;
            }
                
            case SHORT:
            case UNSIGNED_SHORT:
            {
                ShortBuffer buffer = (ShortBuffer)dataBuffer.getBuffer();
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
                break;
            }

            case INT:
            case UNSIGNED_INT:
            {
                IntBuffer buffer = (IntBuffer)dataBuffer.getBuffer();
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
                break;
            }

            case FLOAT:
            {
                FloatBuffer buffer = (FloatBuffer)dataBuffer.getBuffer();
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
                break;
            }
                
            default:
                ErrorHandler.handle(
                    "Type "+dataBuffer.getType()+" not supported");
        }
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        GLDataBuffer glDataBuffer = 
            DefaultGL.createGLDataBuffer(
                vbo, GLType.of(dataBuffer.getType()), 
                dataBuffer.getBuffer().capacity(), 0, 0);
        return glDataBuffer;
    }
    
    @Override
    public void releaseInternal(
        DataBuffer dataBuffer, GLDataBuffer glDataBuffer)
    {
        glDeleteBuffers(glDataBuffer.getVBO());
        
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
     * @param dataBuffer The {@link DataBuffer} to update
     */
    private void executeDataBufferUpdate(DataBuffer dataBuffer)
    {
        GLDataBuffer glDataBuffer = getInternal(dataBuffer);
        if (glDataBuffer == null)
        {
            ErrorHandler.handle("GLDataBuffer not found for "+dataBuffer);
            return;
        }
        
        glBindBuffer(GL_ARRAY_BUFFER, glDataBuffer.getVBO());
        int typeSize = dataBuffer.getType().getSize();
        int size = dataBuffer.getBuffer().capacity();
        ByteBuffer mappedByteBuffer = glMapBuffer(GL_ARRAY_BUFFER, 
            GL_WRITE_ONLY, size * typeSize, null);

        updateDataBuffer(mappedByteBuffer, dataBuffer);
        
        glUnmapBuffer(GL_ARRAY_BUFFER);
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
            case UNSIGNED_BYTE:
            {
                dataBuffer.getBuffer().rewind();
                ByteBuffer data = (ByteBuffer)dataBuffer.getBuffer();
                int oldPosition = data.position();
                mappedByteBuffer.put(data);
                data.position(oldPosition);
                break;
            }

            case SHORT:
            case UNSIGNED_SHORT:
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
            case UNSIGNED_INT:
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
