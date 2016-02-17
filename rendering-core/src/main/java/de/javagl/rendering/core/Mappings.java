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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Methods and classes for creating {@link Mapping} instances
 */
public class Mappings
{
    /**
     * A Builder for {@link Mapping} instances.
     * 
     * @param <BK> The key type
     * @param <BV> The value type
     */
    public static class Builder<BK, BV>
    {
        /**
         * The map for the {@link Mapping} that is currently being built
         */
        private Map<BK,BV> map;
        
        /**
         * Creates a Builder
         *  
         * @param <BBK> The key type 
         * @param <BBV> The value type
         * 
         * @return The builder
         */
        public static <BBK, BBV> Builder<BBK, BBV> create()
        {
            return new Builder<BBK, BBV>();
        }
        
        /**
         * Creates a new Builder
         */
        private Builder()
        {
            map = new LinkedHashMap<BK, BV>();
        }
        
        /**
         * Map the given key to the given value
         * 
         * @param key The key
         * @param value The value
         * @return This Builder
         */
        public Builder<BK,BV> put(BK key, BV value)
        {
            map.put(key, value);
            return this;
        }
        
        /**
         * Builds the {@link Mapping}.
         * 
         * @return The {@link Mapping}
         */
        public Mapping<BK, BV> build()
        {
            Mapping<BK, BV> result = new DefaultMapping<BK, BV>(map);
            return result;
        }
    }

    /**
     * Creates a new empty {@link Mapping}
     *
     * @param <K> The key type 
     * @param <V> The value type
     *
     * @return A new {@link Mapping}
     */
    public static <K, V> Mapping<K, V> create()
    {
        return Mappings.Builder.<K, V>create().build();
    }

    
    /**
     * Creates a new {@link Mapping} containing the given key-value pairs
     *
     * @param <K> The key type 
     * @param <RK> The returned key type 
     * @param <V> The value type
     * @param <RV> The returned key type 
     *
     * @param key0 The key
     * @param value0 The value
     * @return A new {@link Mapping}
     */
    public static <RK, K extends RK, RV, V extends RV> Mapping<RK, RV> create(
        K key0, V value0)
    {
        return Mappings.Builder.<RK, RV>create().
            put(key0, value0).
            build();
    }
    
    /**
     * Creates a new {@link Mapping} containing the given key-value pairs
     * 
     * @param <K> The key type 
     * @param <RK> The returned key type 
     * @param <V> The value type
     * @param <RV> The returned key type 
     *
     * @param key0 The key
     * @param value0 The value
     * @param key1 The key
     * @param value1 The value
     * @return A new {@link Mapping}
     */
    public static <RK, K extends RK, RV, V extends RV> Mapping<RK, RV> create(
        K key0, V value0,
        K key1, V value1)
    {
        return Mappings.Builder.<RK, RV>create().
            put(key0, value0).
            put(key1, value1).
            build();
    }
    
    
    /**
     * Creates a new {@link Mapping} containing the given key-value pairs
     *
     * @param <K> The key type 
     * @param <RK> The returned key type 
     * @param <V> The value type
     * @param <RV> The returned key type 
     *
     * @param key0 The key
     * @param value0 The value
     * @param key1 The key
     * @param value1 The value
     * @param key2 The key
     * @param value2 The value
     * @return A new {@link Mapping}
     */
    public static <RK, K extends RK, RV, V extends RV> Mapping<RK, RV> create(
        K key0, V value0,
        K key1, V value1,
        K key2, V value2)
    {
        return Mappings.Builder.<RK, RV>create().
            put(key0, value0).
            put(key1, value1).
            put(key2, value2).
            build();
    }

    /**
     * Creates a new {@link Mapping} containing the given key-value pairs
     *
     * @param <K> The key type 
     * @param <RK> The returned key type 
     * @param <V> The value type
     * @param <RV> The returned key type 
     *
     * @param key0 The key
     * @param value0 The value
     * @param key1 The key
     * @param value1 The value
     * @param key2 The key
     * @param value2 The value
     * @param key3 The key
     * @param value3 The value
     * @return A new {@link Mapping}
     */
    public static <RK, K extends RK, RV, V extends RV> Mapping<RK, RV> create(
        K key0, V value0,
        K key1, V value1,
        K key2, V value2,
        K key3, V value3)
    {
        return Mappings.Builder.<RK, RV>create().
            put(key0, value0).
            put(key1, value1).
            put(key2, value2).
            put(key3, value3).
            build();
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Mappings()
    {
        // Private constructor to prevent instantiation
    }

}
