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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Objects;

import de.javagl.rendering.core.utils.BufferUtils;

/**
 * Methods and classes for creating {@link GraphicsObject} instances
 */
public class GraphicsObjects
{
    /**
     * A Builder for {@link GraphicsObject} instances.
     */
    public static class Builder
    {
        /**
         * The indices for the {@link GraphicsObject} that is currently being
         * built
         */
        private DataBuffer indices;
        
        /**
         * The {@link Mappings.Builder} for a {@link Mapping} that maps 
         * {@link Attribute}s to {@link DataBuffer}s
         */
        private Mappings.Builder<Attribute, DataBuffer> dataBuffersBuilder;
        
        /**
         * Creates a new Builder
         * 
         * @param indices The indices of the {@link GraphicsObject} being 
         * built
         */
        private Builder(DataBuffer indices)
        {
            this.indices = indices;
            dataBuffersBuilder = Mappings.Builder.create();
        }
        
        /**
         * Set the {@link DataBuffer} for the given {@link Attribute}
         * to be a {@link DataBuffer} that is created from the given 
         * buffer.
         * 
         * @param attribute The {@link Attribute}
         * @param buffer The buffer containing the attribute data
         * @return This Builder
         */
        public Builder set(Attribute attribute, FloatBuffer buffer)
        {
            dataBuffersBuilder.put(attribute, 
                DataBuffers.createFloatDataBuffer(buffer));
            return this;
        }
        
        /**
         * Builds the {@link GraphicsObject}.
         * 
         * @return The {@link GraphicsObject}
         */
        public GraphicsObject build()
        {
            Mapping<Attribute, DataBuffer> dataBuffers = 
                dataBuffersBuilder.build();
            GraphicsObject result = 
                new DefaultGraphicsObject(indices, dataBuffers);
            return result;
        }
    }

    /**
     * Creates a {@link Builder} for {@link GraphicsObject} instances.
     * The given indices are treated as <i>unsigned</i> values, because in 
     * OpenGL, indices always have to be unsigned.
     * 
     * @param indices The indices of the {@link GraphicsObject}
     * @return The builder
     */
    public static Builder create(ByteBuffer indices)
    {
        return new Builder(DataBuffers.createDataBufferUnsignedByte(indices));
    }

    /**
     * Creates a {@link Builder} for {@link GraphicsObject} instances.
     * The given indices are treated as <i>unsigned</i> values, because in 
     * OpenGL, indices always have to be unsigned.
     * 
     * @param indices The indices of the {@link GraphicsObject}
     * @return The builder
     */
    public static Builder create(ShortBuffer indices)
    {
        return new Builder(DataBuffers.createDataBufferUnsignedShort(indices));
    }

    /**
     * Creates a {@link Builder} for {@link GraphicsObject} instances.
     * The given indices are treated as <i>unsigned</i> values, because in 
     * OpenGL, indices always have to be unsigned.
     * 
     * @param indices The indices of the {@link GraphicsObject}
     * @return The builder
     */
    public static Builder create(IntBuffer indices)
    {
        return new Builder(DataBuffers.createDataBufferUnsignedInt(indices));
    }

    
    
    /**
     * Creates a new {@link GraphicsObject} that is a plane (particularly,
     * a unit square in the xy-plane) with the specified number of points 
     * along each axis.<br>
     * <br>
     * The {@link GraphicsObject#getAttributes() attributes} of this 
     * {@link GraphicsObject} will be {@link Attributes#VERTICES}, 
     * {@link Attributes#NORMALS} and {@link Attributes#TEXCOORDS}
     * 
     * @param sizeX The number of points in x-direction
     * @param sizeY The number of points in y-direction
     * @return The new {@link GraphicsObject}
     */
    public static GraphicsObject createPlane(
        int sizeX, int sizeY)
    {
        return createPlane(sizeX, sizeY, 
            Attributes.VERTICES, Attributes.NORMALS, Attributes.TEXCOORDS);
    }
    
