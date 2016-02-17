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

import javax.vecmath.Tuple3f;

import de.javagl.rendering.geometry.Ray;


/**
 * Interface for a {@link PickingResult} that resulted from
 * picking a triangle.
 * 
 * @param <T> The type of the object that was picked.
 */
public interface TrianglePickingResult<T> extends PickingResult<T>
{
    /**
     * Returns the index of the triangle that was picked.
     * 
     * @return The index of the triangle that was picked.
     */
    int getTriangleIndex();

    /**
     * Returns a copy of the barycentric coordinates of the point where the 
     * picking {@link Ray} intersected the triangle.
     * 
     * @return The barycentric coordinates of the intersection.
     */
    Tuple3f getBarycentricCoordinates();

}