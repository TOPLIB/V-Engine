
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

package org.engine.vengine.render;

import org.engine.vengine.filesystem.ENV;
import org.engine.vengine.time.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Render {
    private long WID;
    public Render(long WID){
        this.WID = WID;
        render(WID);
    }

    private void render(long WID){
        boolean appShouldClose = Boolean.parseBoolean(ENV.get("app_should_close").toString());

        RenderableObject o = new RenderableObject();
        Time time = new Time();
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        while (!glfwWindowShouldClose(WID) && !Thread.currentThread().isInterrupted() && !appShouldClose) {
            time.update();
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0f, 0f, 0f, 1.0f);
            o.render();
            glfwPollEvents();
            glfwSwapBuffers(WID);
        }
        ENV.set("app_should_close", true);
        glfwTerminate();
    }
}
