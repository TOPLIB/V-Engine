
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

import java.io.*;
import java.util.Arrays;
import java.util.Set;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Shader {
    private int programId;

    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(File vertexShader, File fragmentShader){
        // Init OpenGL shader program

        programId = glCreateProgram();
        if(programId == 0){
            Logger.error(LogLevel.SEVERE, "Cannot create OpenGL program");
        }

        String fragmentSource;
        String vertexSource;

        try {
            // Correct the second reader to read from vertexShader instead of fragmentShader
            BufferedReader fragmentReader = new BufferedReader(new FileReader(fragmentShader));
            BufferedReader vertexReader = new BufferedReader(new FileReader(vertexShader)); // Changed here

            String line;
            while ((line = fragmentReader.readLine()) != null){
                System.out.println(line); // Print the actual line, not just "1"
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred: " + e.getMessage(), e);
        }


        fragmentShaderId = createShader(new BufferedReader(fragmentShader), GL_FRAGMENT_SHADER);
        vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
    }


    private int createShader(String source, int type){
        int shader = glCreateShader(type);

        if(shader == 0){
            Logger.error(LogLevel.SEVERE, "Failed to create shader.");
        }
        glShaderSource(shader, source);
        glCompileShader(shader);


        // Compiling shader
        int shaderStatus = glGetShaderi(shader, GL_COMPILE_STATUS);
        if(shaderStatus == GL_FALSE){
            String log = glGetShaderInfoLog(shader);
            Logger.print(LogLevel.SEVERE, "Error, cannot compile shader '" + shader + "'");
            Logger.print(LogLevel.SEVERE, "Log: '" + log + "'");
        }
        // END

        // Link status
        int linkStatus = glGetProgrami(programId, GL_LINK_STATUS);

        if(linkStatus == GL_FALSE){
            String log = glGetShaderInfoLog(shader);
            Logger.print(LogLevel.SEVERE, "Error, linking shader program '" + shader + "'");
            Logger.print(LogLevel.SEVERE, "Log: '" + log + "'");
        }
        // END

        return shader;
    }

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
            Logger.error(LogLevel.CRITICAL, "Error, cannot compile shader '" + vertexShader + "'. Latest log: " + Arrays.toString(infolog));
        }
    }
}
