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

package de.javagl.rendering.core.gl;


/**
 * Default implementation of a {@link GLRenderedObject}
 */
class DefaultGLRenderedObject implements GLRenderedObject
{
    /**
     * The {@link GLProgram}.
     */
    private final GLProgram glProgram;

    /**
     * The VAO ID
     */
    private final int vertexArrayObject;

    /**
     * The {@link GLGraphicsObject}
     */
    private final GLGraphicsObject glGraphicsObject;

    /**
     * Creates a new DefaultGLRenderedObject
     * 
     * @param glProgram The {@link GLProgram}
     * @param vertexArrayObject The VAO ID
     * @param glGraphicsObject The {@link GLGraphicsObject}
     */
    DefaultGLRenderedObject(
        GLProgram glProgram, int vertexArrayObject, 
        GLGraphicsObject glGraphicsObject)
    {
        this.glProgram = glProgram;
        this.vertexArrayObject = vertexArrayObject;
        this.glGraphicsObject = glGraphicsObject;
    }

    @Override
    public GLProgram getGLProgram()
    {
        return glProgram;
    }
    
    
    @Override
    public int getVertexArrayObject()
    {
        return vertexArrayObject;
    }
    
    @Override
    public GLGraphicsObject getGLGraphicsObject()
    {
        return glGraphicsObject;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((glGraphicsObject == null) ? 0 : glGraphicsObject.hashCode());
        result = prime * result + ((glProgram == null) ? 0 : glProgram.hashCode());
        result = prime * result + vertexArrayObject;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultGLRenderedObject other = (DefaultGLRenderedObject)obj;
        if (glGraphicsObject == null)
        {
            if (other.glGraphicsObject != null)
                return false;
        }
        else if (!glGraphicsObject.equals(other.glGraphicsObject))
            return false;
        if (glProgram == null)
        {
            if (other.glProgram != null)
                return false;
        }
        else if (!glProgram.equals(other.glProgram))
            return false;
        if (vertexArrayObject != other.vertexArrayObject)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "DefaultGLRenderedObject[" +
        	"glProgram=" + glProgram + "," +
        	"vertexArrayObject=" + vertexArrayObject + "," +
        	"glGraphicsObject=" + glGraphicsObject + "]";
    }
    

}