
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

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexFormat {
    private final List<VertexAttribute> attributes = new ArrayList<>();
    private int stride;

    public void addAttribute(int index, int size, int type) {
        int offset = stride;
        attributes.add(new VertexAttribute(index, size, type, offset));
        stride += size * getTypeSize(type);


    }

    public List<VertexAttribute> getAttributes() {
        return attributes;
    }

    public int getStride() {
        return stride;
    }

    private int getTypeSize(int type) {
        switch (type) {
            case GL_FLOAT:
                return Float.BYTES;
            case GL_INT:
                return Integer.BYTES;
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }
}
