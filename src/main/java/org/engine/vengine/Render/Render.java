package org.engine.vengine.Render;

import org.engine.vengine.Debug.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Render {
    private long windowHandle;
    private RenderQueue renderQueue;

    public Render() {
        renderQueue = new RenderQueue();
    }

    public void init(long windowHandle) {
        this.windowHandle = windowHandle;
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void loop() {
        while (!glfwWindowShouldClose(windowHandle)) {
            update();
            render();
            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
        }
        renderQueue.cleanup();
    }

    private void update() {
        // Обновление логики игры или приложения
    }

    private void render() {
        try{
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            renderQueue.renderAll();
        }
        catch(Exception e){

        }
    }

    public void addRenderable(Renderable renderable) {
        renderQueue.add(renderable);
    }

    public void removeRenderable(Renderable renderable) {
        renderQueue.remove(renderable);
    }
}
