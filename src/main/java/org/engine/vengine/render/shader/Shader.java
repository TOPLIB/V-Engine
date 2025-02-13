
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

package org.engine.vengine.render.shader;

import org.engine.vengine.debug.LogLevel;
import org.engine.vengine.debug.Logger;

import java.util.Arrays;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Shader {

    public static void main(String[] a){
        int vertexShader;
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, "#version 330 core\n" +
                "\n" +
                "layout (location = 0) in vec3 position;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = vec4(position.x, position.y, position.z, 1.0);\n" +
                "}");
        glCompileShader(vertexShader);

        char[] infolog = new char[512];
        int success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            Logger.print(LogLevel.CRITICAL, "Error, cannot compile shader '" + vertexShader + "'. Latest log: " + Arrays.toString(infolog));
        }
    }
}
