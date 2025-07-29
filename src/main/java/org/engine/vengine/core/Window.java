package org.engine.vengine.core;

import org.engine.vengine.utils.Logger;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static final Logger logger = Logger.getLogger(Window.class);
    private final String title;
    private final int width;
    private final int height;
    private long windowHandle;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init() {
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        // Center window on screen
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowHandle, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidmode != null) {
                glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
                );
            }
        }

        // Make OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(1); // Enable v-sync
        glfwShowWindow(windowHandle);

        logger.info("Window initialized successfully");
    }

    public void update() {
        glfwPollEvents();
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void clear(int mask) {
        glClear(mask);
    }

    public void fillClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public boolean appShouldClose() {
        return shouldClose();
    }

    public void cleanup() {
        glfwDestroyWindow(windowHandle);
    }

    public void terminate() {
        glfwTerminate();
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public long getWindowID() {
        return windowHandle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
} 