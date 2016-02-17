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
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
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
 * Implementation of a {@link RenderedObjectHandler} using LWJGL
 */
class LWJGLRenderedObjectHandler 
    extends AbstractRenderedObjectHandler<GLRenderedObject>
    implements RenderedObjectHandler<GLRenderedObject>
{
    /**
     * The {@link GraphicsObjectHandler}
     */
    private final LWJGLGraphicsObjectHandler graphicsObjectHandler;

    /**
     * The {@link ProgramHandler}
     */
    private final LWJGLProgramHandler programHandler;

    /**
     * The {@link TextureHandler}
     */
    private final LWJGLTextureHandler textureHandler;

    /**
     * Creates a new LWJGLRenderedObjectHandler
     */
    public LWJGLRenderedObjectHandler()
    {
        this.graphicsObjectHandler = new LWJGLGraphicsObjectHandler();
        this.programHandler = new LWJGLProgramHandler();
        this.textureHandler = new LWJGLTextureHandler();
    }
    
    @Override
    public GLRenderedObject handleInternal(RenderedObject renderedObject)
    {
        Program program = renderedObject.getProgram();
        GLProgram glProgram = programHandler.getInternal(program);
        
        GraphicsObject graphicsObject = renderedObject.getGraphicsObject();
        GLGraphicsObject glGraphicsObject = 
            graphicsObjectHandler.getInternal(graphicsObject);

        // Create the VAO for the RenderedObject
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // For each attribute of the rendered object (that is, for each
        // program input that is mapped to a buffer using the attributeMapping)
        // enable the attribute and bind its location to the data from the VBO
        Mapping<Parameter, Attribute> attributeMapping = 
            renderedObject.getAttributeMapping();
        for (Parameter programParameter : attributeMapping.keySet())
        {
            Attribute attribute = attributeMapping.get(programParameter);
            if (attribute == null)
            {
                ErrorHandler.handle("No attribute found for "+programParameter);
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
                
                System.out.println("Attribute "+attribute+" mapped to "+programParameter+" with location "+glAttribute.getLocation());

                GLDataBuffer glDataBuffer = 
                    glGraphicsObject.getGLDataBuffer(attribute);
                if (glDataBuffer == null)
                {
                    ErrorHandler.handle(
                        "GLDataBuffer not found for "+attribute);
                    continue;
                }

                glBindBuffer(GL_ARRAY_BUFFER, glDataBuffer.getVBO());

                glVertexAttribPointer(
                    glAttribute.getLocation(), attribute.getSize(), 
                    GL_FLOAT, false, glDataBuffer.getStride(), 
                    glDataBuffer.getOffset());
                glEnableVertexAttribArray(glAttribute.getLocation());

                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            else
            {
                ErrorHandler.handle(
                    "Only float attributes supported, " +
                	"found "+attribute.getType());
                continue;
            }
        }
        glBindVertexArray(0);
        
        GLRenderedObject glRenderedObject = 
            DefaultGL.createGLRenderedObject(glProgram, vao, glGraphicsObject);
        
        return glRenderedObject;
    }
    
    
    @Override
    public void releaseInternal(
        RenderedObject renderedObject, GLRenderedObject glRenderedObject)
    {
        glDeleteVertexArrays(glRenderedObject.getVertexArrayObject());
    }

    
    @Override
    public LWJGLGraphicsObjectHandler getGraphicsObjectHandler()
    {
        return graphicsObjectHandler;
    }


    @Override
    public LWJGLProgramHandler getProgramHandler()
    {
        return programHandler;
    }


    @Override
    public LWJGLTextureHandler getTextureHandler()
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

        Mapping<Parameter, Texture> textureMapping = 
            renderedObject.getTextureMapping();

        // The current GL_TEXTUREn-index
        int currentTextureIndex = 0;
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
        glUseProgram(glProgram.getProgram());
        glBindVertexArray(glRenderedObject.getVertexArrayObject());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices.getVBO());
        glDrawElements(
            GL_TRIANGLES, indices.getSize(), indices.getType(), 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glUseProgram(0);
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
        glActiveTexture(GL_TEXTURE0+currentTextureIndex);
        glBindTexture(GL_TEXTURE_2D, glTexture.getTexture());
        ProgramHandler<GLProgram> programHandler = getProgramHandler();
        programHandler.setInt(program, programInputName, currentTextureIndex);
    }

    
    
    
}