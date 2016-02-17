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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Abstract base implementation of a {@link Handler} that
 * uses a reference counter.
 * 
 * @param <T> The type of the handled objects
 * @param <U> The type of the internal representation of the
 * objects handled by this class  
 */
public abstract class AbstractReferenceHandler<T, U> implements Handler<T, U>
{
    /**
     * Map from handled object to the corresponding internal representations.
     */
    private final Map<T, U> internalObjects;

    /**
     * The reference counter for the objects handled by this class
     */
    private final ReferenceCounter referenceCounter;
    
    /**
     * Creates a new AbstractReferenceHandler
     */
    protected AbstractReferenceHandler()
    {
        internalObjects = new LinkedHashMap<T, U>();
        referenceCounter = new ReferenceCounter();
    }
    
    /**
     * Protected method that releases all objects maintained by this
     * handler, and returns them. This method is intended for the 
     * case that a complete re-initialization of the objects is
     * required, and should be called with care. 
     * 
     * @return The collections of objects that have been released
     */
    protected final Collection<T> releaseAll()
    {
        Set<T> objects = new LinkedHashSet<T>(internalObjects.keySet()); 
        for (T t : objects)
        {
            release(t);
        }
        return objects;
    }
    
    /**
     * Protected method that handles all objects from the given collection.
     * This method is intended for the case that a complete re-initialization 
     * of the objects is required, and should be called with care.
     *  
     * @param objects The objects that should be handled. 
     */
    protected final void handleAll(Collection<? extends T> objects)
    {
        for (T t : objects)
        {
            handle(t);
        }
    }
    
    
    @Override
    public final U getInternal(T t)
    {
        U result = internalObjects.get(t);
//        System.out.println("Int "+this+" : "+System.identityHashCode(t)+" gives     "+System.identityHashCode(result));
//        for (T key : internalObjects.keySet())
//        {
//            System.out.println("In "+this+" : "+System.identityHashCode(key)+" mapped to "+System.identityHashCode(internalObjects.get(key)));
//        }
        return result;
    }

    @Override
    public final void handle(T t)
    {
        referenceCounter.increase(t);
        
        handleChildren(t);
        
        if (internalObjects.containsKey(t))
        {
            return;
        }
        DataLogger.handling(t);
        
        U internal = handleInternal(t);
        internalObjects.put(t, internal);
    }



    @Override
    public final void release(T t)
    {
        referenceCounter.decrease(t);
        if (referenceCounter.isZero(t))
        {
            DataLogger.releasing(t);
            
            U internal = internalObjects.get(t);
            releaseInternal(t, internal);
            internalObjects.remove(t);
        }
        
        releaseChildren(t);
    }
    
    /**
     * Handle all children (sub-objects) of the given object. This will be 
     * called when the object is passed to the {@link #handle(Object)} 
     * method, before the handler checks whether this object is already 
     * handled by this class and, if it is not handled yet, calls  
     * {@link #handleInternal(Object)} with the respective object.
     * 
     * @param t The object
     */
    protected abstract void handleChildren(T t);
    
    /**
     * Release all children (sub-object) of the given object. This will be 
     * called when the object is passed to the {@link #release(Object)} 
     * method, after the handler has checked whether the reference counter
     * of the object became zero, and possibly called 
     * {@link #releaseInternal(Object, Object)} for the object.
     * 
     * @param t The object
     */
    protected abstract void releaseChildren(T t);
    
    /**
     * Handle the given object and return its internal representation.
     * This will be called when the object is passed to the
     * {@link #handle(Object)} method and it is not yet handled
     * by this class. (Note: When the object is already handled
     * by this class, then this method will <b>not</b> be called!)
     * 
     * @param t The object
     * @return The internal representation.
     */
    protected abstract U handleInternal(T t);
    
    /**
     * Release the given internal representation for the
     * given object.
     * This will be called when the object is passed to the
     * {@link #release(Object)} method and this call causes
     * the reference counter to decrease to zero.
     * 
     * @param t The objects
     * @param internal The internal representation
     */
    protected abstract void releaseInternal(T t, U internal);
    
}
