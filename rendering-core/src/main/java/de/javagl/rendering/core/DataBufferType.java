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
 * Collection of supported data types for {@link DataBuffer}s
 */
public enum DataBufferType
{
    /**
     * The 8 bit byte data type
     */
    BYTE(1), 

    /**
     * The 8 bit unsigned byte data type
     */
    UNSIGNED_BYTE(1), 

    /**
     * The 16 bit byte data type
     */
    SHORT(2), 

    /**
     * The 16 bit unsigned short data type
     */
    UNSIGNED_SHORT(2), 
    
    /**
     * The 32 bit int data type
     */
    INT(4), 
    
    /**
     * The 32 bit unsigned int data type
     */
    UNSIGNED_INT(4), 
    
    /**
     * The 32 bit float data type
     */
    FLOAT(4);
    
    /**
     * The size of the type
     */
    private final int size;
    
    /**
     * Creates a new Type with the given size
     * 
     * @param size The size of the type, in bytes
     */
    private DataBufferType(int size)
    {
        this.size = size;
    }
    
    /**
     * Returns the size of the type, in bytes
     * 
     * @return The size of the type, in bytes
     */
    public int getSize()
    {
        return size;
    }
}
