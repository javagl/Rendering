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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Default implementation of an {@link Mapping}.
 * 
 * @param <K> The key type
 * @param <V> The value type
 */
class DefaultMapping<K,V> implements Mapping<K,V>
{
    /**
     * The Map backing this {@link Mapping}
     */
    private final Map<K, V> map;

    /**
     * Creates a new {@link Mapping} with the same contents as the
     * given map
     * 
     * @param <KK> The key type 
     * @param <VV> The value type
     * 
     * @param map The input map
     */
    <KK extends K, VV extends V> DefaultMapping(Map<KK, VV> map)
    {
        this.map = Collections.unmodifiableMap(new LinkedHashMap<K, V>(map));
    }
    
    @Override
    public V get(K key)
    {
        return map.get(key);
    }

    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }
    
    @Override
    public Collection<V> values()
    {
        return map.values();
    }
    
    @Override
    public String toString()
    {
        return "DefaultMapping["+
            "map="+map+"]";
    }

    @Override
    public int hashCode()
    {
        return map.hashCode();
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
        DefaultMapping<?,?> other = (DefaultMapping<?,?>) object;
        if (map == null)
        {
            if (other.map != null)
            {
                return false;
            }
        } 
        else if (!map.equals(other.map))
        {
            return false;
        }
        return true;
    }
    


}

    