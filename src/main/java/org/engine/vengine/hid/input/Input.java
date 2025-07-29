
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

package org.engine.vengine.hid.input;

import org.lwjgl.glfw.GLFW;

public class Input {
    public Boolean isKeyPressed(long window, int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

    public Boolean isKeyReleased(long window, int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_RELEASE;
    }

    public Boolean isKeyRepeated(long window, int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_REPEAT;
    }

    public Boolean isMouseButtonPressed(long window, int button) {
        return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS;
    }

    public double[] getMousePosition(long window) {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(window, x, y);
        return new double[]{x[0], y[0]};
    }

    public float[] getMousePositionFloat(long window) {
        double[] pos = getMousePosition(window);
        return new float[]{(float) pos[0], (float) pos[1]};
    }

    public Boolean isMouseInsideWindow(long window) {
        double[] pos = getMousePosition(window);
        float[] size = getWindowSize(window);
        return pos[0] >= 0 && pos[0] <= size[0] && pos[1] >= 0 && pos[1] <= size[1];
    }

    public float[] getWindowSize(long window) {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(window, width, height);
        return new float[]{(float) width[0], (float) height[0]};
    }
}
