
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

import org.engine.vengine.utils.TextureCoordinate;

public class Mesh {

    private final float[] vertices;
    private final int[] indices;
    private final TextureCoordinate[] texCoords;

    public Mesh(float[] vertices, int[] indices, TextureCoordinate[] texCoords){
        this.vertices = vertices;
        this.indices = indices;
        this.texCoords = texCoords;
    }

    /*
     * Executes at Render Engine pre-phase
     */
    public void initialize() {
        
    }

    /*
        * It is called every time the rendering is called.
     */
    public void render() {

    }

    /*
        * Executes when Mesh will be destroyed.
     */
    public void cleanup() {

    }
}
