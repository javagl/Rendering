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

package de.javagl.rendering.core.view;


/**
 * Methods for creating {@link Rectangle} instances
 */
public class Rectangles
{
    /**
     * Creates a new {@link Rectangle} with the given parameters.
     * 
     * @param x The x-coordinate of the {@link Rectangle}
     * @param y The y-coordinate of the {@link Rectangle}
     * @param width The width of the {@link Rectangle}
     * @param height The height of the {@link Rectangle}
     * @return The {@link Rectangle}
     */
    public static Rectangle create(int x, int y, int width, int height)
    {
        return new DefaultRectangle(x, y, width, height);
    }

    /**
     * Creates a copy of the given {@link Rectangle}
     * 
     * @param other The other {@link Rectangle}
     * @return The new {@link Rectangle}
     */
    public static Rectangle create(Rectangle other)
    {
        return new DefaultRectangle(other);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Rectangles()
    {
        // Private constructor to prevent instantiation
    }
}
