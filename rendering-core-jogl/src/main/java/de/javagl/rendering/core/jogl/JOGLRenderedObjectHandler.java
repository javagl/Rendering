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
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;

import java.util.Collection;

import com.jogamp.opengl.GL3;

import de.javagl.rendering.core.Attribute;
import de.javagl.rendering.core.DataBufferType;
import de.javagl.rendering.core.GraphicsObject;
import de.javagl.rendering.core.Mapping;
import de.javagl.rendering.core.Parameter;
import de.javagl.rendering.core.Program;
import de.javagl.rendering.core.RenderedObject;
import de.javagl.rendering.core.Texture;
import de.javagl.rendering.core.gl.DefaultGL;
import de.javagl.rendering.core.gl.GLAttribute;
import de.javagl.rendering.core.gl.GLDataBuffer;
import de.javagl.rendering.core.gl.GLGraphicsObject;
import de.javagl.rendering.core.gl.GLProgram;
import de.javagl.rendering.core.gl.GLRenderedObject;
import de.javagl.rendering.core.gl.GLTexture;
import de.javagl.rendering.core.gl.util.ErrorHandler;
import de.javagl.rendering.core.handling.AbstractRenderedObjectHandler;
import de.javagl.rendering.core.handling.GraphicsObjectHandler;
import de.javagl.rendering.core.handling.ProgramHandler;
import de.javagl.rendering.core.handling.RenderedObjectHandler;
import de.javagl.rendering.core.handling.TextureHandler;


/**
 * Implementation of a {@link RenderedObjectHandler} using JOGL.
 */
