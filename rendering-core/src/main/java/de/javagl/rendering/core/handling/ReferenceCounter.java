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

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Simple implementation of a reference counter
 */
public final class ReferenceCounter
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ReferenceCounter.class.getName());
    
    /**
     * The log level which will be used for status reports
     */
    private static Level level = Level.FINE;
    
    /**
     * Set the log level which will be used for status reports
     * 
     * @param level The log level which will be used for status reports
     */
    public static void setOutputLogLevel(Level level)
    {
        ReferenceCounter.level = level;
    }
    
    /**
     * The map from objects to their reference count
     */
    private final Map<Object, Integer> map;
    
    /**
     * Creates a new ReferenceCounter
     */
    public ReferenceCounter()
    {
        map = new IdentityHashMap<Object, Integer>();
    }

    /**
     * Increase the reference count for the given object
     * 
     * @param object The object
     */
    public void increase(Object object)
    {
        Integer oldValue = map.get(object);
        if (oldValue == null)
        {
            map.put(object, 1);
        }
        else
        {
            map.put(object, oldValue+1);
        }
        if (logger.isLoggable(level))
        {
            logger.log(level, "ReferenceCounter increased to "+
                map.get(object)+" for "+id(object)+": "+object);
        }
    }
    
    /**
     * Decrease the reference count for the given object
     * 
     * @param object The object
     */
    public void decrease(Object object)
    {
        Integer oldValue = map.get(object);
        if (oldValue.equals(Integer.valueOf(1)))
        {
            map.remove(object);
        }
        else
        {
            map.put(object, oldValue-1);
        }
        if (logger.isLoggable(level))
        {
            logger.log(level, "ReferenceCounter decreased to "+
                map.get(object)+" for "+id(object)+": "+object);
        }
    }
    
    /**
     * Returns whether the given object has no more references
     * 
     * @param object The object
     * @return Whether the given object has no more references
     */
    public boolean isZero(Object object)
    {
        return !map.containsKey(object);
    }
    
    /**
     * Returns a formatted String of the identity hash code of the
     * given object
     * 
     * @param object The object
     * @return The string
     */
    private static String id(Object object)
    {
        return String.format("%8s", System.identityHashCode(object));
    }
    
}
