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
import java.nio.Buffer;


/**
 * Default implementation of a {@link DataBuffer}.
 */
class DefaultDataBuffer implements DataBuffer
{
    /**
     * The buffer holding the actual data
     */
    private final Buffer buffer;
    
    /**
     * The {@link DataBufferType} of the buffer
     */
    private final DataBufferType type;
    
    /**
     * The listeners for update requests on this buffer
     */
//    private List<DataBufferListener> 
//        dataBufferListeners = 
//            new CopyOnWriteArrayList<DataBufferListener>();
    
    /**
     * Creates a new buffer with the given data and {@link DataBufferType}.
     * 
     * @param buffer The data
     * @param type The {@link DataBufferType}
     */
    DefaultDataBuffer(Buffer buffer, DataBufferType type)
    {
        this.buffer = buffer;
        this.type = type;
    }
    
    @Override
    public Buffer getBuffer()
    {
        return buffer;
    }

    @Override
    public DataBufferType getType()
    {
        return type;
    }
    
    
//    @Override
//    public void requestUpdate(int start, int length)
//    {
//        for (DataBufferListener r : 
//             dataBufferListeners)
//        {
//            r.updateRequested(this, start, length);
//        }
//    }
//
//    @Override
//    public void addDataBufferListener(
//        DataBufferListener dataBufferListener)
//    {
//        dataBufferListeners.add(
//            dataBufferListener);
//    }
//
//    @Override
//    public void removeDataBufferListener(
//        DataBufferListener dataBufferListener)
//    {
//        dataBufferListeners.remove(
//            dataBufferListener);
//    }
    

    @Override
    public String toString()
    {
        return "DefaultDataBuffer["+
            "buffer="+buffer+","+
            "type="+type+"]";
    }
    
    /* 
     * Implementations of equals and hashCode. Note that these implementations
     * only consider the *references* of some of the fields! 
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + 
            ((buffer == null) ? 0 : System.identityHashCode(buffer)); 
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
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
        DefaultDataBuffer other = (DefaultDataBuffer)object;
        if (buffer == null)
        {
            if (other.buffer != null)
            {
                return false;
            }
        }
        else if (buffer != other.buffer)
        {
            return false;
        }
        if (type != other.type)
        {
            return false;
        }
        return true;
    }


}