class JOGLRenderedObjectHandler 
    extends AbstractRenderedObjectHandler<GLRenderedObject>
    implements RenderedObjectHandler<GLRenderedObject>
{
    /**
     * The current GL instance
     */
    private GL3 gl;
    
    /**
     * The {@link ProgramHandler}
     */
    private final JOGLProgramHandler programHandler;

    /**
     * The {@link TextureHandler}
     */
    private final JOGLTextureHandler textureHandler;
    
    /**
     * The {@link GraphicsObjectHandler}
     */
    private final JOGLGraphicsObjectHandler graphicsObjectHandler;

    /**
     * Creates a new JOGLREnderedObjectHandler
     */
    JOGLRenderedObjectHandler()
    {
        this.programHandler = new JOGLProgramHandler();
        this.textureHandler = new JOGLTextureHandler();
        this.graphicsObjectHandler = new JOGLGraphicsObjectHandler();
    }

    /**
     * Set the current GL instance. If the given GL instance is not
     * the same as the one that is currently stored in this class,
     * then the objects will be re-initialized by calling
     * {@link #reInitialize()}.
     * 
     * @param gl The current GL instance
     */
    void setGL(GL3 gl)
    {
        if (this.gl == null || !this.gl.equals(gl))
        {
            Collection<RenderedObject> released = releaseAll();
            this.gl = gl;
            programHandler.setGL(gl);
            textureHandler.setGL(gl);
            graphicsObjectHandler.setGL(gl);
            handleAll(released);
        }
    }
    
    
    
    @Override
    public GLRenderedObject handleInternal(RenderedObject renderedObject)
    {
        Program program = renderedObject.getProgram();
        GLProgram glProgram = programHandler.getInternal(program);
        
        GraphicsObject graphicsObject = renderedObject.getGraphicsObject();
        GLGraphicsObject glGraphicsObject = 
            graphicsObjectHandler.getInternal(graphicsObject);

        int vaoArray[] = {0};
        gl.glGenVertexArrays(1, vaoArray, 0);
        int vao = vaoArray[0];
        gl.glBindVertexArray(vao);
        
        Mapping<Parameter, Attribute> attributeMapping = 
            renderedObject.getAttributeMapping();
        for (Parameter programParameter : attributeMapping.keySet())
        {
            Attribute attribute = attributeMapping.get(programParameter);
            if (attribute == null)
            {
                ErrorHandler.handle(
                    "No attribute found for "+programParameter);
                continue;
            }
            if (attribute.getType() == DataBufferType.FLOAT)
            {
                GLAttribute glAttribute = 
                    programHandler.getGLAttribute(
                        program, programParameter.getName());
                if (glAttribute == null)
                {
                    ErrorHandler.handle(
                        "No GLAttribute found in " + program + 
                        " for " + programParameter.getName());
                    continue;
                }
                //System.out.println("Attribute "+attribute+" mapped to "+programInputName+" with location "+glAttribute.getLocation());

                GLDataBuffer glDataBuffer = 
                    glGraphicsObject.getGLDataBuffer(attribute);
                if (glDataBuffer == null)
                {
                    ErrorHandler.handle(
                        "GLDataBuffer not found for "+attribute);
                    continue;
                }

                gl.glBindBuffer(GL_ARRAY_BUFFER, glDataBuffer.getVBO());

                gl.glVertexAttribPointer(
                    glAttribute.getLocation(), attribute.getSize(), 
                    GL_FLOAT, false, glDataBuffer.getStride(), 
                    glDataBuffer.getOffset());
                gl.glEnableVertexAttribArray(glAttribute.getLocation());

                gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            else
            {
                ErrorHandler.handle(
                    "Only float attributes supported, found "+
                    attribute.getType());
                continue;
            }
        }
        
        gl.glBindVertexArray(0);

        GLRenderedObject glRenderedObject = 
            DefaultGL.createGLRenderedObject(glProgram, vao, glGraphicsObject);
        
        return glRenderedObject;
    }
    
    
    @Override
    public void releaseInternal(
        RenderedObject renderedObject, GLRenderedObject glRenderedObject)
    {
        int buffer[] = {glRenderedObject.getVertexArrayObject()};
        gl.glDeleteVertexArrays(1, buffer, 0);
    }
    
    
    
    @Override
    public JOGLGraphicsObjectHandler getGraphicsObjectHandler()
    {
        return graphicsObjectHandler;
    }


    @Override
    public JOGLProgramHandler getProgramHandler()
    {
        return programHandler;
    }


    @Override
    public JOGLTextureHandler getTextureHandler()
    {
        return textureHandler;
    }

    @Override
    public void render(RenderedObject renderedObject)
    {
        Program program = renderedObject.getProgram();
        GLRenderedObject glRenderedObject = getInternal(renderedObject);
        if (glRenderedObject == null)
        {
            ErrorHandler.handle(
                "GLRenderedObject not found for "+renderedObject);
            return;
        }
        GLProgram glProgram = glRenderedObject.getGLProgram();
        GLGraphicsObject glGraphicsObject = 
            glRenderedObject.getGLGraphicsObject();

        // The current GL_TEXTUREn-index
        int currentTextureIndex = 0;
        Mapping<Parameter, Texture> textureMapping = 
            renderedObject.getTextureMapping();
        for (Parameter programParameter : textureMapping.keySet())
        {
            Texture texture = textureMapping.get(programParameter);
            GLTexture glTexture = textureHandler.getInternal(texture);
            if (glTexture != null)
            {
                enableTexture(program, programParameter.getName(), 
                    glTexture, currentTextureIndex);
                currentTextureIndex++;
            }
            else
            {
                ErrorHandler.handle("GLTexture not found for "+texture);
            }
        }
        
        GLDataBuffer indices = glGraphicsObject.getIndicesGLDataBuffer(); 
        gl.glUseProgram(glProgram.getProgram());
        gl.glBindVertexArray(glRenderedObject.getVertexArrayObject());
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices.getVBO());
        gl.glDrawElements(
            GL_TRIANGLES, indices.getSize(), indices.getType(), 0);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        gl.glBindVertexArray(0);
        gl.glBindTexture(GL_TEXTURE_2D, 0);
        gl.glUseProgram(0);
        currentTextureIndex = 0;
    }

    /**
     * Enables the given texture as the input with the given name
     * for the given program
     * 
     * @param program The program
     * @param programInputName The input name
     * @param glTexture The texture
     * @param currentTextureIndex The current index to be added to GL_TEXTURE0
     */
    private void enableTexture(
        Program program, String programInputName, 
        GLTexture glTexture, int currentTextureIndex)
    {
        gl.glActiveTexture(GL3.GL_TEXTURE0+currentTextureIndex);
        gl.glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());
        ProgramHandler<GLProgram> programHandler = getProgramHandler();
        programHandler.setInt(program, programInputName, currentTextureIndex);
    }


    
    
}