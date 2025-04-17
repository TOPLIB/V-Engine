
/*
 * V-Engine
 * Copyright (C) 2025
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.engine.vengine.mesh;

public class MeshData {

    private float[] vertices;
    private float[] uvs;
    private float[] normals;
    private int[] indices;

    public MeshData(float[] vertices, float[] uvs, float[] normals, int[] indices) {
        this.vertices = vertices;
        this.uvs = uvs;
        this.normals = normals;
        this.indices = indices;
    }

    // Getters
    public float[] getVertices() {
        return vertices;
    }

    public float[] getUVs() {
        return uvs;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    // Setters
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public void setUVs(float[] uvs) {
        this.uvs = uvs;
    }

    public void setNormals(float[] normals) {
        this.normals = normals;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
}
