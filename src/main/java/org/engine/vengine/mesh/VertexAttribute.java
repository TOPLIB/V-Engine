
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

public class VertexAttribute {
    public final int index;
    public final int size; // components per attribute
    public final int type; // GL_FLOAT, etc.
    public final int offset;

    public VertexAttribute(int index, int size, int type, int offset) {
        this.index = index;
        this.size = size;
        this.type = type;
        this.offset = offset;
    }
}
