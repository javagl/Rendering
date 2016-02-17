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
 * Default implementation of a {@link Rectangle}
 */
class DefaultRectangle implements Rectangle
{
    /**
     * The x-coordinate of this rectangle
     */
    private final int x;

    /**
     * The y-coordinate of this rectangle
     */
    private final int y;

    /**
     * The width of this rectangle
     */
    private final int width;

    /**
     * The height of this rectangle
     */
    private final int height;
    
    /**
     * Creates a new rectangle with the given parameters.
     * 
     * @param x The x-coordinate of this rectangle
     * @param y The y-coordinate of this rectangle
     * @param width The width of this rectangle
     * @param height The height of this rectangle
     */
    DefaultRectangle(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor
     * 
     * @param other The other {@link Rectangle}
     */
    DefaultRectangle(Rectangle other)
    {
        this(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }
    
}
