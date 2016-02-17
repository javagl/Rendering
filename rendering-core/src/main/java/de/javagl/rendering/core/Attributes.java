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
 * Commonly used default {@link Attribute} instances and methods
 * to create new ones.
 */
public class Attributes
{
    /**
     * Vertices of an object, consisting of 3 float values
     */
    public static final Attribute VERTICES = 
        Attributes.create("vertices", DataBufferType.FLOAT, 3);
    
    /**
     * Vertex normals of an object
     */
    public static final Attribute NORMALS = 
        Attributes.create("normals", DataBufferType.FLOAT, 3);
    
    /**
     * 2D texture coordinates
     */
    public static final Attribute TEXCOORDS = 
        Attributes.create("texcoords", DataBufferType.FLOAT, 2);
    
    /**
     * Colors consisting of 3 float values 
     */
    public static final Attribute COLORS = 
        Attributes.create("colors", DataBufferType.FLOAT, 3);
    
    /**
     * Tangents consisting of 3 float values 
     */
    public static final Attribute TANGENTS = 
        Attributes.create("tangents", DataBufferType.FLOAT, 3);
    
    /**
     * Creates a new {@link Attribute} with the given name, size and type.
     * 
     * @param name The name of the {@link Attribute}
     * @param type The type of the {@link Attribute}
     * @param size The size (number of components) of the {@link Attribute}
     * @return The {@link Attribute}
     */
    public static Attribute create(String name, DataBufferType type, int size)
    {
        Attribute attribute = Attribute.create(name, type, size);
        return attribute;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Attributes()
    {
        // Private constructor to prevent instantiation
    }
    
}
