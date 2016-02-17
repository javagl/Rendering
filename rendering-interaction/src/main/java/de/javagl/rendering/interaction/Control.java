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

package de.javagl.rendering.interaction;

import java.awt.Component;

/**
 * This interface describes a control that may be attached to or detached 
 * from a component. A control usually summarizes a set of listeners or 
 * input actions. A control may be enabled or disabled, offering the 
 * possibility to switch between different control modes.
 */
public interface Control
{
    /**
     * Attach this control to the given component. This usually will attach 
     * the listeners that are associated with this control to the given 
     * component. In order to properly remove the listeners when the control
     * is no longer used, the {@link #detachFrom(Component)} method has to 
     * be called.
     * 
     * @param component The component to attach to
     */
    void attachTo(Component component);

    /**
     * Detach this control from the given component. This will remove all 
     * listeners from the component that have been added when 
     * {@link #attachTo(Component)} was called.
     * 
     * @param component The component to detach from
     */
    void detachFrom(Component component);
    
    /**
     * Enable or disable this control
     * 
     * @param enabled Whether this control is enabled
     */
    void setEnabled(boolean enabled);
    
    /**
     * Returns whether this control is enabled
     * 
     * @return Whether this control is enabled
     */
    boolean isEnabled();
}
