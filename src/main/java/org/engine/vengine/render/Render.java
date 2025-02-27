
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

import org.engine.vengine.render.window.Window;
import org.engine.vengine.time.Time;
import org.engine.vengine.utils.Vertex;

import static org.lwjgl.opengl.GL11.*;

public class Render {
    private long WID;
    private Window window;
    private RenderableObject o;

    public Render(Window window) {
        this.window = window;
    }

    public void initialize(long WID){
        this.WID = WID;
    }

    public void startRenderPoll(){
        glEnable(GL_DEPTH_TEST);
        Time time = new Time();
        o = new RenderableObject();
        while (!window.appShouldClose()){
            time.update();
            render();
        }
        window.terminate();
    }

    private void render() {
        window.clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        window.fillClearColor(0f, 0f, 0f, 1.0f);
        o.render();
        window.swapBuffer();
        window.pollEvents();
    }

}
