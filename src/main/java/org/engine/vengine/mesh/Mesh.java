
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

import org.engine.vengine.filesystem.Default;
import org.engine.vengine.render.RenderableObject;
import org.engine.vengine.render.materials.Material;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh implements RenderableObject {

    private MeshData data;
    private Material material;

    private int VAO;
    private int VBO;
    private int EBO;


    public Mesh(MeshData data, VertexFormat format){
        this.material = Default.MATERIAL;
        this.data = data;

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, data.getVertices(), GL_STATIC_DRAW);

        EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.getIndices(), GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;

        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(2);

        for (VertexAttribute attr : format.getAttributes()) {
            glVertexAttribPointer(attr.index, attr.size, attr.type, false, format.getStride(), attr.offset);
            glEnableVertexAttribArray(attr.index);
        }

        glBindVertexArray(0);
    }
    public Mesh(MeshData data, VertexFormat format, Material material){
        this.material = material;
        this.data = data;

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, data.getVertices(), GL_STATIC_DRAW);

        EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.getIndices(), GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;

        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(2);

        for (VertexAttribute attr : format.getAttributes()) {
            glVertexAttribPointer(attr.index, attr.size, attr.type, false, format.getStride(), attr.offset);
            glEnableVertexAttribArray(attr.index);
        }

        glBindVertexArray(0);
    }
    @Override
    public void render() {
        material.apply();
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, data.getIndices().length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
    @Override
    public void cleanup() {
        this.data = null;
    }
}
