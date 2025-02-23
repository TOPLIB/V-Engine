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

package org.engine.vengine.render.window;

import org.engine.vengine.hid.input.Input;
import org.engine.vengine.render.Render;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.engine.vengine.filesystem.Default;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    private long WID; // Window ID
    private int width;
    private int height;
    private String title;

    private Input inputSystem; // InputSystem version 1

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);


        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        if (!glfwInit()) {
            logger.error("OpenGL init Error, cannot initialize GLFW. Application will close soon.");
            System.exit(-1);
        }
    }

    private void initWindow() {
        WID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (WID == NULL) {
            logger.error("Failed to create GLFW window");
            System.exit(-1);
        }

        // Center window
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(WID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    WID,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(WID); // Set OpenGL context
        glfwShowWindow(WID); // Show window
        glfwSetWindowAttrib(WID, GLFW_RESIZABLE, GL_FALSE);
        GL.createCapabilities();
        glViewport(0, 0, width, height);

        String SystemInfo = glfwGetVersionString();
        String version = glGetString(GL_VERSION);
        String vendor = glGetString(GL_VENDOR);
        String gpu = glGetString(GL_RENDERER);
        logger.debug("\n\nGLFW Version: {}\n" +
                "OpenGL Version: {}\n" +
                "GPU Vendor: {}\n" +
                "GPU Renderer: {}", SystemInfo, version, vendor, gpu);

        Render render = new Render(WID);
    }

    @Override
    public void run() {
        initWindow();
    }
}