    /**
     * Creates a new {@link GraphicsObject} that is a plane (particularly,
     * a unit square in the xy-plane) with the specified number of points 
     * along each axis.
     * 
     * @param sizeX The number of points in x-direction
     * @param sizeY The number of points in y-direction
     * @param verticesAttribute The {@link Attribute} that should be used for
     * the vertices. This may not be <code>null</code>.
     * @param normalsAttribute The {@link Attribute} that should be used for
     * the normals. This may be <code>null</code>.
     * @param texCoordsAttribute The {@link Attribute} that should be used
     * for the texture coordinates. This may be <code>null</code>. 
     * @return The new {@link GraphicsObject}
     */
    public static GraphicsObject createPlane(
        int sizeX, int sizeY,
        Attribute verticesAttribute,
        Attribute normalsAttribute,
        Attribute texCoordsAttribute)
    {
        Objects.requireNonNull(verticesAttribute, 
            "The verticesAttribute may not be null");
        
        int indices[] = new int[(sizeX-1)*(sizeY-1)*3*2];
        int index = 0;
        for (int j = 0; j < sizeY - 1; j++)
        {
            for (int i = 0; i < sizeX - 1; i++)
            {
                int i0 = 0;
                int i1 = 0;
                int i2 = 0;
                
                i0 = i + j * sizeX;
                i1 = i + (j + 1) * sizeX;
                i2 = (i + 1) + (j + 1) * sizeX;
                
                indices[index++] = i0;
                indices[index++] = i1;
                indices[index++] = i2;

                i0 = i + j * sizeX;
                i1 = (i + 1) + (j + 1) * sizeX;
                i2 = (i + 1) + j * sizeX;

                indices[index++] = i0;
                indices[index++] = i1;
                indices[index++] = i2;
            }
        }         
        
        float vertices[] = new float[sizeX*sizeY*3];
        index = 0;
        for (int j = 0; j < sizeY; j++)
        {
            for (int i = 0; i < sizeX; i++)
            {
                float x = -0.5f + i * (1.0f / (sizeX - 1));
                float y = -0.5f + j * (1.0f / (sizeY - 1));
                float z = 0;
                vertices[index*3+0] = x;
                vertices[index*3+1] = y;
                vertices[index*3+2] = z;
                index++;
            }
        }
        
        float normals[] = new float[sizeX*sizeY*3];
        index = 0;
        for (int j = 0; j < sizeY; j++)
        {
            for (int i = 0; i < sizeX; i++)
            {
                normals[index*3+0] = 0;
                normals[index*3+1] = 0;
                normals[index*3+2] = 1;
                index++;
            }
        }
     
        float texCoords[] = new float[sizeX*sizeY*2];
        index = 0;
        for (int j = 0; j < sizeY; j++)
        {
            for (int i = 0; i < sizeX; i++)
            {
                texCoords[index*2+0] = i * (1.0f / (sizeX - 1));
                texCoords[index*2+1] = j * (1.0f / (sizeY - 1));
                index++;
            }
        }
        
        FloatBuffer verticesBuffer = BufferUtils.createDirectBuffer(vertices);
        FloatBuffer normalsBuffer = BufferUtils.createDirectBuffer(normals);
        FloatBuffer texCoordsBuffer = BufferUtils.createDirectBuffer(texCoords);
        GraphicsObjects.Builder builder =
            GraphicsObjects.create(BufferUtils.createDirectBuffer(indices));
        builder.set(verticesAttribute, verticesBuffer);
        if (normalsAttribute != null)
        {
            builder.set(normalsAttribute, normalsBuffer);
        }
        if (texCoordsAttribute != null)
        {
            builder.set(texCoordsAttribute, texCoordsBuffer);
        }
        GraphicsObject graphicsObject = builder.build();  
        return graphicsObject;
    }
    
    
    /**
     * Creates a default {@link GraphicsObject} that is a simple plane
     * (particularly, a unit square in the xy-plane). <br>
     * <br>
     * The {@link GraphicsObject#getAttributes() attributes} of this 
     * {@link GraphicsObject} will be {@link Attributes#VERTICES}, 
     * {@link Attributes#NORMALS}, {@link Attributes#TEXCOORDS}
     * and {@link Attributes#COLORS}.
     * 
     * @return A default {@link GraphicsObject}
     */
    public static GraphicsObject createPlane()
    {
        return createPlane(
            Attributes.VERTICES, Attributes.NORMALS, 
            Attributes.TEXCOORDS, Attributes.COLORS);
    }
    
