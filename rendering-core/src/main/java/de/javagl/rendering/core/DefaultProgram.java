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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Default implementation of a {@link Program}.
 */
class DefaultProgram implements Program
{
    /**
     * A name of this {@link Program}. Only used for informative
     * output in the toString method. 
     */
    private final String name;

    /**
     * The unmodifiable list of {@link Shader} instances 
     */
    private final List<Shader> shaders;
    
    /**
     * Creates a new {@link Program} consisting of the given 
     * {@link Shader} instances.
     * 
     * @param name The name of the program
     * @param shaders The shaders of the program
     */
    DefaultProgram(String name, Shader ... shaders)
    {
        this.name = name;
        this.shaders = Collections.unmodifiableList(
            new ArrayList<Shader>(Arrays.asList(shaders)));
    }

    @Override
    public List<Shader> getShaders()
    {
        return shaders;
    }

    @Override
    public String toString()
    {
        return "DefaultProgram[name="+name+"]";
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, shaders);
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
        DefaultProgram other = (DefaultProgram)object;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        if (shaders == null)
        {
            if (other.shaders != null)
            {
                return false;
            }
        }
        else if (!shaders.equals(other.shaders))
        {
            return false;
        }
        return true;
    }
}
