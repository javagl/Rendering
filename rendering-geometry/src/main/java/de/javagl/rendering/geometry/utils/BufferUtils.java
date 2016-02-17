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

package de.javagl.rendering.geometry.utils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Utility methods for buffers. Unless otherwise noted, none of the
 * arguments for these methods may be <code>null</code>.
 */
class BufferUtils
{
    /**
     * Create a direct byte buffer with the given size
     * 
     * @param size The size of the buffer
     * @return The buffer
     * @throws IllegalArgumentException If the given size is negative
     */
    public static ByteBuffer createByteBuffer(int size)
    {
        return ByteBuffer.allocateDirect(size).
            order(ByteOrder.nativeOrder());
    }

    /**
     * Create a direct float buffer with the given size
     * 
     * @param size The size of the buffer
     * @return The buffer
     * @throws IllegalArgumentException If the given size is negative
     */
    public static FloatBuffer createFloatBuffer(int size)
    {
        return createByteBuffer(size * 4).asFloatBuffer();
    }

    /**
     * Create a direct int buffer with the given size
     * 
     * @param size The size of the buffer
     * @return The buffer
     * @throws IllegalArgumentException If the given size is negative
     */
    public static IntBuffer createIntBuffer(int size)
    {
        return createByteBuffer(size * 4).asIntBuffer();
    }
	
    /**
     * Create a direct int buffer which has the contents
     * of the given array.
     * 
     * @param a The array
     * @return The buffer
     */
    public static IntBuffer createDirectBuffer(int a[])
    {
        IntBuffer b = createIntBuffer(a.length);
        b.put(a);
        b.rewind();
        return b;
    }

    /**
     * Create a direct float buffer which has the contents
     * of the given array.
     * 
     * @param a The array
     * @return The buffer
     */
    public static FloatBuffer createDirectBuffer(float a[])
    {
        FloatBuffer b = createFloatBuffer(a.length);
        b.put(a);
        b.rewind();
        return b;
    }

    /**
     * Create a direct float buffer which has the remaining
     * contents of the given buffer.
     * 
     * @param floatBuffer The input buffer
     * @return The buffer
     */
    public static FloatBuffer createDirectBuffer(FloatBuffer floatBuffer)
    {
        FloatBuffer b = createFloatBuffer(floatBuffer.remaining());
        int oldPosition = floatBuffer.position();
        b.put(floatBuffer);
        floatBuffer.position(oldPosition);
        b.rewind();
        return b;
    }

    /**
     * Returns a String representation of the remaining elements
     * of the given buffer.
     * 
     * @param buffer The buffer
     * @return A String representation of the buffer
     */
    public static String toString(FloatBuffer buffer)
    {
        return toString(buffer, buffer.remaining());
    }

    /**
     * Returns a String representation of the remaining elements
     * of the given buffer, up to the given maximum number of 
     * elements.
     * 
     * @param buffer The buffer
     * @param maxElements The maximum number of elements to include
     * @return A String representation of the buffer
     */
    public static String toString(FloatBuffer buffer, int maxElements)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int n = Math.min(buffer.remaining(), maxElements);
		for (int i=0; i<n; i++)
		{
			sb.append(buffer.get(i));
			if (i<n-1)
			{
				sb.append(", ");
			}
		}
		if (buffer.remaining() > maxElements)
		{
		    sb.append("...");
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * Converts the given String into a 0-terminated, direct
	 * byte buffer.
	 * 
	 * @param s The input String
	 * @return The byte buffer
	 */
    public static ByteBuffer toByteBuffer(String s)
    {
        byte bytes[] = s.getBytes();
        ByteBuffer buffer = createByteBuffer(bytes.length+1);
        buffer.put(bytes);
        buffer.put((byte)0);
        buffer.rewind();
        return buffer;
    }
 
    
    /**
     * Private constructor to prevent instantiation
     */
    private BufferUtils()
    {
        // Private constructor to prevent instantiation
    }
    
	/*
    private static byte[] toByteArray(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buffer[] = new byte[8192];
        while (true)
        {
            int read = inputStream.read(buffer);
            if (read < 0)
            {
                break;
            }
            baos.write(buffer, 0, read);
        }
        baos.close();
        return baos.toByteArray();
    }
	
    private static ByteBuffer toByteBuffer(InputStream inputStream) throws IOException
    {
        byte data[] = toByteArray(inputStream);
        ByteBuffer dataBuffer = createByteBuffer(data.length);
        dataBuffer.put(data);
        dataBuffer.flip();
        return dataBuffer;
    }
	*/
}