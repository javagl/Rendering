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
 * Description of an Attribute of a {@link GraphicsObject}.<br>
 * <br>
 * Predefined {@link Attribute}s and methods to create new ones are contained
 * in the {@link Attributes} class. 
 */
public final class Attribute
{
    /**
     * Creates a new {@link Attribute} with the given name, size and type.
     * 
     * @param name The name of the {@link Attribute}
     * @param type The type of the {@link Attribute}
     * @param size The size (number of components) of the {@link Attribute}
     * @return The {@link Attribute}
     */
    static Attribute create(String name, DataBufferType type, int size)
    {
        return new Attribute(name, type, size);
    }
    
    /**
     * The name of the attribute
     */
    private final String name;
    
    /**
     * The type of the attribute
     */
    private final DataBufferType type;
    
    /**
     * The size (number of components) of the attribute
     */
    private final int size;
    
    /**
     * Creates a new {@link Attribute} with the given name, size and type.
     * 
     * @param name The name of the {@link Attribute}
     * @param type The type of the {@link Attribute}
     * @param size The size (number of components) of the {@link Attribute}
     */
    private Attribute(String name, DataBufferType type, int size)
    {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    /**
     * Returns the name of this attribute
     * 
     * @return The name of this attribute
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the type of this attribute
     * 
     * @return The type of this attribute
     */
    public DataBufferType getType()
    {
        return type;
    }

    /**
     * Returns the size (number of components) of the attribute
     * 
     * @return The size (number of components) of the attribute
     */
    public int getSize()
    {
        return size;
    }

    @Override
    public String toString()
    {
        return "Attribute["+
            "name="+name+","+
            "type="+type+","+
            "size="+size+"]";
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + size;
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
        Attribute other = (Attribute)obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (size != other.size)
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    
    
    
}