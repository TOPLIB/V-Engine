package org.engine.vengine.Render;

import org.engine.vengine.Debug.LogLevel;
import org.engine.vengine.Debug.Logger;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long wid;
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

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        wid = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( wid == NULL ){
            Logger.print(LogLevel.CRITICAL, "Failed to create the GLFW window");
            System.exit(-1);
        }

        glfwSetKeyCallback(wid, (wid, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(wid, true);
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            glfwGetWindowSize(wid, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    wid,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        glfwMakeContextCurrent(wid);
        glfwSwapInterval(1);
        glfwShowWindow(wid);

        render.loop(wid);
    }

}
