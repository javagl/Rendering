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
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL3;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;

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
     * Temporary buffer for a program ID
     */
    private final int tempPreviousProgramArray[] = {0};
    
    /**
     * The current program, stored temporarily for setting uniforms
     */
    private int tempCurrentProgram;
    
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
            //printShaderLogInfo(shaderID);
            gl.glAttachShader(programID, shaderID);
            gl.glDeleteShader(shaderID);
        }
        gl.glLinkProgram(programID);
        gl.glValidateProgram(programID);
        //printProgramLogInfo(programID);
        
        return glProgram;
    }
    
    @Override
    public void releaseInternal(Program program, GLProgram glProgram)
    {
        gl.glDeleteProgram(glProgram.getProgram());
    }
    
    /**
     * Temporarily activate the given {@link Program}. Returns the GL 
     * program ID, or -1 if the program can not be found. 
     * 
     * This will store the GL program ID as the 'tempCurrentProgram' and
     * the program that was activated before this call in the
     * 'tempPreviousProgramArray'. A call to restoreProgram will restore
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
        gl.glGetIntegerv(GL3.GL_CURRENT_PROGRAM, 
            tempPreviousProgramArray, 0);
        tempCurrentProgram = glProgram.getProgram();
        if (tempPreviousProgramArray[0] != tempCurrentProgram)
        {
            gl.glUseProgram(tempCurrentProgram);
        }
        return tempCurrentProgram;
    }
    
    /**
     * Restore the program that is stored in the tempPreviousProgramArray.
     * See {@link #useProgram(Program)}.
     */
    private void restoreProgram()
    {
        if (tempPreviousProgramArray[0] != tempCurrentProgram)
        {
            gl.glUseProgram(tempPreviousProgramArray[0]);
        }
    }

    /*
     * Implementation note: When the 'location' that is obtained with
     * glGetUniformLocation is -1, then nothing is done.
     * The location is -1 when the uniform is not found (which could 
     * be considered as an error), but may also be -1 when the uniform 
     * is only not USED - which is no error
     */

    @Override
    public void setMatrix3f(Program program, String name, Matrix3f value)
    {
        int programID = useProgram(program);
        if (programID == -1)
        {
            return;
        }
        MatrixUtils.writeMatrixToBuffer(value, tempMatrix3fBuffer);
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniformMatrix3fv(location, 1, false, tempMatrix3fBuffer);
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
        MatrixUtils.writeMatrixToBuffer(value, tempMatrix4fBuffer);
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniformMatrix4fv(location, 1, false, tempMatrix4fBuffer);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform1f(location, value);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform2f(location, tuple.x, tuple.y);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform3f(location, tuple.x, tuple.y, tuple.z);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform4f(location, tuple.x, tuple.y, tuple.z, tuple.w);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform1i(location, value);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform2i(location, tuple.x, tuple.y);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform3i(location, tuple.x, tuple.y, tuple.z);
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
        int location = gl.glGetUniformLocation(programID, name);
        if (location != -1)
        {
            gl.glUniform4i(location, tuple.x, tuple.y, tuple.z, tuple.w);
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
        int location = gl.glGetAttribLocation(programID, name);
        if (location != -1)
        {
            glAttribute = DefaultGL.createGLAttribute(location);
        }
        restoreProgram();
        return glAttribute;
    }

    
    
    
    
//    private void printProgramLogInfo(int id) 
//    {
//        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
//        gl.glGetProgramiv(id, GL3.GL_INFO_LOG_LENGTH, infoLogLength);
//
//        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0)).order(ByteOrder.nativeOrder());
//        gl.glGetProgramInfoLog(id, infoLogLength.get(0), null, infoLog);
//
//        String infoLogString =
//                Charset.forName("US-ASCII").decode(infoLog).toString();
//        System.out.println("program Log: "+infoLogString);
//    }    
//
//    private void printShaderLogInfo(int id) 
//    {
//        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
//        gl.glGetShaderiv(id, GL3.GL_INFO_LOG_LENGTH, infoLogLength);
//
//        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0)).order(ByteOrder.nativeOrder());
//        gl.glGetShaderInfoLog(id, infoLogLength.get(0), null, infoLog);
//
//        String infoLogString =
//                Charset.forName("US-ASCII").decode(infoLog).toString();
//        System.out.println("shader Log: "+infoLogString);
//    }    
    
}
