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
import org.engine.vengine.render.shader.Shader;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.engine.vengine.debug.LogLevel;
import org.engine.vengine.debug.Logger;
import org.engine.vengine.filesystem.DEFAULT_STRINGS;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import  static org.lwjgl.system.MemoryUtil.*;

public class Window implements Runnable{

    private long WID; // Window ID
    private int width;
    private int height;
    private String title;

    private Input inputSystem; // InputSystem version 1;

    public Window(String title, int width, int height){
        File configFile = new File("engine.conf.ini");

        if (!configFile.exists()) {
            Logger.print(LogLevel.SEVERE, "Cannot find 'engine.conf.ini', creating new one for you.");

            // Creating config
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(DEFAULT_STRINGS.ENGING_CONF_INI);
                Logger.print(LogLevel.INFO, "'engine.conf.ini' created successfully.");
            } catch (IOException e) {
                Logger.print(LogLevel.SEVERE, "Failed to create 'engine.conf.ini'.");
            }
        }
        try {
            Ini cfg = new Ini(configFile);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, Integer.parseInt(cfg.get("OPENGL", "version_major")));
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, Integer.parseInt(cfg.get("OPENGL", "version_minor")));
        } catch (Exception e) {
            Logger.print(LogLevel.SEVERE, "Failed to load 'engine.conf.ini', using default values");
            try {
                Ini cfg = new Ini(new StringReader(DEFAULT_STRINGS.ENGING_CONF_INI));
                glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, Integer.parseInt(cfg.get("OPENGL", "version_major")));
                glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, Integer.parseInt(cfg.get("OPENGL", "version_minor")));
                glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
            } catch (IOException ignored) {}
        }

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        this.title = title;
        this.width = width;
        this.height = height;

        if(!glfwInit()){
            Logger.print(LogLevel.CRITICAL, "OpenGL init Error, cannot initialize GLFW. Application will close soon");
            System.exit(-1);
        }
    }

    private void initWindow(){
        WID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (WID == NULL) {
            Logger.print(LogLevel.CRITICAL, "Failed to create GLFW window");
            System.exit(-1);
        }

        glfwMakeContextCurrent(WID); // Set OpenGL context
        glfwShowWindow(WID); // Show window
        glfwSetWindowAttrib(WID, GLFW_RESIZABLE, 0);
        GL.createCapabilities();
        glViewport(0, 0, width, height);

        Shader shader = new Shader();
        shader.main(null);
        Render render = new Render(WID);
    }


    @Override
    public void run() {
        initWindow();
    }
}
