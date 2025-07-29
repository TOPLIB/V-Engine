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

import org.engine.vengine.mesh.Mesh;
import org.engine.vengine.mesh.MeshData;
import org.engine.vengine.mesh.VertexFormat;
import org.engine.vengine.parser.ObjectParser;
import org.engine.vengine.render.materials.Material;
import org.engine.vengine.render.materials.Texture2D;
import org.engine.vengine.render.shader.Shader;
import org.engine.vengine.core.Window;
import org.engine.vengine.time.Time;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

public class Render {
    private static final Logger logger = LoggerFactory.getLogger("Render");
    private long WID;
    private Window window;
    private Mesh mesh;
    private Material material;

    private final Matrix4f model = new Matrix4f();
    private final Matrix4f view = new Matrix4f().identity().translate(0.0f, 0.0f, -5.0f);
    private final Matrix4f projection = new Matrix4f().perspective((float) Math.toRadians(45.0), (float) 800 / 600, 0.1f, 100.0f);

    public Render(Window window) {
        this.window = window;
    }

    public void initialize(long WID) throws IOException {
        this.WID = WID;

        VertexFormat format = new VertexFormat();
        format.addAttribute(0, 3, GL_FLOAT); // position
        format.addAttribute(1, 3, GL_FLOAT); // color
        format.addAttribute(2, 2, GL_FLOAT); // texCoord
;
        Texture2D texture = new Texture2D(new File("resources/smile.png"), true);

        material = new Material(new Shader(readFromFile(new File("resources/vertex.glsl")), readFromFile(new File("resources/fragment.glsl"))));
        material.setTexture("ourTexture", texture);

        MeshData data = ObjectParser.parse(new String(Files.readAllBytes(Paths.get("resources/cube.obj"))));
        mesh = new Mesh(data, format, material);
    }

    public void startRenderPoll(){
        glEnable(GL_DEPTH_TEST);
        Time time = new Time();
        while (!window.appShouldClose()){
            time.update();
            render();
        }
        window.terminate();
    }

    private void render() {
        window.clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        window.fillClearColor(0f, 0f, 0f, 1.0f);

        material.getShader().use();

        float angle = (float) glfwGetTime() * 1.05f;
        Matrix4f model = new Matrix4f().identity()
            .translate(0.0f, 0.0f, 0.0f)
            .rotate(angle, new Vector3f(0.5f, 1.0f, 0.0f))
            .scale(0.5f);

        material.getShader().setMatrix4f("model", model);
        material.getShader().setMatrix4f("view", view);
        material.getShader().setMatrix4f("projection", projection);

        material.apply();
        mesh.render();
        
        window.swapBuffers();
        window.pollEvents();
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
}
