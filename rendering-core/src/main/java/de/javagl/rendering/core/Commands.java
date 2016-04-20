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

package de.javagl.rendering.core;


import static de.javagl.rendering.core.Parameters.MODEL_MATRIX;
import static de.javagl.rendering.core.Parameters.NORMAL_MATRIX;
import static de.javagl.rendering.core.Parameters.PROJECTION_MATRIX;
import static de.javagl.rendering.core.Parameters.VIEW_MATRIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point2f;
import javax.vecmath.Point2i;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Point4f;
import javax.vecmath.Point4i;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;
import javax.vecmath.Vector3f;

import de.javagl.rendering.core.Parameters.LightParameters;
import de.javagl.rendering.core.handling.ProgramHandler;
import de.javagl.rendering.core.light.Light;
import de.javagl.rendering.core.light.LightSetup;
import de.javagl.rendering.core.light.LightType;
import de.javagl.rendering.core.material.Material;
import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.core.view.Camera;
import de.javagl.rendering.core.view.CameraUtils;
import de.javagl.rendering.core.view.View;


/**
 * Methods for creating commonly used {@link Command} instances
 */
public class Commands
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(Commands.class.toString());
    
    /**
     * The log level for {@link #tracing(Command)}
     */
    private static final Level traceLogLevel = Level.INFO;
    
    /**
     * Compile-time flag which indicates whether the stack
     * traces of commands should be preserved and printed 
     * when a command causes an exception.
     */
    private static boolean PRESERVE_STACK_TRACES = true;
    
    /**
     * Create a {@link Command} that executes the commands provided 
     * by the {@link Command} suppliers of the given 
     * Iterable.
     * 
     * @param iterable The Iterable for the {@link Command} suppliers.
     * @return The new {@link Command}
     */
    public static Command combine(
        Iterable<? extends Supplier<? extends Command>> iterable)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                for (Supplier<? extends Command> supplier : iterable)
                {
                    Command command = supplier.get();
                    command.execute(renderer);
                }
            }

            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Compound command from suppliers:\n");
                for (Supplier<? extends Command> supplier : iterable)
                {
                    Command command = supplier.get();
                    sb.append("    "+String.valueOf(command)+"\n");
                }
                return sb.toString();
            }
        });        
    }
    
    /**
     * Create a {@link Command} that executes the {@link Command}s 
     * provided by the given Iterable.
     * 
     * @param iterable The Iterable for the {@link Command}s
     * @return The new {@link Command}
     */
    public static Command create(Iterable<? extends Command> iterable)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                for (Command command : iterable)
                {
                    command.execute(renderer);
                }
            }

            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Compound command:\n");
                for (Command command : iterable)
                {
                    sb.append("    "+String.valueOf(command)+"\n");
                }
                return sb.toString();
            }
        });        
    }

    /**
     * Create a {@link Command} that executes the given {@link Command}s. 
     * A copy of the given array will be stored internally.
     * 
     * @param commands The {@link Command}s
     * @return The {@link Command}
     */
    public static Command create(Command ...commands)
    {
        Iterable<? extends Command> iterable =
            new ArrayList<Command>(Arrays.asList(commands));
        return create(iterable);
    }

    /**
     * Create a {@link Command} that executes the given {@link Command}s. 
     * A copy of the given collection will be stored internally.
     * 
     * @param commands The {@link Command}s
     * @return The {@link Command}
     */
    public static Command create(Collection<? extends Command> commands)
    {
        Iterable<? extends Command> iterable =
            new ArrayList<Command>(commands);
        return create(iterable);
    }
    
    
    /**
     * Creates a {@link Command} to render the given object.
     * 
     * @param renderedObject The object to render
     * @return The new {@link Command}
     */
    public static Command render(RenderedObject renderedObject)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getRenderedObjectHandler().render(renderedObject);
            }
            
            @Override
            public String toString()
            {
                return "render("+renderedObject+")";
            }
            
        });        
    }
    
    
    /**
     * Creates a new {@link Command} that activates the given 
     * {@link FrameBuffer}
     *
     * @param frameBuffer The {@link FrameBuffer}
     * @return The new {@link Command}
     */
    public static Command setFrameBufferActive(FrameBuffer frameBuffer)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getFrameBufferHandler().setFrameBufferActive(
                    frameBuffer);
            }
            
            @Override
            public String toString()
            {
                return "setFrameBufferActive("+frameBuffer+")";
            }
            
        });        
    }
    
    
    
    
    /**
     * Creates a {@link Command} that sets the view matrix for
     * the given {@link Program}.  
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     *   
     * @param program The program
     * @param viewMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @return The new {@link Command}
     */
    public static Command setViewMatrix(
        Program program, String viewMatrixName, View view)
    {
        return setMatrix4f(program, viewMatrixName, () ->
        {
            Camera camera = view.getCamera();
            Matrix4f viewMatrix = 
                CameraUtils.computeViewMatrix(camera);
            return viewMatrix;
        });
    }

    /**
     * Creates a {@link Command} that sets the model-view matrix for
     * the given {@link Program}.  
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     * The model matrix is obtained from the given supplier.
     *   
     * @param program The program
     * @param modelViewMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @param modelMatrixSupplier The supplier for the model matrix
     * @return The new {@link Command}
     */
    public static Command setModelViewMatrix(
        Program program, String modelViewMatrixName, View view,
        Supplier<Matrix4f> modelMatrixSupplier)
    {
        return setMatrix4f(program, modelViewMatrixName, () ->
        {
            Matrix4f modelMatrix = modelMatrixSupplier.get();
            Camera camera = view.getCamera();
            Matrix4f viewMatrix = 
                CameraUtils.computeViewMatrix(camera);
            Matrix4f modelviewMatrix = 
                MatrixUtils.mul(viewMatrix, modelMatrix);
            return modelviewMatrix;
        });
    }

    /**
     * Creates a {@link Command} that sets the model-view matrix for
     * the given {@link Program}.  
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setModelViewMatrix(Program, String, View, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     *   
     * @param program The program
     * @param modelViewMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @param modelMatrix The model matrix
     * @return The new {@link Command}
     */
    public static Command setModelViewMatrix(
        Program program, String modelViewMatrixName, View view,
        Matrix4f modelMatrix)
    {
        return setModelViewMatrix(program, modelViewMatrixName, view,
            Suppliers.constantSupplier(new Matrix4f(modelMatrix)));
    }
    
    /**
     * Creates a {@link Command} that sets the projection matrix for
     * the given {@link Program}.  
     * The projection matrix is obtained from the given view by 
     * calling {@link View#getProjectionMatrix()}.
     *   
     * @param program The program
     * @param viewMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @return The new {@link Command}
     */
    public static Command setProjectionMatrix(
        Program program, String viewMatrixName, View view)
    {
        return setMatrix4f(program, viewMatrixName, 
            view::getProjectionMatrix);
    }
    
    
    /**
     * Creates a {@link Command} that sets the normal matrix for
     * the given {@link Program}, as a 4x4 matrix.  
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     * The model matrix is obtained from the given supplier.
     *   
     * @param program The program
     * @param normalMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @param modelMatrixSupplier The supplier for the model matrix
     * @return The new {@link Command}
     */
    public static Command setNormalMatrix4f(
        Program program, String normalMatrixName, View view,
        Supplier<Matrix4f> modelMatrixSupplier)
    {
        return setMatrix4f(program, normalMatrixName, () ->
        {
            Matrix4f modelMatrix = modelMatrixSupplier.get();
            Camera camera = view.getCamera();
            Matrix4f viewMatrix = 
                CameraUtils.computeViewMatrix(camera);
            Matrix4f modelviewMatrix = 
                MatrixUtils.mul(viewMatrix, modelMatrix);
            Matrix4f normalMatrix =
                MatrixUtils.transposed(
                    MatrixUtils.inverse(modelviewMatrix));
            return normalMatrix;
        });
    }

    /**
     * Creates a {@link Command} that sets the normal matrix for
     * the given {@link Program}, as a 3x3 matrix.
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     * The model matrix is obtained from the given supplier.
     *   
     * @param program The program
     * @param normalMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @param modelMatrixSupplier The supplier for the model matrix
     * @return The new {@link Command}
     */
    public static Command setNormalMatrix3f(
        Program program, String normalMatrixName, View view,
        Supplier<Matrix4f> modelMatrixSupplier)
    {
        return setMatrix3f(program, normalMatrixName, () ->
        {
            Matrix4f modelMatrix = modelMatrixSupplier.get();
            Camera camera = view.getCamera();
            Matrix4f viewMatrix = 
                CameraUtils.computeViewMatrix(camera);
            Matrix4f modelviewMatrix = 
                MatrixUtils.mul(viewMatrix, modelMatrix);
            Matrix4f normalMatrix =
                MatrixUtils.transposed(
                    MatrixUtils.inverse(modelviewMatrix));
            Matrix3f normalMatrix3f = new Matrix3f();
            normalMatrix.getRotationScale(normalMatrix3f);
            return normalMatrix3f;
        });
    }

    /**
     * Creates a {@link Command} that sets the normal matrix for
     * the given {@link Program}, as a 3x3 matrix.
     * The view matrix is computed from the {@link Camera} of the given 
     * {@link View} using {@link CameraUtils#computeViewMatrix(Camera)}.
     * The model matrix is obtained from the given supplier.
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setModelViewMatrix(Program, String, View, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     *   
     * @param program The program
     * @param normalMatrixName The uniform name for the matrix
     * @param view The {@link View}
     * @param modelMatrix The the model matrix
     * @return The new {@link Command}
     */
    public static Command setNormalMatrix3f(
        Program program, String normalMatrixName, View view,
        Matrix4f modelMatrix)
    {
        return setNormalMatrix3f(program, normalMatrixName, view,
            Suppliers.constantSupplier(new Matrix4f(modelMatrix)));
    }
    
    
    /**
     * Creates a {@link Command} that sets the default matrices for
     * the given {@link Program}. The default matrices are 
     * <ul>
     *   <li>
     *     the {@link Parameters#MODEL_MATRIX model matrix} which is obtained
     *     from the given supplier
     *   </li>
     *   <li>
     *     the {@link Parameters#VIEW_MATRIX view matrix} which is computed
     *     from the {@link Camera} of the {@link Renderer} using
     *     {@link CameraUtils#computeViewMatrix(Camera)}
     *   </li>
     *   <li>
     *     the {@link Parameters#PROJECTION_MATRIX projection matrix} which 
     *     is computed obtained from {@link View#getProjectionMatrix()}
     *   </li>
     *   <li>
     *     the {@link Parameters#NORMAL_MATRIX normal matrix} which is the
     *     transposed inverse of the the model-view-matrix. That is, for
     *     a model matrix M and a view matrix V, the normal matrix is
     *     (V*M)^-1. 
     *   </li>
     * </ul>  
     *   
     * @param program The program
     * @param modelMatrixSupplier The supplier of the model matrix
     * @param view The view
     * @return The new {@link Command}
     */
    public static Command setDefaultMatrices(
        Program program, 
        Supplier<Matrix4f> modelMatrixSupplier, 
        View view)
    {
        return setDefaultMatrices(program, modelMatrixSupplier, view,
            MODEL_MATRIX.getName(),
            VIEW_MATRIX.getName(),
            null,
            PROJECTION_MATRIX.getName(),
            NORMAL_MATRIX.getName());
    }
    
    
    /**
     * Set the default input matrices for the given program as in
     * {@link #setDefaultMatrices(Program, Supplier, View)}, using 
     * a constant model matrix.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setDefaultMatrices(Program, Supplier, View, String, String, 
     * String, String, String)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The program
     * @param modelMatrix The model matrix
     * @param view The view
     * @return The new {@link Command}
     */
    public static Command setDefaultMatrices(
        Program program, Matrix4f modelMatrix, View view)
    {
        return setDefaultMatrices(program, 
            Suppliers.constantSupplier(new Matrix4f(modelMatrix)), view);
    }

    
    /**
     * Creates a {@link Command} that sets the default matrices for
     * the given {@link Program}. The default matrices are 
     * <ul>
     *   <li>
     *     the <b>model matrix</b> which is obtained
     *     from the given supplier
     *   </li>
     *   <li>
     *     the <b>view matrix</b> which is computed
     *     from the {@link Camera} of the {@link Renderer} using
     *     {@link CameraUtils#computeViewMatrix(Camera)}
     *   </li>
     *   <li>
     *     the <b>projection matrix</b> which 
     *     is obtained from {@link View#getProjectionMatrix()}
     *   </li>
     *   <li>
     *     the <b>normal matrix</b> which is the
     *     transposed inverse of the the model-view-matrix. That is, for
     *     a model matrix M and a view matrix V, the normal matrix is
     *     (V*M)^-1. 
     *   </li>
     * </ul>  
     * If any of the given uniform names is <code>null</code>, then the
     * corresponding matrix will not be set.
     * <br>
     * Note: This assumes that the normal matrix will be passed to
     * the program as a 4x4 matrix. In order to set a 3x3 normal
     * matrix, use {@link #setNormalMatrix3f(Program, String, View, Supplier)}.
     *   
     * @param program The program
     * @param modelMatrixSupplier The supplier of the model matrix
     * @param view The view
     * @param modelMatrixName The uniform name for the model matrix
     * @param viewMatrixName The uniform name for the view matrix
     * @param modelViewMatrixName The uniform name for the model-view matrix
     * @param projectionMatrixName The uniform name for the projection matrix
     * @param normalMatrixName The uniform name for the normal matrix
     * @return The new {@link Command}
     */
    public static Command setDefaultMatrices(
        Program program, 
        Supplier<Matrix4f> modelMatrixSupplier, 
        View view, 
        String modelMatrixName, 
        String viewMatrixName, 
        String modelViewMatrixName,
        String projectionMatrixName, 
        String normalMatrixName)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                Matrix4f modelMatrix = modelMatrixSupplier.get();

                Camera camera = view.getCamera();
                Matrix4f viewMatrix = 
                    CameraUtils.computeViewMatrix(camera);
                
                Matrix4f projectionMatrix = view.getProjectionMatrix();

                Matrix4f modelviewMatrix = 
                    MatrixUtils.mul(viewMatrix, modelMatrix);
                Matrix4f normalMatrix =
                    MatrixUtils.transposed(
                        MatrixUtils.inverse(modelviewMatrix));
                if (modelMatrixName != null)
                {
                    renderer.getProgramHandler().setMatrix4f(
                        program, modelMatrixName, modelMatrix);
                }
                if (viewMatrixName != null)
                {
                    renderer.getProgramHandler().setMatrix4f(
                        program, viewMatrixName, viewMatrix);
                }
                if (modelViewMatrixName != null)
                {
                    renderer.getProgramHandler().setMatrix4f(
                        program, modelViewMatrixName, modelviewMatrix);
                }
                if (projectionMatrixName != null)
                {
                    renderer.getProgramHandler().setMatrix4f(
                        program, projectionMatrixName, projectionMatrix);
                }
                if (normalMatrixName != null)
                {
                    renderer.getProgramHandler().setMatrix4f(
                        program, normalMatrixName, normalMatrix);
                }
            }
            
            @Override
            public String toString()
            {
                return "setDefaultMatrices("+
                    "program="+program+", "+
                    "...)";
            }
        });        
    }
    
    
    /**
     * Set the default input matrices for the given program as in
     * {@link #setDefaultMatrices(Program, Supplier, View, String, String, 
     * String, String, String)}, using a constant model matrix.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setDefaultMatrices(Program, Supplier, View, String, String, 
     * String, String, String)} with a 
     * <code>Supplier</code> that supplies the desired value.<br>
     * <br>
     * If any of the given uniform names is <code>null</code>, then the
     * corresponding matrix will not be set.
     * 
     * @param program The program
     * @param modelMatrix The model matrix
     * @param view The view
     * @param modelMatrixName The uniform name for the model matrix
     * @param viewMatrixName The uniform name for the view matrix
     * @param modelViewMatrixName The uniform name for the model-view matrix
     * @param projectionMatrixName The uniform name for the projection matrix
     * @param normalMatrixName The uniform name for the normal matrix
     * @return The new {@link Command}
     */
    public static Command setDefaultMatrices(
        Program program, 
        Matrix4f modelMatrix, 
        View view, 
        String modelMatrixName, 
        String viewMatrixName, 
        String modelViewMatrixName, 
        String projectionMatrixName, 
        String normalMatrixName)
    {
        return setDefaultMatrices(program, 
            Suppliers.constantSupplier(new Matrix4f(modelMatrix)), view,
            modelMatrixName, viewMatrixName, modelViewMatrixName,
            projectionMatrixName, normalMatrixName);
    }
    
    
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program}.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setMatrix4f(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program}
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType MATRIX4F}  
     */
    public static Command setMatrix4f(
        Program program, Parameter parameter, Matrix4f value)
    {
        checkParameterType(parameter, ParameterType.MATRIX4F);
        return setMatrix4f(program, parameter.getName(), 
            Suppliers.constantSupplier(new Matrix4f(value)));
    }

    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program} to the value that is 
     * obtained from the given supplier
     * 
     * @param program The {@link Program}
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType MATRIX4F}  
     */
    public static Command setMatrix4f(
        Program program, Parameter parameter, Supplier<Matrix4f> supplier)
    {
        checkParameterType(parameter, ParameterType.MATRIX4F);
        return setMatrix4f(program, parameter.getName(), supplier);
    }
    
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program}.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setMatrix4f(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program}
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setMatrix4f(
        Program program, String uniformName, Matrix4f value)
    {
        return setMatrix4f(program, uniformName, 
            Suppliers.constantSupplier(new Matrix4f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program}
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setMatrix4f(
        Program program, String uniformName, 
        Supplier<Matrix4f> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setMatrix4f(
                    program, uniformName, supplier.get());
            }
            
            @Override
            public String toString()
            {
                return "setMatrix4f(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }

    
    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program}.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setMatrix3f(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program}
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType MATRIX3F}  
     */
    public static Command setMatrix3f(
        Program program, Parameter parameter, Matrix3f value)
    {
        checkParameterType(parameter, ParameterType.MATRIX3F);
        return setMatrix3f(program, parameter.getName(), 
            Suppliers.constantSupplier(new Matrix3f(value)));
    }

    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program} to the value that is 
     * obtained from the given supplier
     * 
     * @param program The {@link Program}
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType MATRIX3F}  
     */
    public static Command setMatrix3f(
        Program program, Parameter parameter, Supplier<Matrix3f> supplier)
    {
        checkParameterType(parameter, ParameterType.MATRIX3F);
        return setMatrix3f(program, parameter.getName(), supplier);
    }
    
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform 
     * variable for the given {@link Program}.<br>
     * <br>
     * A copy of the given matrix will be created, so changes in the 
     * matrix will not affect the {@link Command}. In order to create
     * a command where the matrix may be modified externally, use
     * {@link #setMatrix3f(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program}
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setMatrix3f(
        Program program, String uniformName, Matrix3f value)
    {
        return setMatrix3f(program, uniformName, 
            Suppliers.constantSupplier(new Matrix3f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program}
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setMatrix3f(
        Program program, String uniformName, 
        Supplier<Matrix3f> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setMatrix3f(
                    program, uniformName, supplier.get());
            }
            
            @Override
            public String toString()
            {
                return "setMatrix3f(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }

    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple2f(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE2F}  
     */
    public static Command setTuple2f(
        Program program, Parameter parameter, Tuple2f value)
    {
        checkParameterType(parameter, ParameterType.TUPLE2F);
        return setTuple2f(program, parameter.getName(), 
            Suppliers.<Tuple2f>constantSupplier(new Point2f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE2F}  
     */
    public static Command setTuple2f(
        Program program, Parameter parameter, Supplier<Tuple2f> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE2F);
        return setTuple2f(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4f(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple2f(
        Program program, String uniformName, Tuple2f value)
    {
        return setTuple2f(program, uniformName, 
            Suppliers.<Tuple2f>constantSupplier(new Point2f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple2f(
        Program program, String uniformName,
        Supplier<Tuple2f> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple2f(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple2f(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple3f(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE3F}  
     */
    public static Command setTuple3f(
        Program program, Parameter parameter, Tuple3f value)
    {
        checkParameterType(parameter, ParameterType.TUPLE3F);
        return setTuple3f(program, parameter.getName(), 
            Suppliers.<Tuple3f>constantSupplier(new Point3f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE3F}  
     */
    public static Command setTuple3f(
        Program program, Parameter parameter, Supplier<Tuple3f> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE3F);
        return setTuple3f(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4f(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple3f(
        Program program, String uniformName, Tuple3f value)
    {
        return setTuple3f(program, uniformName, 
            Suppliers.<Tuple3f>constantSupplier(new Point3f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple3f(
        Program program, String uniformName,
        Supplier<Tuple3f> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple3f(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple3f(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }

    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4f(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE4F}  
     */
    public static Command setTuple4f(
        Program program, Parameter parameter, Tuple4f value)
    {
        checkParameterType(parameter, ParameterType.TUPLE4F);
        return setTuple4f(program, parameter.getName(), 
            Suppliers.<Tuple4f>constantSupplier(new Point4f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType TUPLE4F}  
     */
    public static Command setTuple4f(
        Program program, Parameter parameter, Supplier<Tuple4f> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE4F);
        return setTuple4f(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4f(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple4f(
        Program program, String uniformName, Tuple4f value)
    {
        return setTuple4f(program, uniformName, 
            Suppliers.<Tuple4f>constantSupplier(new Point4f(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple4f(
        Program program, String uniformName,
        Supplier<Tuple4f> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple4f(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple4f(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }

    
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple2i(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple2i}  
     */
    public static Command setTuple2i(
        Program program, Parameter parameter, Tuple2i value)
    {
        checkParameterType(parameter, ParameterType.TUPLE2I);
        return setTuple2i(program, parameter.getName(), 
            Suppliers.<Tuple2i>constantSupplier(new Point2i(value)));
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple2i}  
     */
    public static Command setTuple2i(
        Program program, Parameter parameter, Supplier<Tuple2i> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE2I);
        return setTuple2i(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4i(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple2i(
        Program program, String uniformName, Tuple2i value)
    {
        return setTuple2i(program, uniformName, 
            Suppliers.<Tuple2i>constantSupplier(new Point2i(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple2i(
        Program program, String uniformName,
        Supplier<Tuple2i> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple2i(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple2i(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple3i(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple3i}  
     */
    public static Command setTuple3i(
        Program program, Parameter parameter, Tuple3i value)
    {
        checkParameterType(parameter, ParameterType.TUPLE3I);
        return setTuple3i(program, parameter.getName(), 
            Suppliers.<Tuple3i>constantSupplier(new Point3i(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple3i}  
     */
    public static Command setTuple3i(
        Program program, Parameter parameter, Supplier<Tuple3i> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE3I);
        return setTuple3i(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4i(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple3i(
        Program program, String uniformName, Tuple3i value)
    {
        return setTuple3i(program, uniformName, 
            Suppliers.<Tuple3i>constantSupplier(new Point3i(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple3i(
        Program program, String uniformName,
        Supplier<Tuple3i> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple3i(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple3i(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }

    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4i(Program, Parameter, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple4i}  
     */
    public static Command setTuple4i(
        Program program, Parameter parameter, Tuple4i value)
    {
        checkParameterType(parameter, ParameterType.TUPLE4I);
        return setTuple4i(program, parameter.getName(), 
            Suppliers.<Tuple4i>constantSupplier(new Point4i(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified {@link Parameter}
     * for the given {@link Program}  to the value that is obtained from 
     * the given supplier
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier of the value
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType Tuple4i}  
     */
    public static Command setTuple4i(
        Program program, Parameter parameter, Supplier<Tuple4i> supplier)
    {
        checkParameterType(parameter, ParameterType.TUPLE4I);
        return setTuple4i(program, parameter.getName(), supplier);
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program}.<br>
     * <br>
     * A copy of the given tuple will be created, so changes in the 
     * tuple will not affect the {@link Command}. In order to create
     * a command where the tuple may be modified externally, use
     * {@link #setTuple4i(Program, String, Supplier)} with a 
     * <code>Supplier</code> that supplies the desired value.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setTuple4i(
        Program program, String uniformName, Tuple4i value)
    {
        return setTuple4i(program, uniformName, 
            Suppliers.<Tuple4i>constantSupplier(new Point4i(value)));
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is obtained from the
     * given supplier
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setTuple4i(
        Program program, String uniformName,
        Supplier<Tuple4i> supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setTuple4i(
                    program, uniformName, supplier.get());
            }

            @Override
            public String toString()
            {
                return "setTuple4i(" + 
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.get()+")";
            }
            
        });
    }
    
    

    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the given value
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType INT}  
     */
    public static Command setInt(
        Program program, Parameter parameter, int value)
    {
        checkParameterType(parameter, ParameterType.INT);
        return setInt(program, parameter.getName(), value);
    }

    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the given value
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setInt(
        Program program, String uniformName, int value)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setInt(
                    program, uniformName, value);
            }

            @Override
            public String toString()
            {
                return "setInt("+
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "value="+value+")";
            }
        });
    }

    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is provided by the
     * given supplier.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType INT}  
     */
    public static Command setInt(
        Program program, Parameter parameter, IntSupplier supplier)
    {
        checkParameterType(parameter, ParameterType.INT);
        return setInt(program, parameter.getName(), supplier);
    }

    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is provided by the
     * given supplier.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setInt(
        Program program, String uniformName, IntSupplier supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setInt(
                    program, uniformName, supplier.getAsInt());
            }

            @Override
            public String toString()
            {
                return "setInt("+
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.getAsInt()+")";
            }
        });
    }
    
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the given value
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType FLOAT}  
     */
    public static Command setFloat(
        Program program, Parameter parameter, float value)
    {
        checkParameterType(parameter, ParameterType.FLOAT);
        return setFloat(program, parameter.getName(), value);
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the given value
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param value The value to set
     * @return The new {@link Command}
     */
    public static Command setFloat(
        Program program, String uniformName, float value)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setFloat(
                    program, uniformName, value);
            }

            @Override
            public String toString()
            {
                return "setFloat("+
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "value="+value+")";
            }
        });
    }
    

    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is provided by the
     * given supplier.
     * 
     * @param program The {@link Program} 
     * @param parameter The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     * @throws IllegalArgumentException If the given {@link Parameter} does
     * not have the type {@link ParameterType FLOAT}  
     */
    public static Command setFloat(
        Program program, Parameter parameter, DoubleSupplier supplier)
    {
        checkParameterType(parameter, ParameterType.FLOAT);
        return setFloat(program, parameter.getName(), supplier);
    }
    
    /**
     * Creates a new {@link Command} that sets the specified uniform variable
     * for the given {@link Program} to the value that is provided by the
     * given supplier.
     * 
     * @param program The {@link Program} 
     * @param uniformName The name of the uniform
     * @param supplier The supplier for the value to set
     * @return The new {@link Command}
     */
    public static Command setFloat(
        Program program, String uniformName, DoubleSupplier supplier)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                renderer.getProgramHandler().setFloat(
                    program, uniformName, (float)supplier.getAsDouble());
            }

            @Override
            public String toString()
            {
                return "setFloat("+
                    "program="+program+", "+
                    "uniformName="+uniformName+", "+
                    "supplier.get()="+supplier.getAsDouble()+")";
            }
        });
    }
    
    
    /**
     * Creates a new {@link Command} that sets the 
     * {@link Parameters#NUM_TEXTURES} 
     * for the given {@link Program}.
     * 
     * @param program The {@link Program}
     * @param numTextures The number of textures
     * @return The new {@link Command}
     */
    public static Command setNumTextures(
        Program program, int numTextures)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                ProgramHandler<?> programHandler = renderer.getProgramHandler();
                programHandler.setInt(program, 
                    Parameters.NUM_TEXTURES.getName(), numTextures);
            }
            
            @Override
            public String toString()
            {
                return "setNumTextures(" + 
                    "program="+program+", " + 
                    "numTextures="+numTextures+")";
            }
        });
    }
    
    
    /**
     * Creates a new {@link Command} that applies the given {@link Material}
     * for the given {@link Program}, using the default 
     * {@link Parameters#MATERIAL material parameters}
     * 
     * @param program The {@link Program}
     * @param material The {@link Material}
     * @return The new {@link Command}
     */
    public static Command setMaterial(
        Program program, Material material)
    {
        return new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                ProgramHandler<?> programHandler = renderer.getProgramHandler();
              
                programHandler.setTuple4f(program, 
                    Parameters.MATERIAL.AMBIENT.getName(), 
                    material.getAmbientColor());
                programHandler.setTuple4f(program, 
                    Parameters.MATERIAL.DIFFUSE.getName(), 
                    material.getDiffuseColor());
                programHandler.setTuple4f(program, 
                    Parameters.MATERIAL.SPECULAR.getName(), 
                    material.getSpecularColor());
                programHandler.setTuple4f(program, 
                    Parameters.MATERIAL.EMISSION.getName(), 
                    material.getEmissionColor());
                programHandler.setFloat(program, 
                    Parameters.MATERIAL.SHININESS.getName(), 
                    material.getShininess());
            }
            
            @Override
            public String toString()
            {
                return "setMaterial(" + 
                    "program="+program+", "+
                    "material="+material+")";
            }
        };
    }
    
    /**
     * Creates a new {@link Command} that applies the given {@link LightSetup}
     * for the given {@link Program}, using the default
     * {@link Parameters#LIGHTS light parameters}.<br>
     * <br>
     * Note that there might be no visible effect of the light setup 
     * unless {@link #setMaterial(Program, Material)} is called as well.
     * 
     * @param program The {@link Program}
     * @param lightSetup The {@link LightSetup}
     * @param view The {@link View}
     * @return The new {@link Command}
     */
    public static Command setLightSetup(
        Program program, LightSetup lightSetup, View view)
    {
        return wrap(new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                ProgramHandler<?> programHandler = renderer.getProgramHandler();
                
                Camera camera = view.getCamera();
                Matrix4f viewMatrix = 
                    CameraUtils.computeViewMatrix(camera);
                
                programHandler.setInt(program, Parameters.NUM_LIGHTS.getName(), 
                    lightSetup.getLights().size());
                for (int i=0; i<lightSetup.getLights().size(); i++)
                {
                    Light light = lightSetup.getLights().get(i);
                    LightParameters lightParameters = Parameters.LIGHTS.get(i);
                    
                    setLight(program, programHandler, viewMatrix, 
                        light, lightParameters);
                }
            }
            
            @Override
            public String toString()
            {
                return "setLightSetup("+
                    "program="+program+", "+
                    "lightSetup="+lightSetup+", "+
                    "view="+view+")";
            }

        });
    }
    
    /**
     * Set all parameters of the given light in the given {@link Program}
     * 
     * @param program The {@link Program}
     * @param programHandler The {@link ProgramHandler}
     * @param viewMatrix The view matrix
     * @param light The {@link Light}
     * @param lightParameters The Light {@link Parameter}s
     */
    private static void setLight(Program program,
        ProgramHandler<?> programHandler, Matrix4f viewMatrix,
        Light light, LightParameters lightParameters)
    {
        int DIRECTIONAL_LIGHT_TYPE_ID = 0;
        int POINT_LIGHT_TYPE_ID = 1;
        int SPOT_LIGHT_TYPE_ID = 2;

        //System.out.println("Apply light "+light);
        
        if (light.getType() == LightType.DIRECTIONAL)
        {        
            programHandler.setInt(program, 
                lightParameters.TYPE.getName(), 
                DIRECTIONAL_LIGHT_TYPE_ID);
            Vector3f v = light.getDirection();
            viewMatrix.transform(v);
            programHandler.setTuple4f(program, 
                lightParameters.POSITION.getName(), 
                new Point4f(v.x, v.y, v.z, 0));
        }
        else if (light.getType() == LightType.POINT)
        {
            programHandler.setInt(program, 
                lightParameters.TYPE.getName(), 
                POINT_LIGHT_TYPE_ID);
            Point3f v = light.getPosition();
            viewMatrix.transform(v);
            programHandler.setTuple4f(program, 
                lightParameters.POSITION.getName(), 
                new Point4f(v.x, v.y, v.z, 1));
        }
        else if (light.getType() == LightType.SPOT)
        {
            programHandler.setInt(program, 
                lightParameters.TYPE.getName(), 
                SPOT_LIGHT_TYPE_ID);
            Point3f pp = light.getPosition();
            viewMatrix.transform(pp);
            programHandler.setTuple4f(program, 
                lightParameters.POSITION.getName(), 
                new Point4f(pp.x, pp.y, pp.z, 1));

            Vector3f v = light.getDirection();
            viewMatrix.transform(v);
            programHandler.setTuple3f(program, 
                lightParameters.SPOT_DIRECTION.getName(), v);
            programHandler.setFloat(program, 
                lightParameters.SPOT_CUTOFF.getName(), 
                light.getSpotCutoffDeg());
            programHandler.setFloat(program, 
                lightParameters.SPOT_EXPONENT.getName(), 
                light.getSpotExponent());
        }

        programHandler.setTuple4f(program, 
            lightParameters.AMBIENT.getName(), 
            light.getAmbientColor());
        programHandler.setTuple4f(program, 
            lightParameters.DIFFUSE.getName(), 
            light.getDiffuseColor());
        programHandler.setTuple4f(program, 
            lightParameters.SPECULAR.getName(), 
            light.getSpecularColor());

        programHandler.setFloat(program, 
            lightParameters.CONSTANT_ATTENUATION.getName(), 
            light.getConstantAttenuation());
        programHandler.setFloat(program, 
            lightParameters.LINEAR_ATTENUATION.getName(), 
            light.getLinearAttenuation());
        programHandler.setFloat(program, 
            lightParameters.QUADRATIC_ATTENUATION.getName(), 
            light.getQuadraticAttenuation());
    }
    
    
    /**
     * Wraps the given {@link Command} into a {@link Command} that prints
     * tracing information whenever the given command is executed.
     * 
     * @param command The delegate {@link Command}
     * @return The tracing {@link Command}
     */
    public static Command tracing(Command command)
    {
        return new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                if (logger.isLoggable(traceLogLevel))
                {
                    logger.log(traceLogLevel, "Executing "+command);
                }
                command.execute(renderer);
            }
        };
        
    }

    /**
     * Utility method that checks whether the given {@link Parameter} has
     * the given {@link ParameterType}, and throws an 
     * <code>IllegalArgumentException</code> if this is not the case
     * 
     * @param parameter The {@link Parameter}
     * @param parameterType The {@link ParameterType}
     */
    private static void checkParameterType(
        Parameter parameter, ParameterType parameterType)
    {
        if (parameter.getType() != parameterType)
        {
            throw new IllegalArgumentException(
                "Type of paramter must be "+parameterType+
                " but is "+parameter.getType());
        }
    }
    
    
    /**
     * When {@link #PRESERVE_STACK_TRACES} is <code>true</code>, this method 
     * wraps the given {@link Command} into one that preserves the stack trace 
     * of this method call, and, if the command causes an exception, prints 
     * it together with the stack trace of the exception caused by the 
     * {@link Command}. <br> 
     * <br>
     * When {@link #PRESERVE_STACK_TRACES} is <code>false</code>, the given
     * command is returned.
     * 
     * @param command The {@link Command} to wrap.
     * @return The wrapped {@link Command} or the original {@link Command}
     */
    private static Command wrap(Command command)
    {
        if (!PRESERVE_STACK_TRACES)
        {
            return command;
        }
        
        StackTraceElement ste[] = Thread.currentThread().getStackTrace();
        return new Command()
        {
            @Override
            public void execute(Renderer renderer)
            {
                try
                {
                    //System.out.println("Execute "+command);
                    command.execute(renderer);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.err.println("Scheduled at");
                    for (StackTraceElement s : ste)
                    {
                        System.err.println("\tat "+s);
                    }
                }
            }
            
            @Override
            public boolean equals(Object object)
            {
                if (object == null)
                {
                    return false;
                }
                if (object == this)
                {
                    return true;
                }
                return command.equals(object);
            }

            @Override
            public int hashCode()
            {
                return command.hashCode();
            }
            
            @Override
            public String toString()
            {
                return command.toString();
            }
        };
    }
    

    /**
     * Private constructor to prevent instantiation
     */
    private Commands()
    {
        // Private constructor to prevent instantiation
    }


}
