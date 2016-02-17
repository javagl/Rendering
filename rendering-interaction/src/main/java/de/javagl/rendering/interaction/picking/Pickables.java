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

package de.javagl.rendering.interaction.picking;

import de.javagl.rendering.geometry.Geometry;

/**
 * Methods for creating {@link Pickable} objects
 */
public class Pickables
{
    /**
     * Create a {@link Pickable} that allows picking the given 
     * {@link Geometry} and returns a {@link TrianglePickingResult}
     * for the picking.
     * 
     * @param <T> The type of the picked object
     * @param geometry The {@link Geometry}
     * @return The {@link Pickable}
     */
    public static <T extends Geometry> Pickable<TrianglePickingResult<T>> 
        createPickableGeometry(T geometry)
    {
        return new PickableGeometry<T>(geometry);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Pickables()
    {
        // Private constructor to prevent instantiation
    }
}
