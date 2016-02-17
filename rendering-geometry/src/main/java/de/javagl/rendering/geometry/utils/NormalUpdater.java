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

package de.javagl.rendering.geometry.utils;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.javagl.rendering.geometry.Array3f;
import de.javagl.rendering.geometry.GeometryArrays;
import de.javagl.rendering.geometry.IntArray;
import de.javagl.rendering.geometry.MutableArray3f;
import de.javagl.rendering.geometry.MutableRenderGeometry;

/**
 * Utility class for updating the vertex normals of a 
 * {@link MutableRenderGeometry}, by computing the 
 * average of the normals of the triangles that are
 * adjacent to the respective vertex.
 */
public class NormalUpdater
{
    /**
     * Update the vertex normals of the given {@link MutableRenderGeometry}.
     * 
    * @param geometry The {@link MutableRenderGeometry}
    * @throws IllegalArgumentException If the given geometry does not 
    * have vertex normals.
     */
    public static void updateNormals(MutableRenderGeometry geometry)
    {
        IntArray indices = geometry.getIndices();
        Array3f vertices = geometry.getVertices();
        MutableArray3f normals = geometry.getNormals();
        if (normals == null)
        {
            throw new IllegalArgumentException(
                "The given geometry does not have vertex normals");
        }

        // Create the list that stores for each vertex the list of indices
        // of triangles that are adjacent to the vertex
        List<List<Integer>> vertexTriangleIndices = 
            new ArrayList<List<Integer>>();
        int numVertices = vertices.getSize();
        for (int i=0; i<numVertices; i++)
        {
            vertexTriangleIndices.add(new ArrayList<Integer>());
        }
        
        // Fill the list of triangle indices for the vertices
        // and create the triangle normals
        int numTriangles = indices.getSize() / 3;
        for (int i=0; i<numTriangles; i++)
        {
            int i0 = indices.get(i*3+0);
            int i1 = indices.get(i*3+1);
            int i2 = indices.get(i*3+2);
            vertexTriangleIndices.get(i0).add(i);
            vertexTriangleIndices.get(i1).add(i);
            vertexTriangleIndices.get(i2).add(i);
        }
        
        // Compute the triangle normals
        Vector3f edge0 = new Vector3f();
        Vector3f edge1 = new Vector3f();
        Point3f v0 = new Point3f();
        Point3f v1 = new Point3f();
        Point3f v2 = new Point3f();
        Vector3f triangleNormal = new Vector3f();
        MutableArray3f triangleNormals = 
            GeometryArrays.createMutableArray3f(numTriangles);
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
            //triangleNormal.normalize(); // Not required
            triangleNormals.set3f(i, triangleNormal);
        }

        // Compute the vertex normals
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
        }
    }


    /**
     * Private constructor to prevent instantiation
     */
    private NormalUpdater()
    {
        // Private constructor to prevent instantiation
    }
}
