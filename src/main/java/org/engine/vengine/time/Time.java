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

package org.engine.vengine.time;

public class Time {

    private static float deltaTime;
    private static float time;
    private static int fps;
    private int frameCount = 0;
    private float fpsTimer = 0;

    private float previousTime = System.nanoTime();

    public void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - previousTime) / 1000000000.0f;
        previousTime = currentTime;

        time = currentTime / 1000000.0f;


        frameCount++;
        fpsTimer += deltaTime;
        if (fpsTimer >= 1.0f) {
            fps = frameCount;
            frameCount = 0;
            fpsTimer = 0;
        }
    }

    public static float deltaTime() {
        return deltaTime;
    }

    public static float time() {
        return time;
    }

    public static int fps() {
        return fps;
    }
}