    /**
     * Creates a default {@link GraphicsObject} that is a simple plane
     * (particularly, a unit square)
     * 
     * @param verticesAttribute The {@link Attribute} that should be used for
     * the vertices. This may not be <code>null</code>.
     * @param normalsAttribute The {@link Attribute} that should be used for
     * the normals. This may be <code>null</code>.
     * @param texCoordsAttribute The {@link Attribute} that should be used
     * for the texture coordinates. This may be <code>null</code>. 
     * @param colorsAttribute The {@link Attribute} that should be used for
     * the colors. This may be <code>null</code>.
     * @return A default {@link GraphicsObject}
     */
    public static GraphicsObject createPlane(
        Attribute verticesAttribute,
        Attribute normalsAttribute,
        Attribute texCoordsAttribute,
        Attribute colorsAttribute)
    {
        Objects.requireNonNull(verticesAttribute, 
            "The verticesAttribute may not be null");
        
        byte indices[] = 
        {
            0, 1, 3,
            1, 2, 3,
        };
        
        float vertices[] = 
        { 
            +0.0f, +0.0f, +0.0f, 
            +1.0f, +0.0f, +0.0f, 
            +1.0f, +1.0f, +0.0f, 
            +0.0f, +1.0f, +0.0f,
        };
        
        float normals[] = 
        { 
            +0.0f, +0.0f, +1.0f, 
            +0.0f, +0.0f, +1.0f, 
            +0.0f, +0.0f, +1.0f, 
            +0.0f, +0.0f, +1.0f,
        };
        
        float texCoords[] = 
        { 
            +0.0f, +0.0f, 
            +1.0f, +0.0f, 
            +1.0f, +1.0f, 
            +0.0f, +1.0f,
        };
            
        float colors[] = 
        { 
            +0.0f, +0.0f, +0.0f, 
            +1.0f, +0.0f, +0.0f, 
            +1.0f, +1.0f, +0.0f, 
            +0.0f, +1.0f, +0.0f,
        };
        
        FloatBuffer verticesBuffer = BufferUtils.createDirectBuffer(vertices);
        FloatBuffer normalsBuffer = BufferUtils.createDirectBuffer(normals);
        FloatBuffer texCoordsBuffer = BufferUtils.createDirectBuffer(texCoords);
        FloatBuffer colorsBuffer = BufferUtils.createDirectBuffer(colors);
        GraphicsObjects.Builder builder =
            GraphicsObjects.create(BufferUtils.createDirectBuffer(indices));
        builder.set(verticesAttribute, verticesBuffer);
        if (normalsAttribute != null)
        {
            builder.set(normalsAttribute, normalsBuffer);
        }
        if (texCoordsAttribute != null)
        {
            builder.set(texCoordsAttribute, texCoordsBuffer);
        }
        if (colorsAttribute != null)
        {
            builder.set(colorsAttribute, colorsBuffer);
        }
        GraphicsObject graphicsObject = builder.build();  
        return graphicsObject;
    }

    /**
     * Creates a default {@link GraphicsObject} that is a simple unit cube.<br>
     * <br>
     * The {@link GraphicsObject#getAttributes() attributes} of this 
     * {@link GraphicsObject} will be {@link Attributes#VERTICES}, 
     * {@link Attributes#NORMALS}, {@link Attributes#TEXCOORDS},
     * {@link Attributes#COLORS} and {@link Attributes#TANGENTS}.
     * 
     * @return A default {@link GraphicsObject}
     */
    public static GraphicsObject createCube()
    {
        return createCube(
            Attributes.VERTICES, Attributes.NORMALS, 
            Attributes.TEXCOORDS, Attributes.COLORS, 
            Attributes.TANGENTS);
    }
    
