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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Package-pricate IO utility methods
 */
class IOUtils
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(IOUtils.class.getName());
    
    /**
     * Read the file with the given name and return its contents
     * as a String. 
     * 
     * @param fileName The name of the file
     * @return The file contents.
     * @throws IOException If an IO error occurs
     */
    static String readFileAsString(String fileName) throws IOException
    {
        return new String(readFile(fileName));
    }
    
    /**
     * Read the given inputStream, closes it, and return its contents 
     * as a String.
     * 
     * @param inputStream The input stream to read
     * @return The file contents.
     * @throws IOException If an IO error occurs
     */
    static String readStreamAsString(InputStream inputStream) throws IOException
    {
        String result = new String(readStream(inputStream));
        inputStream.close();
        return result;
    }
    
    /**
     * Load the data from the file with the given name and returns 
     * it as a byte array. 
     *  
     * @param fileName The name of the file
     * @return The data from the file
     * @throws IOException If an IO error occurs
     */
    private static byte[] readFile(String fileName) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream= new FileInputStream(new File(fileName));
            return readStream(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    logger.warning("Could not close input: "+e.getMessage());
                }
            }
        }
    }
    
    /**
     * Reads the data from the given inputStream and returns it as
     * a byte array. The caller is responsible for closing the stream.
     * 
     * @param inputStream The input stream to read
     * @return The data from the inputStream
     * @throws IOException If an IO error occurs
     */
    private static byte[] readStream(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            byte buffer[] = new byte[8192];
            while (true)
            {
                int read = inputStream.read(buffer);
                if (read == -1)
                {
                    break;
                }
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return baos.toByteArray();
        }
        finally
        {
            if (baos != null)
            {
                try
                {
                    baos.close();
                }
                catch (IOException e)
                {
                    logger.warning("Could not close output: "+e.getMessage());
                }
            }
        }
    }
    

    /**
     * Private constructor to prevent instantiation
     */
    private IOUtils()
    {
        // Private constructor to prevent instantiation
    }
}
