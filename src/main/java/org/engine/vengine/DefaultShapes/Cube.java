package org.engine.vengine.DefaultShapes;

import org.engine.vengine.Render.Renderable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Cube implements Renderable {
    private int vaoID;
    private int vboID;
    private int eboID;
    private float rotationAngle = 0.0f;
    private long lastUpdateTime; // Для хранения времени последнего обновления

    // Цвета для каждой из 6 граней
    private final float[][] faceColors = {
            {1.0f, 0.0f, 0.0f}, // Red
            {0.0f, 1.0f, 0.0f}, // Green
            {0.0f, 0.0f, 1.0f}, // Blue
            {1.0f, 1.0f, 0.0f}, // Yellow
            {1.0f, 1.0f, 1.0f}, // White
            {0.0f, 1.0f, 1.0f}  // White-Blue
    };

    public Cube() {
        lastUpdateTime = System.nanoTime(); // Инициализация времени

        // Вершины куба (8 вершин)
        float[] vertices = {
                // Front (Z+)
                -0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                // Back (Z-)
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f
        };

        // Индексы для треугольников (6 граней × 2 треугольника × 3 вершины)
        int[] indices = {
                // Front
                0, 1, 2,  2, 3, 0,
                // Right (X+)
                1, 5, 6,  6, 2, 1,
                // Back
                5, 4, 7,  7, 6, 5,
                // Left (X-)
                4, 0, 3,  3, 7, 4,
                // Top (Y+)
                3, 2, 6,  6, 7, 3,
                // Down (Y-)
                4, 5, 1,  1, 0, 4
        };

        // Настройка VAO/VBO
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer vertexBuffer = org.lwjgl.BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        GL15.glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        eboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        IntBuffer indexBuffer = org.lwjgl.BufferUtils.createIntBuffer(indices.length);
        indexBuffer.put(indices).flip();
        GL15.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        GL30.glBindVertexArray(0);
    }

    @Override
    public void render() {
        // Расчет дельта-времени
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0f;
        lastUpdateTime = currentTime;

        rotationAngle += 90.0f * deltaTime;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glRotatef(rotationAngle, 1.0f, 1.0f, 1.0f);

        GL30.glBindVertexArray(vaoID);
        GL20.glEnableVertexAttribArray(0);

        for(int i = 0; i < 6; i++) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer colorBuffer = stack.floats(faceColors[i]);
                GL11.glColor3fv(colorBuffer);
            }
            GL11.glDrawElements(
                    GL11.GL_TRIANGLES,
                    6,
                    GL11.GL_UNSIGNED_INT,
                    (long) i * 6 * Integer.BYTES
            );
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void cleanup() {
        GL30.glDeleteVertexArrays(vaoID);
        GL15.glDeleteBuffers(vboID);
        GL15.glDeleteBuffers(eboID);
    }
}