    /**
     * Creates a default {@link GraphicsObject} that is a simple unit cube
     * 
     * @param verticesAttribute The {@link Attribute} that should be used for
     * the vertices. This may not be <code>null</code>.
     * @param normalsAttribute The {@link Attribute} that should be used for
     * the normals. This may be <code>null</code>.
     * @param texCoordsAttribute The {@link Attribute} that should be used
     * for the texture coordinates. This may be <code>null</code>. 
     * @param colorsAttribute The {@link Attribute} that should be used for
     * the colors. This may be <code>null</code>.
     * @param tangentsAttribute The {@link Attribute} that should be used for
     * the tangents. This may be <code>null</code>.
     * @return A default {@link GraphicsObject}
     */
    public static GraphicsObject createCube(
        Attribute verticesAttribute,
        Attribute normalsAttribute,
        Attribute texCoordsAttribute,
        Attribute colorsAttribute,
        Attribute tangentsAttribute)
    {
        Objects.requireNonNull(verticesAttribute, 
            "The verticesAttribute may not be null");

        // "Boolean" numbering scheme
        float points[][] =
        {
            { 0, 0, 0 },
            { 1, 0, 0 },
            { 0, 1, 0 },
            { 1, 1, 0 },
            { 0, 0, 1 },
            { 1, 0, 1 },
            { 0, 1, 1 },
            { 1, 1, 1 },
        };

        byte indices[] = 
        { 
            0,   2,  1,  
            0,   3,  2, 
            4,   6,  5, 
            4,   7,  6, 
            8,  10,  9, 
            8,  11, 10, 
            12, 14, 13, 
            12, 15, 14, 
            16, 18, 17, 
            16, 19, 18, 
            20, 22, 21, 
            20, 23, 22, 
        };

        byte quadIndices[] = 
        {
            0, 1, 3, 2, // front
            5, 4, 6, 7, // back
            1, 5, 7, 3, // right
            4, 0, 2, 6, // left
            2, 3, 7, 6, // top 
            4, 5, 1, 0, // bottom
        };
        float vertices[] = new float[24*3];
        for (int i=0; i<24; i++)
        {
            vertices[i*3+0] = points[quadIndices[i]][0];
            vertices[i*3+1] = points[quadIndices[i]][1];
            vertices[i*3+2] = points[quadIndices[i]][2];
        }

        
        float normals[] = 
        { 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
             0.0f, +1.0f,  0.0f, 
             0.0f, +1.0f,  0.0f, 
             0.0f, +1.0f,  0.0f, 
             0.0f, +1.0f,  0.0f, 
             0.0f, -1.0f,  0.0f, 
             0.0f, -1.0f,  0.0f, 
             0.0f, -1.0f,  0.0f, 
             0.0f, -1.0f,  0.0f, 
        };
                                    
        float texCoords[] = 
        { 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
            0.0f, 0.0f, 
            1.0f, 0.0f, 
            1.0f, 1.0f, 
            0.0f, 1.0f, 
        };

        float colors[] = new float[24*3];
        for (int i=0; i<24*3; i++)
        {
            if (vertices[i]==1)
            {
                colors[i] = 1;
            }
        }

        float tangents[] = 
        { 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            +1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
            -1.0f,  0.0f,  0.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, -1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
             0.0f,  0.0f, +1.0f, 
        };
        
        FloatBuffer verticesBuffer = BufferUtils.createDirectBuffer(vertices);
        FloatBuffer normalsBuffer = BufferUtils.createDirectBuffer(normals);
        FloatBuffer texCoordsBuffer = BufferUtils.createDirectBuffer(texCoords);
        FloatBuffer colorsBuffer = BufferUtils.createDirectBuffer(colors);
        FloatBuffer tangentsBuffer = BufferUtils.createDirectBuffer(tangents);
        GraphicsObjects.Builder builder =
            GraphicsObjects.create(BufferUtils.createDirectBuffer(indices));
        builder.set(verticesAttribute, verticesBuffer);
        if (normalsAttribute != null)
        {
            builder.set(normalsAttribute, normalsBuffer);
        }
        if (texCoordsAttribute != null)
        {
            builder.set(texCoordsAttribute, texCoordsBuffer);
        }
        if (colorsAttribute != null)
        {
            builder.set(colorsAttribute, colorsBuffer);
        }
        if (tangentsAttribute != null)
        {
            builder.set(tangentsAttribute, tangentsBuffer);
        }
        GraphicsObject graphicsObject = builder.build();  
        return graphicsObject;
    }
 
        
    /**
     * Create a new {@link GraphicsObject} that is a unit sphere,
     * located at the origin, consisting of 20*(4^depth) triangles.<br>
     * <br>
     * The {@link GraphicsObject#getAttributes() attributes} of this 
     * {@link GraphicsObject} will be {@link Attributes#VERTICES}, 
     * {@link Attributes#NORMALS}, {@link Attributes#TEXCOORDS}
     * and {@link Attributes#COLORS}.
     * 
     * @param depth The recursion depth
     * @return The new {@link GraphicsObject}
     */
    public static GraphicsObject createSphere(int depth)
    {
        return createSphere(depth, 
            Attributes.VERTICES,  Attributes.NORMALS,  
            Attributes.TEXCOORDS,  Attributes.COLORS);
    }


