
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

package org.engine.vengine.utils;

public class FaceElement {
    public int vertexIndex;
    public Integer texCoordIndex;
    public Integer normalIndex;

    public FaceElement(int vertexIndex, Integer texCoordIndex, Integer normalIndex) {
        this.vertexIndex = vertexIndex;
        this.texCoordIndex = texCoordIndex;
        this.normalIndex = normalIndex;
    }

    @Override
    public String toString() {
        return "FaceElement{" +
                "vertexIndex=" + vertexIndex +
                ", texCoordIndex=" + texCoordIndex +
                ", normalIndex=" + normalIndex +
                '}';
    }
}
