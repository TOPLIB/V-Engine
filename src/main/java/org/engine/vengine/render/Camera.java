
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

package org.engine.vengine.render;

import org.joml.Vector3f;

public class Camera {
    private Vector3f cameraPosition;
    private Vector3f cameraTarget;
    private Vector3f cameraDirection;

    public Camera(Vector3f position, Vector3f target, Vector3f direction){
        this.cameraPosition = position;
        this.cameraTarget = target;
        this.cameraDirection = direction;
    }

    public void initialize(){
        Vector3f worldUp = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f cameraRight = worldUp.cross(cameraDirection, new Vector3f()).normalize();
    }
}