    /**
     * Create a new {@link GraphicsObject} that is a unit sphere,
     * located at the origin, consisting of 20*(4^depth) triangles.
     * 
     * @param depth The recursion depth
     * @param verticesAttribute The {@link Attribute} that should be used for
     * the vertices. This may not be <code>null</code>.
     * @param normalsAttribute The {@link Attribute} that should be used for
     * the normals. This may be <code>null</code>.
     * @param texCoordsAttribute The {@link Attribute} that should be used
     * for the texture coordinates. This may be <code>null</code>. 
     * @param colorsAttribute The {@link Attribute} that should be used for
     * the colors. This may be <code>null</code>.
     * @return The new {@link GraphicsObject}
     * @throws IllegalArgumentException If the given depth is negative
     */
    public static GraphicsObject createSphere(int depth,
        Attribute verticesAttribute,
        Attribute normalsAttribute,
        Attribute texCoordsAttribute,
        Attribute colorsAttribute)
    {
        Objects.requireNonNull(verticesAttribute, 
            "The verticesAttribute may not be null");
        if (depth < 0)
        {
            throw new IllegalArgumentException(
                "The depth must be positive, but is "+depth);
        }
        
        // Coordinates and indices taken from the redbook
        final float X = 0.525731112119133606f; 
        final float Z = 0.850650808352039932f;
        
        float vdata[][] = 
        {    
            {  -X, 0.0f,    Z}, 
            {   X, 0.0f,    Z}, 
            {  -X, 0.0f,   -Z}, 
            {   X, 0.0f,   -Z},    
            {0.0f,    Z,    X}, 
            {0.0f,    Z,   -X}, 
            {0.0f,   -Z,    X}, 
            {0.0f,   -Z,   -X},    
            {   Z,    X, 0.0f}, 
            {  -Z,    X, 0.0f}, 
            {   Z,   -X, 0.0f}, 
            {  -Z,   -X, 0.0f} 
         };
         
        int tindices[][] = 
         { 
            { 0, 4, 1}, 
            { 0, 9, 4}, 
            { 9, 5, 4}, 
            { 4, 5, 8}, 
            { 4, 8, 1},    
            { 8,10, 1}, 
            { 8, 3,10}, 
            { 5, 3, 8}, 
            { 5, 2, 3}, 
            { 2, 7, 3},    
            { 7,10, 3}, 
            { 7, 6,10}, 
            { 7,11, 6}, 
            {11, 0, 6}, 
            { 0, 1, 6}, 
            { 6, 1,10}, 
            { 9, 0,11}, 
            { 9,11, 2}, 
            { 9, 2, 5}, 
            { 7, 2,11} 
        };
        
        int power = (int)Math.round(Math.pow(4, depth));
        int numTriangles = 20 * power;
        IntBuffer indices = BufferUtils.createIntBuffer(numTriangles*3);
        int numVertices = 60 * power;
        FloatBuffer vertices = BufferUtils.createFloatBuffer(numVertices*3);
        for (int i = 0; i < 20; i++) 
        { 
            FloatBuffer v1 = FloatBuffer.wrap(vdata[tindices[i][0]]);  
            FloatBuffer v2 = FloatBuffer.wrap(vdata[tindices[i][1]]);  
            FloatBuffer v3 = FloatBuffer.wrap(vdata[tindices[i][2]]);  
            subdivide(v1, v2, v3, vertices, indices, depth); 
        }
        
        float texCoords[] = new float[numVertices*2];
        for (int i=0; i<numVertices; i++)
        {
            float nx = vertices.get(i*3+0);
            float ny = vertices.get(i*3+1);
            float nz = vertices.get(i*3+2);
            texCoords[i * 2 + 0] =
                (float) (Math.atan2(nx, nz) / (2 * Math.PI) + 0.5);
            texCoords[i * 2 + 1] = (float) (Math.asin(ny) / Math.PI + 0.5);
        }
        
        vertices.rewind();
        indices.rewind();
        
        // The normals and colors are actually the same as the vertices.
        // However, copies of the vertices buffer are created here, so
        // that the contents of one buffer may be modified without
        // affecting the other attributes
        FloatBuffer normalsBuffer = BufferUtils.createDirectBuffer(vertices);
        FloatBuffer colorsBuffer = BufferUtils.createDirectBuffer(vertices);
        
        FloatBuffer texCoordsBuffer = BufferUtils.createDirectBuffer(texCoords);
        GraphicsObjects.Builder builder =
            GraphicsObjects.create(indices);
        builder.set(verticesAttribute, vertices);
        if (normalsAttribute != null)
        {
            builder.set(normalsAttribute, normalsBuffer);
        }
        if (texCoordsAttribute != null)
        {
            builder.set(texCoordsAttribute, texCoordsBuffer);
        }
        if (colorsAttribute != null)
        {
            builder.set(colorsAttribute, colorsBuffer);
        }
        GraphicsObject graphicsObject = builder.build();  
        return graphicsObject;
    }
    
    
    /**
     * Subdivision as described in the redbook
     * 
     * @param v1 Vertex 1
     * @param v2 Vertex 2
     * @param v3 Vertex 3
     * @param vertices Target vertices buffer
     * @param indices Target indices buffer
     * @param depth Recursion depth
     */
    static void subdivide(
        FloatBuffer v1, FloatBuffer v2, FloatBuffer v3, 
        FloatBuffer vertices, IntBuffer indices, long depth)
    {
        if (depth == 0) 
        {
            drawTriangle(v1, v2, v3, vertices, indices);
            return;
        }

        FloatBuffer v12 = FloatBuffer.allocate(3);
        FloatBuffer v23 = FloatBuffer.allocate(3);
        FloatBuffer v31 = FloatBuffer.allocate(3);

        for (int i = 0; i < 3; i++) 
        {
            v12.put(i, v1.get(i)+v2.get(i));
            v23.put(i, v2.get(i)+v3.get(i));
            v31.put(i, v3.get(i)+v1.get(i));
        }
        normalize(v12);
        normalize(v23);
        normalize(v31);
        subdivide( v1, v12, v31, vertices, indices, depth-1);
        subdivide( v2, v23, v12, vertices, indices, depth-1);
        subdivide( v3, v31, v23, vertices, indices, depth-1);
        subdivide(v12, v23, v31, vertices, indices, depth-1);
    }

