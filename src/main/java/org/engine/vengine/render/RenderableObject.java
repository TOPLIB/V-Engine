package org.engine.vengine.render;

import org.engine.vengine.render.materials.Texture;
import org.engine.vengine.render.shader.Shader;
import org.lwjgl.opengl.GL11;

import java.io.File;

import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderableObject {

    private Texture texture1;
    private Shader shader;
    private int VBO;
    private int VAO;
    private int EBO;
    private Texture texture;

    public RenderableObject() {
        shader = new Shader(new File("resources/vertex.glsl"), new File("resources/fragment.glsl"));

        float[] vertices = {
                // Позиции           // Цвета             // Текстурные координаты
                0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // Верхний правый
                0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // Нижний правый
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // Нижний левый
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // Верхний левый
        };

        int[] indices = {
                0, 1, 2,  // Первый треугольник
                2, 3, 0   // Второй треугольник
        };

        // Генерация VAO
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        // Генерация VBO
        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // Настройка атрибутов вершин
        int stride = 8 * Float.BYTES; // 3 позиции + 3 цвета + 2 текстурные координаты

        // Позиции
        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        // Цвета
        glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        // Текстурные координаты
        glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, stride, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        // Генерация EBO
        EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        texture = new Texture(new File("resources/texture.png"), true);
        texture1 = new Texture(new File("resources/smile.png"), true);

        // Отвязка VAO
        glBindVertexArray(0);
    }

    public void render() {
        shader.use();

        // Активируем первый текстурный юнит и привязываем первую текстуру
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTexture());
        shader.setInt("ourTexture1", 0);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, texture1.getTexture());
        shader.setInt("ourTexture2", 1);


        // Рисуем объект
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }


    public void cleanup() {
        glDeleteBuffers(VBO);
        glDeleteBuffers(EBO);
        glDeleteVertexArrays(VAO);
        shader.cleanup();
    }
}
