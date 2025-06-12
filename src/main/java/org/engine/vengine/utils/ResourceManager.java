package org.engine.vengine.utils;

import org.engine.vengine.render.materials.Texture2D;
import org.engine.vengine.render.shader.Shader;
import org.engine.vengine.mesh.Mesh;
import org.engine.vengine.mesh.MeshData;
import org.engine.vengine.mesh.VertexFormat;
import org.engine.vengine.parser.ObjectParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Logger logger = Logger.getLogger(ResourceManager.class);
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture2D> textures = new HashMap<>();
    private static final Map<String, Mesh> meshes = new HashMap<>();

    public static void init() {
        logger.info("Resource manager initialized");
    }

    public static Shader loadShader(String name, String vertexPath, String fragmentPath) {
        if (shaders.containsKey(name)) {
            return shaders.get(name);
        }

        try {
            String vertexSource = new String(Files.readAllBytes(Paths.get(vertexPath)));
            String fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentPath)));
            Shader shader = new Shader(vertexSource, fragmentSource);
            shaders.put(name, shader);
            return shader;
        } catch (IOException e) {
            logger.error("Failed to load shader: " + name, e);
            throw new RuntimeException("Failed to load shader: " + name, e);
        }
    }

    public static Texture2D loadTexture(String name, String path) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }

        try {
            Texture2D texture = new Texture2D(new File(path), false);
            textures.put(name, texture);
            return texture;
        } catch (Exception e) {
            logger.error("Failed to load texture: " + name, e);
            throw new RuntimeException("Failed to load texture: " + name, e);
        }
    }

    public static Mesh loadMesh(String name, String path) {
        if (meshes.containsKey(name)) {
            return meshes.get(name);
        }

        try {
            String objContent = new String(Files.readAllBytes(Paths.get(path)));
            MeshData meshData = ObjectParser.parse(objContent);
            Mesh mesh = new Mesh(meshData, new VertexFormat());
            meshes.put(name, mesh);
            return mesh;
        } catch (IOException e) {
            logger.error("Failed to load mesh: " + name, e);
            throw new RuntimeException("Failed to load mesh: " + name, e);
        }
    }

    public static Shader getShader(String name) {
        if (!shaders.containsKey(name)) {
            throw new RuntimeException("Shader not found: " + name);
        }
        return shaders.get(name);
    }

    public static Texture2D getTexture(String name) {
        if (!textures.containsKey(name)) {
            throw new RuntimeException("Texture not found: " + name);
        }
        return textures.get(name);
    }

    public static Mesh getMesh(String name) {
        if (!meshes.containsKey(name)) {
            throw new RuntimeException("Mesh not found: " + name);
        }
        return meshes.get(name);
    }

    public static void cleanup() {
        shaders.values().forEach(Shader::cleanup);
        textures.values().forEach(Texture2D::cleanup);
        meshes.values().forEach(Mesh::cleanup);
        shaders.clear();
        textures.clear();
        meshes.clear();
        logger.info("Resource manager cleaned up");
    }
} 