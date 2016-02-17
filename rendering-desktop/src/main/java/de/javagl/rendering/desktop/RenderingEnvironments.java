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
package de.javagl.rendering.desktop;



import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import de.javagl.rendering.core.RenderingEnvironment;

/**
 * This class is responsible for resolving services that may
 * create {@link RenderingEnvironment} instances.
 */
public class RenderingEnvironments
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(RenderingEnvironments.class.getName());

    /**
     * The ServiceLoader for RenderingEnvironmentFactories
     */
    private static final ServiceLoader<RenderingEnvironmentFactory>
        renderingEnvironmentFactories =
            ServiceLoader.load(RenderingEnvironmentFactory.class);


    /**
     * Creates a new {@link RenderingEnvironment}. This method will
     * try to obtain a service that implements the
     * {@link RenderingEnvironmentFactory} interface. This service
     * will be used to instantiate a RenderingEnvironment. The first
     * RenderingEnvironment that can successfully be instantiated
     * will be returned.
     *
     * @return A new {@link RenderingEnvironment}, or <code>null</code> if no
     * service for creating a {@link RenderingEnvironment} could be found.
     */
    public static RenderingEnvironment<Component> createRenderingEnvironment()
    {
        for (RenderingEnvironmentFactory f : renderingEnvironmentFactories)
        {
            logger.config("Found factory "+f);
        }

        Map<RenderingEnvironmentFactory, Throwable> failures =
            new LinkedHashMap<RenderingEnvironmentFactory, Throwable>();

        // For testing:
        String preference = "";
        //preference = "JOGL";
        //preference = "LWJGL";
        //preference = "JavaGL";
        for (RenderingEnvironmentFactory f : renderingEnvironmentFactories)
        {
            String className = f.getClass().getName();
            if (className.contains(preference))
            {
                RenderingEnvironment<Component> renderingEnvironment = null;
                try
                {
                    renderingEnvironment = f.createRenderingEnvironment();
                    logger.config("Created "+renderingEnvironment);
                    if (renderingEnvironment != null)
                    {
                        return renderingEnvironment;
                    }
                }
                catch (Throwable t)
                {
                    failures.put(f, t);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Could not instantiate a RenderingEnvironment\n");
        boolean foundFactory = false;
        for (RenderingEnvironmentFactory f : renderingEnvironmentFactories)
        {
            Throwable t = failures.get(f);
            if (t == null)
            {
                sb.append("Factory "+f+" did not create a non-null instance");
            }
            else
            {
                sb.append("Factory "+f+" reported an error:\n");
                sb.append(createString(t));
            }
            foundFactory = true;
        }
        if (!foundFactory)
        {
            sb.append(
                "No service implementing the RenderingEnvironmentFactory " +
                "interface has been found in the classpath");
        }
        logger.severe(sb.toString());

        return null;
    }

    /**
     * Create a String containing the stack trace of the given throwable
     * and all its causes.
     *
     * @param t The throwable
     * @return The string
     */
    private static String createString(Throwable t)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(t.toString()+"\n");
        for (StackTraceElement s : t.getStackTrace())
        {
            sb.append("\t"+s+"\n");
        }
        if (t.getCause() != null)
        {
            sb.append("Caused by:\n");
            sb.append(createString(t.getCause()));
        }
        return sb.toString();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private RenderingEnvironments()
    {
        // Private constructor to prevent instantiation
    }
}
