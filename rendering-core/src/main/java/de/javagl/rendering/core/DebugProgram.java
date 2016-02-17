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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Only for debugging.
 */
public class DebugProgram implements Program
{
    private String name;
    private List<Shader> shaders;
    private String vsName;
    private String fsName;
    
    /**
     * Only for debugging
     * @param name Only for debugging
     * @param vsName Only for debugging
     * @param fsName Only for debugging
     */
    public DebugProgram(String name, String vsName, String fsName)
    {
        this.name = name;
        this.vsName = vsName;
        this.fsName = fsName;
        reload();
    }
    
    /**
     * Only for debugging.
     */
    public void reload()
    {
        try
        {
            Shader vs = new DefaultShader(
                ShaderType.VERTEX, IOUtils.readFileAsString(vsName)); 
            Shader fs = new DefaultShader(
                ShaderType.FRAGMENT, IOUtils.readFileAsString(fsName)); 
            this.shaders = new ArrayList<Shader>(Arrays.asList(vs, fs));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Shader> getShaders()
    {
        return Collections.unmodifiableList(shaders);
    }

    @Override
    public String toString()
    {
        return "DebugProgram[name="+name+"]";
    }
}
