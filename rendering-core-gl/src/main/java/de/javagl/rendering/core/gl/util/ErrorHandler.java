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

package de.javagl.rendering.core.gl.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.javagl.rendering.core.RenderingException;

/**
 * Utility class for handling GL errors. Mainly intended for debugging
 * and not part of the official API.
 */
public abstract class ErrorHandler
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ErrorHandler.class.getName());

    /**
     * The instance of the ErrorHandler that is currently used
     */
    private static ErrorHandler instance = createLoggingErrorHandler();

    /**
     * Set the instance of the ErrorHandler that should be used
     * 
     * @param errorHandler The ErrorHandler
     */
    public static void setInstance(ErrorHandler errorHandler)
    {
        ErrorHandler.instance = errorHandler;
    }
    
    /**
     * Creates an ErrorHandler that throws RenderingExceptions on errors
     * 
     * @return An ErrorHandler
     */
    public static ErrorHandler createExceptionErrorHandler()
    {
        return new ErrorHandler()
        {
            @Override
            public void handleError(String message, Throwable cause)
            {
                throw new RenderingException(message, cause);
            }
        };
    }

    /**
     * Creates an ErrorHandler that prints WARNING log messages on errors
     * 
     * @return An ErrorHandler
     */
    public static ErrorHandler createLoggingErrorHandler()
    {
        return new ErrorHandler()
        {
            @Override
            public void handleError(String message, Throwable cause)
            {
                logger.log(Level.WARNING, message, cause);
                if (cause != null)
                {
                    StackTraceElement st[] = cause.getStackTrace();
                    for (StackTraceElement ste : st)
                    {
                        logger.log(Level.WARNING, "\tat "+ste);
                    }
                }
            }
        };
    }

    /**
     * Handle the given error
     *  
     * @param message The message
     * @param cause The reason for the error
     */
    public static void handle(String message, Throwable cause)
    {
        instance.handleError(message, cause);
    }
    
    /**
     * Handle the given error
     *  
     * @param message The message
     */
    public static void handle(String message)
    {
        instance.handleError(message, null);
    }
    
    /**
     * Abstract method to handle errors
     * 
     * @param message The message
     * @param cause The reason for the error
     */
    public abstract void handleError(String message, Throwable cause);
}
