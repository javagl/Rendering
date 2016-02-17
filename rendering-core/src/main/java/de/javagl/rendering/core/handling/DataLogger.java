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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.javagl.rendering.core.Renderer;


/**
 * Utility class that is used for logging the objects that are currently 
 * maintained by a {@link Renderer}. <br>
 * <br>
 * <b>This class is intended for debugging purposes, and not part of 
 * the official API.</b>
 */
public class DataLogger
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(DataLogger.class.getName());

    /**
     * The log level which is used for the outputs
     */
    private static Level level = Level.FINER;
    
    /**
     * The set of objects that are currently maintained
     */
    private static final Set<Object> set = 
        new LinkedHashSet<Object>();
    
    /**
     * Set the log level which is used for the outputs
     * 
     * @param level The log level which is used for the outputs
     */
    public static void setOutputLogLevel(Level level)
    {
        DataLogger.level = level;
    }
    
    /**
     * Called when the object is handled
     * 
     * @param object The object
     */
    public static void handling(Object object)
    {
        logger.log(level, "Handling  "+id(object)+":"+object);
        set.add(object);
        printContents();
    }

    /**
     * Called when the object is released
     * 
     * @param object The object
     */
    public static void releasing(Object object)
    {
        logger.log(level, "Releasing "+id(object)+":"+object);
        set.remove(object);
        printContents();
    }
    
    /**
     * Print the currently handled objects
     */
    private static void printContents()
    {
        logger.log(level, "Contents:");
        for (Object object : set)
        {
            logger.log(level, "    "+id(object)+":"+object);
        }
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
