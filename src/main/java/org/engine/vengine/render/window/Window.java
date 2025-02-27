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
import org.engine.vengine.utils.SystemInfo;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private static final Logger logger = LoggerFactory.getLogger("Window");

    private long WID; // Window ID
    private int width;
    private int height;
    private String title;

    private Input inputSystem;
    public Window(){

    }
    public void initialize(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        if (!glfwInit()) {
            logger.error("OpenGL init Error, cannot initialize GLFW. Application will close soon.");
            System.exit(-1);
        }

        setGLFWHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        setGLFWHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        setGLFWHint(GLFW_RESIZABLE, GL_FALSE);
        setGLFWHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        WID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (WID == NULL) {
            logger.error("Failed to create GLFW window");
            System.exit(-1);
        }

        makeContextCurrent();
        createCapabilities();
        setViewport(width, height);
        centerWindow();
        showWindow();

        SystemInfo.initialize();
    }

    public void startRender() {
        Render render = new Render(this);
        render.initialize(getWindowID());
        render.startRenderPoll();
    }


    public void clear(int mask){
        glClear(mask);
    }
    public void fillClearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }
    public long getWindowID(){
        return WID;
    }
    public boolean appShouldClose(){
        return glfwWindowShouldClose(WID) ||
                Thread.currentThread().isInterrupted();
    }
    public void makeContextCurrent(){
        glfwMakeContextCurrent(WID);
    }
    public void setAttribute(int attribute, int parameter){
        glfwSetWindowAttrib(WID, attribute, parameter);
    }
    public void setGLFWHint(int hint, int parameter){
        glfwWindowHint(hint, parameter);
    }
    public void createCapabilities(){
        GL.createCapabilities();
    }
    public void setViewport(int width, int height){
        glViewport(0, 0, width, height);
    }
    public void showWindow(){
        glfwShowWindow(WID);
    }
    public void hideWindow(){
        glfwHideWindow(WID);
    }
    public void centerWindow(){
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(WID, pWidth, pHeight);
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            assert videoMode != null;
            glfwSetWindowPos(
                    WID,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        }
    }
    public void terminate(){
        glfwTerminate();
    }
    public void swapBuffer(){
        glfwSwapBuffers(WID);
    }
    public void pollEvents(){
        glfwPollEvents();
    }

}
