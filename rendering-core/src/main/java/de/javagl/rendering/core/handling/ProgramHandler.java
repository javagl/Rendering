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

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;

import de.javagl.rendering.core.Program;


/**
 * A class that is a {@link Handler} for 
 * {@link Program} instances.
 * 
 * @param <U> The type of the internal representation of the
 * objects handled by this class. 
 */
public interface ProgramHandler<U> extends Handler<Program, U>
{
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setMatrix3f(Program program, String name, Matrix3f value);

    /**
     * Set the values of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setMatrix3f(Program program, String name, Matrix3f ... values);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setMatrix4f(Program program, String name, Matrix4f value);

    /**
     * Set the values of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setMatrix4f(Program program, String name, Matrix4f ... values);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setFloat(Program program, String name, float value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setFloat(Program program, String name, float ... values);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple2f(Program program, String name, Tuple2f value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple2f(Program program, String name, Tuple2f ... values);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple3f(Program program, String name, Tuple3f value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple3f(Program program, String name, Tuple3f ... values);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple4f(Program program, String name, Tuple4f value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple4f(Program program, String name, Tuple4f ... values);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setInt(Program program, String name, int value);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setInt(Program program, String name, int ... values);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple2i(Program program, String name, Tuple2i value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple2i(Program program, String name, Tuple2i ... values);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple3i(Program program, String name, Tuple3i value);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple3i(Program program, String name, Tuple3i ... values);

    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param value The value
     */
    void setTuple4i(Program program, String name, Tuple4i value);
    
    /**
     * Set the value of the specified uniform of the given {@link Program}
     *   
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param values The values
     */
    void setTuple4i(Program program, String name, Tuple4i ... values);
    
}
