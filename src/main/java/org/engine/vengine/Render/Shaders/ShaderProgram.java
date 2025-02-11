package org.engine.vengine.Render.Shaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int programID;

    public ShaderProgram(String vertexPath, String fragmentPath) {
        int vertexID = loadShader(vertexPath, GL_VERTEX_SHADER);
        int fragmentID = loadShader(fragmentPath, GL_FRAGMENT_SHADER);

        programID = glCreateProgram();
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        glLinkProgram(programID);

        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(programID);
            cleanup();
            throw new RuntimeException("Shader linking failed:\n" + log);
        }

        glDetachShader(programID, vertexID);
        glDetachShader(programID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    private int loadShader(String path, int type) {
        String source;
        try {
            source = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader file: " + path, e);
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, source);
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(shaderID);
            glDeleteShader(shaderID);
            throw new RuntimeException("Shader compilation failed:\n" + log);
        }

        return shaderID;
    }

    public void use() {
        glUseProgram(programID);
    }

    public void cleanup() {
        glDeleteProgram(programID);
    }

    public int getProgramID() {
        return programID;
    }
}