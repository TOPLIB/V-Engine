package org.engine.vengine.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private static final Logger logger = LoggerFactory.getLogger(Shader.class);

    public Shader(String vertexShader, String fragmentShader) {
        programId = glCreateProgram();
        if (programId == 0) {
            logger.error("Cannot create OpenGL program");
            return;
        }


        vertexShaderId = createShader(vertexShader, GL_VERTEX_SHADER);
        fragmentShaderId = createShader(fragmentShader, GL_FRAGMENT_SHADER);

        linkProgram();
    }

    private String readFromFile(File path) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.warn("Cannot load or read file: " + e.getMessage());
        }
        return result.toString();
    }

    private int createShader(String source, int type) {
        int shader = glCreateShader(type);
        if (shader == 0) {
            logger.error("Failed to create shader.");
            System.exit(-1);
            return 0;
        }

        glShaderSource(shader, source);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Error compiling shader: " + glGetShaderInfoLog(shader));
            System.exit(-1);
        }

        return shader;
    }

    private void linkProgram() {
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            logger.error("Error linking shader program: " + glGetProgramInfoLog(programId));
            System.exit(-1);
        }
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
    }

    public void use() {
        glUseProgram(programId);
    }

    public void setInt(String name, int value) {
        int location = glGetUniformLocation(programId, name);
        if (location != -1) {
            glUniform1i(location, value);
        } else {
            logger.warn("Uniform " + name + " not found.");
        }
    }

    public void setFloat(String name, float value) {
        int location = glGetUniformLocation(programId, name);
        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    public void setVector3f(String name, Vector3f vector) {
        int location = glGetUniformLocation(programId, name);
        if (location != -1) {
            glUniform3f(location, vector.x, vector.y, vector.z);
        }
    }

    public void setMatrix4f(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(programId, name);
        if (location != -1) {
            FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
            matrix.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    public int getProgramId() {
        return programId;
    }

    public void cleanup() {
        if (programId != 0) {
            glUseProgram(0);
            glDeleteProgram(programId);
        } else {
            logger.warn("Shader already cleaned up");
        }
    }
}
