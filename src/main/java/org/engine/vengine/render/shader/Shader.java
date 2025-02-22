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
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Shader {
    private int programId;

    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(File vertexShader, File fragmentShader) {
        // Init OpenGL shader program
        programId = glCreateProgram();
        if (programId == 0) {
            Logger.error(LogLevel.SEVERE, "Cannot create OpenGL program");
            return; // Exit if program creation fails
        }

        // Read shader code from files
        String vertexCode = readFromFile(vertexShader);
        String fragmentCode = readFromFile(fragmentShader);

        // Create shaders and attach them to program
        vertexShaderId = createShader(vertexCode, GL_VERTEX_SHADER);
        fragmentShaderId = createShader(fragmentCode, GL_FRAGMENT_SHADER);

        // Link shaders into program after both are created
        linkProgram();
    }

    private String readFromFile(File path) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            Logger.error(LogLevel.WARNING, "Cannot load file: " + e.getMessage());
        } catch (IOException e) {
            Logger.error(LogLevel.WARNING, "Cannot read line from file: " + e.getMessage());
        }
        return result.toString();
    }

    private int createShader(String source, int type) {
        int shader = glCreateShader(type);
        if (shader == 0) {
            Logger.error(LogLevel.SEVERE, "Failed to create shader.");
            Logger.exit(-1);
            return 0;
        }

        glShaderSource(shader, source);
        glCompileShader(shader);

        // Check if shader compiled successfully
        int shaderStatus = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (shaderStatus == GL_FALSE) {
            String log = glGetShaderInfoLog(shader);
            Logger.error(LogLevel.SEVERE, "Error, cannot compile shader.");
            Logger.print(LogLevel.SEVERE, log);
            Logger.exit(-1);
            return 0;
        }

        // Return created shader
        return shader;
    }

    private void linkProgram() {
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        glLinkProgram(programId);

        // Check for linking errors
        int linkStatus = glGetProgrami(programId, GL_LINK_STATUS);
        if (linkStatus == GL_FALSE) {
            String log = glGetProgramInfoLog(programId);
            Logger.error(LogLevel.SEVERE, "Error, linking shader program.");
            Logger.print(LogLevel.SEVERE, log);
            Logger.exit(-1);
        }
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
    }

    public void use(){
        glUseProgram(programId);
    }

    public void setInt(String name, int value) {
        int location = glGetUniformLocation(programId, name);
        if (location != -1) {
            glUniform1i(location, value);
        } else {
            Logger.print(LogLevel.WARNING, "Uniform " + name + " not found.");
        }
    }


    public int getProgramId(){
        return programId;
    }

    public void cleanup(){
        if (programId != 0) {
            glUseProgram(0);
            glDeleteProgram(programId);
        } else {
            Logger.print(LogLevel.WARNING, "Shader already cleanup");
        }
    }

}
