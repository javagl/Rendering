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

import java.util.Collection;
import java.util.Set;

/**
 * A generic mapping (basically, a read-only map). Mapping instances may
 * be created with the {@link Mappings} class.
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public interface Mapping<K, V>
{
    /**
     * Returns an unmodifiable view on the keys of this mapping
     * 
     * @return The key set of this mapping
     */
    Set<K> keySet();
    
    /**
     * Returns an unmodifiable view on the values of this mapping.
     * 
     * @return The values of this mapping
     */
    Collection<V> values();
    
    /**
     * Returns the value for the given key, or <code>null</code> if 
     * the key is not mapped to any value
     * 
     * @param key The key
     * @return The value
     */
    V get(K key);

}
