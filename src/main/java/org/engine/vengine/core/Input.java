package org.engine.vengine.core;

import org.engine.vengine.utils.Logger;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static final Logger logger = Logger.getLogger(Input.class);
    private static final Map<Integer, Boolean> keyStates = new HashMap<>();
    private static final Map<Integer, Boolean> mouseButtonStates = new HashMap<>();
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWScrollCallback scrollCallback;

    public static void init(long windowHandle) {
        // Key callback
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    keyStates.put(key, true);
                } else if (action == GLFW_RELEASE) {
                    keyStates.put(key, false);
                }
            }
        };
        glfwSetKeyCallback(windowHandle, keyCallback);

        // Mouse button callback
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    mouseButtonStates.put(button, true);
                } else if (action == GLFW_RELEASE) {
                    mouseButtonStates.put(button, false);
                }
            }
        };
        glfwSetMouseButtonCallback(windowHandle, mouseButtonCallback);

        // Scroll callback
        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX = xoffset;
                scrollY = yoffset;
            }
        };
        glfwSetScrollCallback(windowHandle, scrollCallback);

        logger.info("Input system initialized successfully");
    }

    public static boolean isKeyPressed(int keyCode) {
        return keyStates.getOrDefault(keyCode, false);
    }

    public static boolean isMouseButtonPressed(int button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    public static void update(long windowHandle) {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(windowHandle, xpos, ypos);
        mouseX = xpos[0];
        mouseY = ypos[0];
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public static void cleanup() {
        keyCallback.free();
        mouseButtonCallback.free();
        scrollCallback.free();
    }
} 