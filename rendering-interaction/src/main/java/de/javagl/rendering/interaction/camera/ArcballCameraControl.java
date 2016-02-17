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
package de.javagl.rendering.interaction.camera;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.MouseInputAdapter;

import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.interaction.Control;

/**
 * Implementation of a {@link Control} that modifies a {@link Camera} using
 * an {@link ArcballCameraBehavior}. <br>
 */
class ArcballCameraControl implements Control
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ArcballCameraControl.class.getName());
    
    /**
     * The listener that is maintained by this control. It will be
     * attached to or detached from the component, and dispatch all
     * relevant method calls to the {@link ArcballCameraBehavior}
     */
    private final class Listener extends MouseInputAdapter
        implements MouseListener, MouseMotionListener, MouseWheelListener
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            logger.log(Level.FINE, "mousePressed "+e);
            
            if (is(e, MouseEvent.BUTTON1_DOWN_MASK))
            {
                arcballCameraBehavior.startArcballRotate(e.getPoint());
            }
            if (is(e, MouseEvent.BUTTON3_DOWN_MASK))
            {
                arcballCameraBehavior.startMovement(e.getPoint());
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e)
        {
            logger.log(Level.FINE, "mouseClicked "+e);

            if (e.getButton() == MouseEvent.BUTTON2)
            {
                arcballCameraBehavior.reset();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            logger.log(Level.FINE, "mouseDragged "+e);

            if (is(e, MouseEvent.BUTTON1_DOWN_MASK))
            {
                arcballCameraBehavior.doArcballRotate(e.getPoint());
            }
            if (is(e, MouseEvent.BUTTON3_DOWN_MASK))
            {
                arcballCameraBehavior.doMovement(e.getPoint());
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            logger.log(Level.FINE, "mouseWheelMoved "+e);
            
            arcballCameraBehavior.zoom(e.getWheelRotation());
        }
    }
    
    /**
     * The controlled {@link ArcballCameraBehavior}
     */
    private final ArcballCameraBehavior arcballCameraBehavior;

    /**
     * The listener instance
     */
    private final Listener listener;

    /**
     * Whether this {@link Control} is currently enabled
     */
    private boolean enabled;
    
    /**
     * Creates a new ArcballCameraControl that controls the
     * given ArcballCameraBehavior
     * 
     * @param arcballCameraBehavior The ArcballCameraBehavior
     */
    ArcballCameraControl(ArcballCameraBehavior arcballCameraBehavior)
    {
        this.arcballCameraBehavior = arcballCameraBehavior;
        this.listener = new Listener();
        this.enabled = true;
    }

    /**
     * Returns whether the given flag is set in the extended
     * modifiers of the event.
     * 
     * @param e The event
     * @param flag The flag
     * @return Whether the flag is set
     */
    private static boolean is(InputEvent e, int flag)
    {
        return (e.getModifiersEx() & flag) == flag;
    }

    @Override
    public void attachTo(Component component)
    {
        MouseListener mouseListeners[] = 
            component.getListeners(MouseListener.class);
        if (!Arrays.asList(mouseListeners).contains(listener))
        {
            component.addMouseListener(listener);
            component.addMouseMotionListener(listener);
            component.addMouseWheelListener(listener);
        }
    }

    @Override
    public void detachFrom(Component component)
    {
        component.removeMouseListener(listener);
        component.removeMouseMotionListener(listener);
        component.removeMouseWheelListener(listener);
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }


}