    /**
     * Normalize the given 3D vector
     * 
     * @param v The vector
     */
    private static void normalize(FloatBuffer v)
    {
        float x = v.get(0);
        float y = v.get(1);
        float z = v.get(2);
        float length = (float)Math.sqrt(x*x+y*y+z*z);
        float invLength = 1.0f / length;
        v.put(0, x*invLength);
        v.put(1, y*invLength);
        v.put(2, z*invLength);
    }

    /**
     * Write triangle data into the target buffers
     *
     * @param v1 Vertex 1
     * @param v2 Vertex 2
     * @param v3 Vertex 3
     * @param vertices Target vertices buffer
     * @param indices Target indices buffer
     */
    private static void drawTriangle(
        FloatBuffer v1, FloatBuffer v2, FloatBuffer v3, 
        FloatBuffer vertices, IntBuffer indices)
    {
        indices.put(vertices.position()/3);
        for (int i=0; i<3; i++)
        {
            vertices.put(v1.get(i));
        }
        indices.put(vertices.position()/3);
        for (int i=0; i<3; i++)
        {
            vertices.put(v3.get(i));
        }
        indices.put(vertices.position()/3);
        for (int i=0; i<3; i++)
        {
            vertices.put(v2.get(i));
        }
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private GraphicsObjects()
    {
        // Private constructor to prevent instantiation
    }

}
