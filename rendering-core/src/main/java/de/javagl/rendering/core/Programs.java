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

import java.io.IOException;
import java.io.InputStream;

/**
 * Methods for creating {@link Program} instances
 */
public class Programs
{
    /**
     * The instance counter, used for creating unique names of the programs
     */
    private static int counter = 0;
    
    /**
     * Creates a default {@link Program} that emulates the fixed function
     * pipeline
     * 
     * @return The {@link Program}
     * @throws RenderingException If there is an IO error while reading
     * the required shader input resources
     */
    public static Program createFixedFunctionProgram()
    {
        return createFromResources(
            "/ffShader.vs",
            "/ffShader.fs");
    }
    
    /**
     * Creates a new default {@link Program} with the specified 
     * vertex- and fragment {@link Shader}.
     * 
     * @param vertexShaderFileName The vertex {@link Shader} file name
     * @param fragmentShaderFileName The fragment {@link Shader} file name
     * @return The {@link Program} instance.
     * @throws RenderingException If there is an IO error while reading
     * the specified files
     */
    public static Program createFromFiles(
        String vertexShaderFileName, 
        String fragmentShaderFileName)
    {
        try
        {
            String vertexShaderSourceCode = 
                IOUtils.readFileAsString(vertexShaderFileName);
            String fragmentShaderSourceCode = 
                IOUtils.readFileAsString(fragmentShaderFileName);
            return createFromSourceCode(
                vertexShaderSourceCode, 
                fragmentShaderSourceCode);
        }
        catch (IOException e)
        {
            throw new RenderingException("Could not read shaders", e);
        }
    }
    
    /**
     * Creates a new default {@link Program} with the given 
     * vertex- and fragment {@link Shader}.
     * 
     * @param vertexShaderSourceCode The vertex {@link Shader} source code
     * @param fragmentShaderSourceCode The fragment {@link Shader} source code
     * @return The {@link Program} instance.
     */
    public static Program createFromSourceCode(
        String vertexShaderSourceCode, 
        String fragmentShaderSourceCode)
    {
        Shader[] shaders =
        { 
            new DefaultShader(ShaderType.VERTEX, 
                vertexShaderSourceCode), 
            new DefaultShader(ShaderType.FRAGMENT, 
                fragmentShaderSourceCode) 
        };
        Program program = new DefaultProgram(
            ("Program-"+String.valueOf(counter++)), shaders);
        return program;
    }
    
    
    /**
     * Creates a new default {@link Program} with the specified 
     * vertex- and fragment {@link Shader}.
     * 
     * @param vertexShaderResourceName The vertex {@link Shader} resource name
     * @param fragmentShaderResourceName The fragment {@link Shader} resource 
     * name
     * @return The {@link Program} instance.
     * @throws RenderingException If there is an IO error while reading
     * the specified resources
     */
    public static Program createFromResources(
        String vertexShaderResourceName, 
        String fragmentShaderResourceName)
    {
        
        InputStream vertexShaderResourceStream = 
            Programs.class.getResourceAsStream(vertexShaderResourceName);
        if (vertexShaderResourceStream == null)
        {
            throw new RenderingException(
                "Could not read resource "+vertexShaderResourceName);
        }
        InputStream fragmentShaderResourceStream = 
            Programs.class.getResourceAsStream(fragmentShaderResourceName);
        if (fragmentShaderResourceStream == null)
        {
            throw new RenderingException(
                "Could not read resource "+fragmentShaderResourceName);
        }
        return createFromStreams(
            vertexShaderResourceStream, fragmentShaderResourceStream);
    }
    
    
    /**
     * Creates a new default program with the given vertex- and fragment 
     * {@link Shader}. The given streams will be closed after they have
     * been read.
     * 
     * @param vertexShaderInputStream The vertex {@link Shader} input stream
     * @param fragmentShaderInputStream The fragment {@link Shader} input 
     * stream
     * @return The {@link Program} instance.
     * @throws RenderingException If there is an IO error while reading
     * the given streams
     */
    public static Program createFromStreams(
        InputStream vertexShaderInputStream, 
        InputStream fragmentShaderInputStream)
    {
        try
        {
            String vertexShaderSourceCode = 
                IOUtils.readStreamAsString(vertexShaderInputStream);
            String fragmentShaderSourceCode = 
                IOUtils.readStreamAsString(fragmentShaderInputStream);
            return createFromSourceCode(
                vertexShaderSourceCode, 
                fragmentShaderSourceCode);
        }
        catch (IOException e)
        {
            throw new RenderingException("Could not read shaders", e);
        }
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Programs()
    {
        // Private constructor to prevent instantiation
    }
    
    
    /**
     * Only for debugging.
     * @param vertexShaderFileName Only for debugging
     * @param fragmentShaderFileName Only for debugging
     * @return Nothing special.
     */
    public static Program createDebugProgramFromFiles(
        String vertexShaderFileName, 
        String fragmentShaderFileName)
    {
        Program program = new DebugProgram(
            "Program-"+String.valueOf(counter++), 
            vertexShaderFileName, fragmentShaderFileName);
        return program;
    }
    
}
