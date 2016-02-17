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

package de.javagl.rendering.core.lwjgl;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL20.GL_CURRENT_PROGRAM;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;

import org.lwjgl.opengl.GL20;

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
 * Implementation of a {@link ProgramHandler} using LWJGL.
 */
class LWJGLProgramHandler 
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
     * Temporary storage for a program ID
     */
    private int tempPreviousProgram;
    
    /**
     * The current program, stored temporarily for setting uniforms
     */
    private int tempCurrentProgram;
    
    /**
     * Creates a new LWJGLProgramHandler
     */
    LWJGLProgramHandler()
    {
        //instance = this;
    }

    private static void printLogInfo(int id)
    {
        String log = GL20.glGetProgramInfoLog(id, 40000);
        if (log.trim().length() > 0)
        {
            System.out.println("LOG: "+log);
        }
    }
    
    @Override
    public GLProgram handleInternal(Program program)
    {
        int programID  = glCreateProgram();
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
            int shaderID = glCreateShader(shaderType);
            ByteBuffer sourceBuffer = 
                BufferUtils.toByteBuffer(shader.getSource());
            glShaderSource(shaderID, sourceBuffer);
            glCompileShader(shaderID);     
            printLogInfo(shaderID);
            glAttachShader(programID, shaderID);
            glDeleteShader(shaderID);
        }
        
        glLinkProgram(programID);
        printLogInfo(programID);
        glValidateProgram(programID);
        printLogInfo(programID);
        
        return glProgram;
    }

    @Override
    public void releaseInternal(Program program, GLProgram glProgram)
    {
        glDeleteProgram(glProgram.getProgram());
    }

    
    /**
     * Temporarily activate the given {@link Program}. Returns the GL 
     * program ID, or -1 if the program can not be found. 
     * 
     * This will store the GL program ID as the 'tempCurrentProgram' and
     * the program that was activated before this call in the
     * 'tempPreviousProgram'. A call to restoreProgram will restore
     * this previously activated program.
     *  
     * @param program The program
     * @return The program ID
     */
    private int useProgram(Program program)
    {
        GLProgram glProgram = getInternal(program);
        if (glProgram == null)
        {
            ErrorHandler.handle("GL Program not found for "+program);
            return -1;
        }
        tempPreviousProgram = glGetInteger(GL_CURRENT_PROGRAM);
        tempCurrentProgram = glProgram.getProgram();
        if (tempPreviousProgram != tempCurrentProgram)
        {
            glUseProgram(tempCurrentProgram);
        }
        return tempCurrentProgram;
    }
    
    /**
     * Restore the program that is stored in the tempPreviousProgram.
     * See {@link #useProgram(Program)}.
     */
    private void restoreProgram()
    {
        if (tempPreviousProgram != tempCurrentProgram)
        {
            glUseProgram(tempPreviousProgram);
        }
    }
    
    /*
     * Implementation note: When the 'location' that is obtained with
     * glGetUniformLocation is -1, then nothing is done.
     * The location is -1 when the uniform is not found (which could 
     * be considered as an error), but may also be -1 when the uniform 
     * is only not USED - which is no error
     */
    private static final boolean REPORT_INVALID_LOCATIONS = true;
    private void locationInvalid(Program program, String name)
    {
        if (REPORT_INVALID_LOCATIONS)
        {
            System.out.println("Location for "+name+" is invalid");
        }
    }
    
    @Override
    public void setMatrix3f(Program program, String name, Matrix3f value)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            MatrixUtils.writeMatrixToBuffer(value, tempMatrix3fBuffer);
            glUniformMatrix3(location, false, tempMatrix3fBuffer);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }


    @Override
    public void setMatrix4f(Program program, String name, Matrix4f value)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            MatrixUtils.writeMatrixToBuffer(value, tempMatrix4fBuffer);
            glUniformMatrix4(location, false, tempMatrix4fBuffer);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    @Override
    public void setFloat(Program program, String name, float value)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform1f(location, value);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    

    @Override
    public void setTuple2f(Program program, String name, Tuple2f tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform2f(location, tuple.x, tuple.y);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    @Override
    public void setTuple3f(Program program, String name, Tuple3f tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform3f(location, tuple.x, tuple.y, tuple.z);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    @Override
    public void setTuple4f(Program program, String name, Tuple4f tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        //System.out.println("Location of "+name+" is "+location);
        if (location != -1)
        {
            glUniform4f(location, tuple.x, tuple.y, tuple.z, tuple.w);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    @Override
    public void setInt(Program program, String name, int value)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform1i(location, value);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    

    @Override
    public void setTuple2i(Program program, String name, Tuple2i tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform2i(location, tuple.x, tuple.y);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    @Override
    public void setTuple3i(Program program, String name, Tuple3i tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform3i(location, tuple.x, tuple.y, tuple.z);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    @Override
    public void setTuple4i(Program program, String name, Tuple4i tuple)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        int location = glGetUniformLocation(programID, name);
        if (location != -1)
        {
            glUniform4i(location, tuple.x, tuple.y, tuple.z, tuple.w);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
    }
    
    
    
    GLAttribute getGLAttribute(Program program, String name)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return null;
        }
        GLAttribute glAttribute = null;
        int location = glGetAttribLocation(programID, name);
        if (location != -1)
        {
            glAttribute = DefaultGL.createGLAttribute(location);
        }
        else
        {
            locationInvalid(program, name);
        }
        restoreProgram();
        return glAttribute;
    }
    
    
    //========================================================================
    // XXX DEBUGGING HACKS!
//    /**
//     * ONLY FOR DEBUGGING! 
//     */
//    public static LWJGLProgramHandler instance;
//    /**
//     * ONLY FOR DEBUGGING! 
//     * @param program 
//     */
//    public void reloadProgram(Program program)
//    {
//        if (!(program instanceof DebugProgram))
//        {
//            System.err.println("Can not reload program of type "+program.getClass());
//            return;
//        }
//        GLProgram old = getInternal(program);
//        glDeleteProgram(old.getProgram());
//        ((DebugProgram)program).reload();
//        
//        GLProgram p = handleInternal(program);
//        try
//        {
//            java.lang.reflect.Field f = DefaultGLProgram.class.getDeclaredField("program");
//            f.setAccessible(true);
//            f.setInt(old, p.getProgram());
//            f.setAccessible(false);
//        }
//        catch (SecurityException e)
//        {
//            e.printStackTrace();
//        }
//        catch (NoSuchFieldException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IllegalArgumentException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IllegalAccessException e)
//        {
//            e.printStackTrace();
//        }
//        
//    }
}
