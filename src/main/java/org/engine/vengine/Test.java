package org.engine.vengine;

import org.engine.vengine.parser.ObjectParser;
import org.engine.vengine.render.window.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.engine.vengine.mesh.MeshData;


public class Test {
    private static final Logger logger = LoggerFactory.getLogger("Test");

    public static void main(String[] args) throws IOException {
        logger.debug("Running new Engine Application");

        String object = new String(Files.readAllBytes(Paths.get("resources/cube.obj")));

        MeshData meshData = ObjectParser.parse(object);

        // Получение данных MeshData
        float[] vertices = meshData.getVertices();
        float[] uvs = meshData.getUVs();   // Если UV-координаты присутствуют
        float[] normals = meshData.getNormals();
        int[] indices = meshData.getIndices();

        // Вывод полученных данных
        System.out.println("Vertices:");
        for (int i = 0; i < vertices.length; i += 3) {
            System.out.printf("Vertex %d: (%f, %f, %f)%n", i / 3, vertices[i], vertices[i + 1], vertices[i + 2]);
        }

        System.out.println("\nUVs:");
        if (uvs != null) {
            for (int i = 0; i < uvs.length; i += 2) {
                System.out.printf("UV %d: (%f, %f)%n", i / 2, uvs[i], uvs[i + 1]);
            }
        }

        System.out.println("\nNormals:");
        for (int i = 0; i < normals.length; i += 3) {
            System.out.printf("Normal %d: (%f, %f, %f)%n", i / 3, normals[i], normals[i + 1], normals[i + 2]);
        }

        System.out.println("\nIndices:");
        for (int i = 0; i < indices.length; i += 3) {
            System.out.printf("Face %d: (%d, %d, %d)%n", i / 3, indices[i], indices[i + 1], indices[i + 2]);
        }

        Window window = new Window();
        window.initialize("Tests", 800, 800);
        window.startRender();

    }
}
