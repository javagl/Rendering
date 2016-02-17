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

package de.javagl.rendering.geometry;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

/**
 * Default implementation of a {@link MutableRenderGeometry}.
 */
class DefaultRenderGeometry implements MutableRenderGeometry
{
    // TODO The updateNormals method of this class
    // can not be called - think about possible
    // improvements of the NormalUpdater and its usage
    
    /**
     * The indices of this Geometry
     */
    private final DefaultIntArray indices;
    
    /**
     * The vertices of this Geometry
     */
    private final DefaultArray3f vertices;
    
    /**
     * The normals of this Geometry
     */
    private final DefaultArray3f normals;
    
    /**
     * The texture coordinates of this Geometry
     */
    private final DefaultArray2f texCoords;

    /**
     * For each vertex, this contains a list of indices of 
     * the triangles that the respective vertex belongs to
     */
    private final List<List<Integer>> vertexTriangleIndices;

    /**
     * The triangle normals of this Geometry
     */
    private final DefaultArray3f triangleNormals;

    /**
     * Creates a new DefaultRenderGeometry
     */
    DefaultRenderGeometry()
    {
        indices = new DefaultIntArray();
        vertices = new DefaultArray3f();
        normals = new DefaultArray3f();
        texCoords = new DefaultArray2f();
        vertexTriangleIndices = new ArrayList<List<Integer>>();
        triangleNormals = new DefaultArray3f();
    }
    
    
    /**
     * Add the given vertex to this geometry.
     * 
     * @param vertex The vertex
     */
    void addVertex(Tuple3f vertex)
    {
        vertices.add(new Point3f(vertex));
        vertexTriangleIndices.add(new ArrayList<Integer>());
    }
    
    /**
     * Add the given texture coordinate to this geometry
     * 
     * @param texCoord The texture coordinate
     */
    void addTexCoord(Tuple2f texCoord)
    {
        texCoords.add(new TexCoord2f(texCoord));
    }

    /**
     * Adds the given normal to this geometry
     * 
     * @param normal The normal
     */
    void addNormal(Tuple3f normal)
    {
        normals.add(new Vector3f(normal));
    }

    /**
     * Add the triangle consisting of the given indices to
     * this geometry. The same indices will be used for
     * vertices, texture coordinates and normals.
     * 
     * @param i0 The first index
     * @param i1 The second index
     * @param i2 The third index
     */
    void addTriangle(int i0, int i1, int i2)
    {
        int triangleIndex = indices.getSize() / 3;
        indices.add(i0);
        indices.add(i1);
        indices.add(i2);
        vertexTriangleIndices.get(i0).add(triangleIndex);
        vertexTriangleIndices.get(i1).add(triangleIndex);
        vertexTriangleIndices.get(i2).add(triangleIndex);
        triangleNormals.add(new Vector3f());
    }


//    /**
//     * Initialize the normals of this Geometry so that it
//     * contains as many normals as vertices. Any previously
//     * added normals will be overwritten.
//     */
//    public void initNormals()
//    {
//        initTriangleNormals();
//        initVertexNormals();
//        updateNormals();
//    }
//    
//    /**
//     * Initialize the triangle normals
//     */
//    private void initTriangleNormals()
//    {
//        triangleNormals = new DefaultArray3f();
//        for (int i = 0; i < indices.getSize() / 3; i++)
//        {
//            Vector3f triangleNormal = new Vector3f();
//            triangleNormals.add(triangleNormal);
//        }
//    }
//    
//    /**
//     * Initialize the vertex normals
//     */
//    private void initVertexNormals()
//    {
//        normals = new DefaultArray3f();
//        for (int i = 0; i < vertices.getSize(); i++)
//        {
//            Vector3f vertexNormal = new Vector3f();
//            normals.add(vertexNormal);
//        }
//    }
    

    /**
     * Update the normals of this Geometry according to 
     * the current vertex positions. It is assumed that
     * the number of vertices and the number of 
     * normals is equal.
     */
    void updateNormals()
    {
        Vector3f edge0 = new Vector3f();
        Vector3f edge1 = new Vector3f();
        Point3f v0 = new Point3f();
        Point3f v1 = new Point3f();
        Point3f v2 = new Point3f();
        Vector3f triangleNormal = new Vector3f();
        for (int i = 0; i < indices.getSize() / 3; i++)
        {
            int i0 = indices.get(i * 3 + 0);
            int i1 = indices.get(i * 3 + 1);
            int i2 = indices.get(i * 3 + 2);
            vertices.get3f(i0, v0);
            vertices.get3f(i1, v1);
            vertices.get3f(i2, v2);
            edge0.sub(v1, v0);
            edge1.sub(v2, v0);
            triangleNormals.get3f(i, triangleNormal);
            triangleNormal.cross(edge0, edge1);
            triangleNormal.normalize();
            triangleNormals.set3f(i, triangleNormal);
        }

        Vector3f vertexNormal = new Vector3f();
        for (int i = 0; i < vertices.getSize(); i++)
        {
            vertexNormal.set(0,0,0);
            for (int j = 0; j < vertexTriangleIndices.get(i).size(); j++)
            {
                int index = vertexTriangleIndices.get(i).get(j);
                triangleNormals.get3f(index, triangleNormal);
                vertexNormal.add(triangleNormal);
            }
            vertexNormal.normalize();
            normals.set3f(i, vertexNormal);
            //System.out.println("Normal "+vertexNormal);
        }
    }

    @Override
    public IntArray getIndices()
    {
        return indices;
    }

    @Override
    public MutableArray3f getVertices()
    {
        return vertices;
    }

    @Override
    public MutableArray3f getNormals()
    {
        return normals;
    }

    @Override
    public MutableArray2f getTexCoords()
    {
        return texCoords;
    }
    
    

}
