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

package de.javagl.rendering.core.handling;

/**
 * Interface for all classes that may handle a specific type of objects.
 * The objects may be handled and released, and the Handler implementation
 * will internally keep track of the current object state. This may 
 * consist of reference counting, or maintaining an internal representation
 * of the handled objects. 
 *
 * @param <T> The type of the handled objects
 * @param <U> The type of the internal representation of the objects.
 */
public interface Handler<T, U>
{
    /**
     * Handle the given object.
     * 
     * @param t The object to handle
     */
    void handle(T t);
    
    /**
     * Release the given object.
     * 
     * @param t The object to release.
     */
    void release(T t);
    
    /**
     * Returns the internal representation of the given object.
     * If there is no specific internal representation, then
     * <code>null</code> is returned.
     * 
     * @param t The object
     * @return The internal representation, or <code>null</code>
     */
    U getInternal(T t);
}
