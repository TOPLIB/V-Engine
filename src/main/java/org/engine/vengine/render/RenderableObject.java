package org.engine.vengine.render;

import org.engine.vengine.render.materials.Texture;
import org.engine.vengine.render.shader.Shader;
import org.engine.vengine.time.Time;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderableObject {

    private float far = 100.0f;
    private float near = 0.1f;
    private float aspectRatio = 800.0f / 600.0f;
    private float fov = (float) Math.toRadians(80.0f);

    private final Shader shader;
    private final int VAO, VBO, EBO;
    private final Texture texture, texture1;
    private final Matrix4f model = new Matrix4f();
    private final Matrix4f view = new Matrix4f().identity().translate(0.0f, 0.0f, -10.0f);
    private final Matrix4f projection = new Matrix4f().perspective((float) Math.toRadians(45.0), aspectRatio, 0.1f, 100.0f);

    public RenderableObject() {
        shader = new Shader(new File("resources/vertex.glsl"), new File("resources/fragment.glsl"));

        float[] vertices = {
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };

        int[] indices = { 0, 1, 2, 2, 3, 0 };

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;

        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(2);


        texture = new Texture(new File("resources/texture.png"), true);
        texture1 = new Texture(new File("resources/smile.png"), true);

        glBindVertexArray(0);
    }

    public void render() {
        shader.use();

        // Обновление матрицы модели с вращением
        float angle = (float) glfwGetTime() * 1.05f;  // Вращение на основе времени
        model.identity().rotate(angle, new Vector3f(0.5f, 1.0f, 0.0f));

        // Обновление шейдерных матриц
        shader.setMatrix4f("model", model);
        shader.setMatrix4f("view", view);  // Камера в позиции (0.0f, 0.0f, -3.0f)
        shader.setMatrix4f("projection", projection);

        // Привязка текстур
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTexture());
        shader.setInt("ourTexture1", 0);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, texture1.getTexture());
        shader.setInt("ourTexture2", 1);

        // Рисуем объект
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 36);  // Используем правильное количество индексов
        glBindVertexArray(0);
    }



    public void cleanup() {
        glDeleteBuffers(VBO);
        glDeleteBuffers(EBO);
        glDeleteVertexArrays(VAO);
        shader.cleanup();
    }

    public Matrix4f getModel() {
        return new Matrix4f(model);
    }

    public void setTransformation(Matrix4f transformation) {
        this.model.set(transformation);
    }

    public Matrix4f getView() {
        return new Matrix4f(view);
    }

    public void setView(Matrix4f view) {
        this.view.set(view);
    }

    public Matrix4f getProjection() {
        return new Matrix4f(projection);
    }

    public void setProjection(Matrix4f projection) {
        this.projection.set(projection);
    }
}
