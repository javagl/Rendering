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

package de.javagl.rendering.core.jogl;
import static com.jogamp.opengl.GL.GL_TRUE;
import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VALIDATE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntConsumer;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;

import com.jogamp.opengl.GL3;

import de.javagl.rendering.core.Program;
import de.javagl.rendering.core.Shader;
import de.javagl.rendering.core.ShaderType;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLAttribute;
import de.javagl.rendering.core.gl.GLProgram;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.handling.AbstractProgramHandler;
import de.javagl.rendering.core.handling.ProgramHandler;
import de.javagl.rendering.core.utils.BufferUtils;
import de.javagl.rendering.core.utils.MatrixUtils;


/**
 * Implementation of a {@link ProgramHandler} using JOGL.
 */
class JOGLProgramHandler 
    extends AbstractProgramHandler<GLProgram> 
    implements ProgramHandler<GLProgram>
{
    /**
     * Temporary buffer for 3x3 float matrices
     */
    private final FloatBuffer tempMatrix3fBuffer = 
        BufferUtils.createFloatBuffer(9);

    /**
     * Temporary buffer for 4x4 float matrices
     */
    private final FloatBuffer tempMatrix4fBuffer = 
        BufferUtils.createFloatBuffer(16);

    /**
     * A map from {@link Program} instances to the runnables that have
     * to be executed in order to update the uniforms of the given 
     * program. The values of this map are maps that map uniform 
     * names to the runnables that update the respective uniform. 
     */
    private final Map<Program, Map<String, Runnable>> pendingSetters = 
        new LinkedHashMap<Program, Map<String, Runnable>>();
    
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * Set the current GL instance
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        this.gl = gl;
    }
    

    @Override
    public GLProgram handleInternal(Program program)
    {
        final boolean alwaysPrintLog = true;
        
        int programID  = gl.glCreateProgram();
        GLProgram glProgram = DefaultGL.createGLProgram(programID);
        
        for (Shader shader : program.getShaders())
        {
            int shaderType = 0;
            ShaderType type = shader.getType();
            switch (type)
            {
                case VERTEX:
                    shaderType = GL_VERTEX_SHADER;
                    break;
                case FRAGMENT:
                    shaderType = GL_FRAGMENT_SHADER;
                    break;
                default: 
                    ErrorHandler.handle("Unhandled shader type: "+type);
            }
            int shaderID = gl.glCreateShader(shaderType);
            
            gl.glShaderSource(
                shaderID, 1, new String[]{shader.getSource()}, null);
            gl.glCompileShader(shaderID);     

            int compileStatus[] = { 0 };
            gl.glGetShaderiv(shaderID, GL_COMPILE_STATUS, compileStatus, 0);
            if (compileStatus[0] != GL_TRUE || alwaysPrintLog)
            {
                printShaderLogInfo(shaderID);
            }
            gl.glAttachShader(programID, shaderID);
            gl.glDeleteShader(shaderID);
        }
        gl.glLinkProgram(programID);
        gl.glValidateProgram(programID);
        
        int validateStatus[] = { 0 };
        gl.glGetProgramiv(programID, GL_VALIDATE_STATUS, validateStatus, 0);
        if (validateStatus[0] != GL_TRUE || alwaysPrintLog)
        {
            printProgramLogInfo(programID);
        }
        
        return glProgram;
    }
    
    @Override
    public void releaseInternal(Program program, GLProgram glProgram)
    {
        gl.glDeleteProgram(glProgram.getProgram());
        pendingSetters.remove(program);
    }
    
    /**
     * Package-private method used by the {@link JOGLRenderedObjectHandler}
     * to update the state of the given program, after it has been activated
     * with <code>glUseProgram</code>. This will execute all
     * {@link #pendingSetters} for the program, and remove them from
     * the map.
     *  
     * @param program The program.
     */
    void executeSetters(Program program)
    {
        Map<String, Runnable> setters = pendingSetters.get(program);
        if (setters != null)
        {
            setters.values().forEach(r -> r.run());
            pendingSetters.remove(program);
        }
    }

    
    private static final boolean REPORT_INVALID_LOCATIONS = false;
    private void locationInvalid(Program program, String name)
    {
        if (REPORT_INVALID_LOCATIONS)
        {
            System.out.println("Location for "+name+" is invalid");
        }
    }
    
    /**
     * Create a setter for the uniform with the given name in the given
     * program. This setter will be executed when {@link #executeSetters}
     * is called.
     * 
     * @param program The {@link Program}
     * @param name The name of the uniform
     * @param glSetter The actual setter that receives the uniform 
     * location, and calls the <code>glUniform*</code> method.
     */
    private void createSetter(
        Program program, String name, IntConsumer glSetter)
    {
        Map<String, Runnable> setters = pendingSetters.computeIfAbsent(
            program, p -> new LinkedHashMap<String, Runnable>());
        Runnable setter = () ->
        {
            GLProgram glProgram = getInternal(program);
            if (glProgram == null)
            {
                ErrorHandler.handle("GL Program not found for "+program);
            }
            else
            {
                int programID = glProgram.getProgram();
                int location = gl.glGetUniformLocation(programID, name);
                if (location != -1)
                {
                    glSetter.accept(location);
                }
                else
                {
                    locationInvalid(program, name);
                }
            }
        };
        setters.put(name, setter);
    }
    
    @Override
    public void setMatrix3f(Program program, String name, Matrix3f value)
    {
        Matrix3f localValue = new Matrix3f(value);
        createSetter(program, name, location ->
        {
            MatrixUtils.writeMatrixToBuffer(localValue, tempMatrix3fBuffer);
            gl.glUniformMatrix3fv(location, 1, false, tempMatrix3fBuffer);
        });
    }


    @Override
    public void setMatrix4f(Program program, String name, Matrix4f value)
    {
        Matrix4f localValue = new Matrix4f(value);
        createSetter(program, name, location ->
        {
            MatrixUtils.writeMatrixToBuffer(localValue, tempMatrix4fBuffer);
            gl.glUniformMatrix4fv(location, 1, false, tempMatrix4fBuffer);
        });
    }
    
    @Override
    public void setFloat(Program program, String name, float value)
    {
        createSetter(program, name, location ->
        {
            gl.glUniform1f(location, value);
        });
    }
    

    @Override
    public void setTuple2f(Program program, String name, Tuple2f tuple)
    {
        float x = tuple.x;
        float y = tuple.y;
        createSetter(program, name, location ->
        {
            gl.glUniform2f(location, x, y);
        });
    }
    
    
    @Override
    public void setTuple3f(Program program, String name, Tuple3f tuple)
    {
        float x = tuple.x;
        float y = tuple.y;
        float z = tuple.z;
        createSetter(program, name, location ->
        {
            gl.glUniform3f(location, x, y, z);
        });
    }
    
    
    @Override
    public void setTuple4f(Program program, String name, Tuple4f tuple)
    {
        float x = tuple.x;
        float y = tuple.y;
        float z = tuple.z;
        float w = tuple.w;
        createSetter(program, name, location ->
        {
            gl.glUniform4f(location, x, y, z, w);
        });
    }
    
    
    @Override
    public void setInt(Program program, String name, int value)
    {
        createSetter(program, name, location ->
        {
            gl.glUniform1i(location, value);
        });
    }
    

    @Override
    public void setTuple2i(Program program, String name, Tuple2i tuple)
    {
        int x = tuple.x;
        int y = tuple.y;
        createSetter(program, name, location ->
        {
            gl.glUniform2i(location, x, y);
        });
    }
    
    
    @Override
    public void setTuple3i(Program program, String name, Tuple3i tuple)
    {
        int x = tuple.x;
        int y = tuple.y;
        int z = tuple.z;
        createSetter(program, name, location ->
        {
            gl.glUniform3i(location, x, y, z);
        });
    }
    
    
    @Override
    public void setTuple4i(Program program, String name, Tuple4i tuple)
    {
        int x = tuple.x;
        int y = tuple.y;
        int z = tuple.z;
        int w = tuple.w;
        createSetter(program, name, location ->
        {
            gl.glUniform4i(location, x, y, z, w);
        });
    }
    
    
    /**
     * Package-private method used by the {@link JOGLRenderedObjectHandler} to
     * obtain the {@link GLAttribute} for an attribute with the given name
     * from the program that is currently active (i.e. which was activated
     * with <code>glUseProgram</code>).
     * 
     * @param program The program
     * @param name The name
     * @return The attribute
     */
    GLAttribute getGLAttribute(Program program, String name)
    {
        GLProgram glProgram = getInternal(program);
        if (glProgram == null)
        {
            ErrorHandler.handle("GL Program not found for "+program);
            return null;
        }
        GLAttribute glAttribute = null;
        int programID = glProgram.getProgram();
        int location = gl.glGetAttribLocation(programID, name);
        if (location != -1)
        {
            glAttribute = DefaultGL.createGLAttribute(location);
        }
        else
        {
            locationInvalid(program, name);
        }
        return glAttribute;
    }
    

    /**
     * For debugging: Print shader log info
     * 
     * @param id shader ID
     */
    private void printShaderLogInfo(int id) 
    {
        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4)
            .order(ByteOrder.nativeOrder()).asIntBuffer();
        gl.glGetShaderiv(id, GL3.GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength.get(0) > 0) 
        {
            infoLogLength.put(0, infoLogLength.get(0)-1);
        }

        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0))
            .order(ByteOrder.nativeOrder());
        gl.glGetShaderInfoLog(id, infoLogLength.get(0), null, infoLog);

        String infoLogString =
            Charset.forName("US-ASCII").decode(infoLog).toString();
        if (infoLogString.trim().length() > 0)
        {
            ErrorHandler.handle("shader log:\n"+infoLogString);
        }
    }    

    /**
     * For debugging: Print program log info
     * 
     * @param id program ID
     */
    private void printProgramLogInfo(int id) 
    {
        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4)
            .order(ByteOrder.nativeOrder()).asIntBuffer();
        gl.glGetProgramiv(id, GL3.GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength.get(0) > 0) 
        {
            infoLogLength.put(0, infoLogLength.get(0)-1);
        }

        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0))
            .order(ByteOrder.nativeOrder());
        gl.glGetProgramInfoLog(id, infoLogLength.get(0), null, infoLog);

        String infoLogString = 
            Charset.forName("US-ASCII").decode(infoLog).toString();
        if (infoLogString.trim().length() > 0)
        {
            ErrorHandler.handle("program log:\n"+infoLogString);
        }
    }    
    
}
