package org.engine.vengine.Render;

import org.engine.vengine.Debug.LogLevel;
import org.engine.vengine.Debug.Logger;
import org.engine.vengine.DefaultShapes.Cube;
import org.engine.vengine.Render.Materials.Material;
import org.engine.vengine.Utils.Transform;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long windowHandle;
    private String title;
    private int width;
    private int height;
    private Render render;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.render = new Render();
        init();
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() ){
            Logger.print(LogLevel.CRITICAL, "Unable initialize GLFW");
            System.exit(-1);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( windowHandle == NULL ){
            Logger.print(LogLevel.CRITICAL, "Failed to create the GLFW window");
            System.exit(-1);
        }

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        centerWindow();
        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(1);
        glfwShowWindow(windowHandle);

        render.init(windowHandle);
    }

    private void centerWindow() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(windowHandle, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
    }

    public void run() {
        // Добавление объектов для отрисовки
        Cube cube = new Cube(new Transform(), new Material(
                new float[]{0.5f, 0.0f, 0.0f, 1.0f},
                new float[]{0.8f, 0.0f, 0.0f, 1.0f},
                new float[]{1.0f, 0.0f, 0.0f, 1.0f},
                32f
        ));
        render.addRenderable(cube);
        render.loop();
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }
}
