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

import java.util.Map;

import de.javagl.rendering.core.Attribute;

/**
 * Factory methods for implementations of the GL interfaces.<br>
 * <br>
 * This class should not be used by clients.
 */
public class DefaultGL
{

    /**
     * Creates a new {@link GLAttribute} with the given location
     * 
     * @param location The location of the attribute
     * @return The {@link GLAttribute}
     */
    public static GLAttribute createGLAttribute(int location)
    {
        return new DefaultGLAttribute(location);
    }

    /**
     * Creates a new {@link GLDataBuffer}
     * 
     * @param vbo The VBO ID
     * @param type The type
     * @param size The size, in number of elements
     * @param offset The offset, in bytes
     * @param stride The stride between two elements, in bytes
     * @return The {@link GLDataBuffer}
     */
    public static GLDataBuffer createGLDataBuffer(
        int vbo, int type, int size, int offset, int stride)
    {
        return new DefaultGLDataBuffer(vbo, type, size, offset, stride);
    }

    /**
     * Creates a new {@link GLFrameBuffer}
     * 
     * @param fbo The FBO identifier
     * @param depthBuffer The depth buffer identifier
     * @param colorBuffer The color buffer identifier
     * @param texture The GL texture that is backed by this FBO
     * @return The {@link GLFrameBuffer}
     */
    public static GLFrameBuffer createGLFrameBuffer(
        int fbo, int depthBuffer, int colorBuffer, int texture)
    {
        return new DefaultGLFrameBuffer(fbo, depthBuffer, colorBuffer, texture);
    }

    /**
     * Creates a new GLGraphicsObject with the given indices.
     * 
     * @param indicesDataBuffer The indices data buffer. This may be 
     * <code>null</code> for non-indexed geometry
     * @param numVertices The number of vertices
     * @param dataBuffers The mapping from {@link Attribute}s to
     * {@link GLDataBuffer}s
     * @return The {@link GLGraphicsObject}
     */
    public static GLGraphicsObject createGLGraphicsObject(
        GLDataBuffer indicesDataBuffer, 
        int numVertices,
        Map<Attribute, GLDataBuffer> dataBuffers)
    {
        return new DefaultGLGraphicsObject(
            indicesDataBuffer, numVertices, dataBuffers);
    }

    /**
     * Creates a new {@link GLProgram} with the given GL
     * program ID
     * 
     * @param program The GL program ID
     * @return The {@link GLProgram}
     */
    public static GLProgram createGLProgram(int program)
    {
        return new DefaultGLProgram(program);
    }

    /**
     * Creates a new {@link GLRenderedObject}
     * 
     * @param glProgram The program
     * @param vertexArrayObject The VAO ID
     * @param glGraphicsObject The graphics object
     * @return The {@link GLRenderedObject}
     */
    public static GLRenderedObject createGLRenderedObject(
        GLProgram glProgram, int vertexArrayObject, 
        GLGraphicsObject glGraphicsObject)
    {
        return new DefaultGLRenderedObject(
            glProgram, vertexArrayObject, glGraphicsObject);
    }

    /**
     * Creates a new {@link GLTexture} with the given
     * parameters.
     * 
     * @param texture The texture ID
     * @param texturePBO The texture PBO
     * @param glTextureFormat The texture format
     * @return The {@link GLTexture}
     */
    public static GLTexture createGLTexture(
        int texture, int texturePBO, GLTextureFormat glTextureFormat)
    {
        return new DefaultGLTexture(texture, texturePBO, glTextureFormat);
    }

    /**
     * Creates a new {@link GLTextureFormat} with the given parameters
     * 
     * @param format The format
     * @param internalFormat The internal format
     * @param type The data type
     * @param elements The number of elements of the type
     * @return The {@link GLTextureFormat}
     */
    public static GLTextureFormat createGLTextureFormat(
        int format, int internalFormat, int type, int elements)
    {
        return new DefaultGLTextureFormat(
            format, internalFormat, type, elements);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private DefaultGL()
    {
        // Private constructor to prevent instantiation
    }
}
