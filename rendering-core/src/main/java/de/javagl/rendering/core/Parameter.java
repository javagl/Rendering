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
 * A class describing a parameter of a {@link Program}. Predefined instances 
 * and methods for creating instances of this class are contained in the 
 * {@link Parameters} class.
 */
public final class Parameter
{
    /**
     * Creates a new {@link Parameter} with the given name and 
     * {@link ParameterType}
     * 
     * @param name The name
     * @param type The {@link ParameterType}
     * @return The {@link Parameter}
     */
    static Parameter create(String name, ParameterType type)
    {
        return new Parameter(name, type);
    }
    
    
    /**
     * The name of this {@link Parameter}
     */
    private final String name;
    
    /**
     * The type of this {@link Parameter}
     */
    private final ParameterType type;
    
    /**
     * Creates a new {@link Parameter} with the given name and 
     * {@link ParameterType}
     * 
     * @param name The name
     * @param type The {@link ParameterType}
     */
    private Parameter(String name, ParameterType type)
    {
        this.name = name;
        this.type = type;
    }
    
    /**
     * Returns the name of this parameter
     * 
     * @return The name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the {@link ParameterType} of this parameter
     * 
     * @return The {@link ParameterType}
     */
    public ParameterType getType()
    {
        return type;
    }
    
    @Override
    public String toString()
    {
        return "Parameter[name=" + name + ",type=" + type + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Parameter other = (Parameter)obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    
}
