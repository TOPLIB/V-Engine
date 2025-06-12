package org.engine.vengine.parser;

import org.engine.vengine.mesh.MeshData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectParser {
    /*
    TODO:
        1. In future needs to realize support for quad vertex. "V 1.0 1.0 1.0 1.0"
        2. Needs to realize Objects and Groups
        3. Optional .mtl implementation
     */
    private static final Logger logger = LoggerFactory.getLogger("ObjectParser");

    // Constants
    public static final String COMMENT = "#";
    public static final String VERTEX  = "v";
    public static final String UV      = "vt";
    public static final String NORMAL  = "vn";
    public static final String FACE    = "f";

    public static MeshData parse(String objContent) {
        List<float[]> tempVertices = new ArrayList<>();
        List<float[]> tempUVs = new ArrayList<>();
        List<float[]> tempNormals = new ArrayList<>();

        List<Float> finalVertices = new ArrayList<>();
        List<Float> finalUVs = new ArrayList<>();
        List<Float> finalNormals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        Map<String, Integer> uniqueVertices = new HashMap<>();
        int index = 0;

        String[] lines = objContent.split("\n");

        for (String line : lines) {
            // Пропускаем комментарии и пустые строки
            if (line.trim().isEmpty() || line.startsWith(COMMENT)) continue;

            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case VERTEX:
                    float x = Float.parseFloat(tokens[1]);
                    float y = Float.parseFloat(tokens[2]);
                    float z = Float.parseFloat(tokens[3]);
                    tempVertices.add(new float[]{x, y, z});
                    break;
                case UV:
                    float u = Float.parseFloat(tokens[1]);
                    float v = Float.parseFloat(tokens[2]);
                    tempUVs.add(new float[]{u, v});
                    break;
                case NORMAL:
                    float nx = Float.parseFloat(tokens[1]);
                    float ny = Float.parseFloat(tokens[2]);
                    float nz = Float.parseFloat(tokens[3]);
                    tempNormals.add(new float[]{nx, ny, nz});
                    break;
                case FACE:
                    // Convert quad to triangles
                    int[] vertexIndices = new int[4];
                    int[] uvIndices = new int[4];
                    int[] normalIndices = new int[4];
                    
                    // Parse all vertex data for the quad
                    for (int i = 0; i < 4; i++) {
                        int[] vertexData = parseVertexData(tokens[i + 1]);
                        vertexIndices[i] = vertexData[0];
                        uvIndices[i] = vertexData[1];
                        normalIndices[i] = vertexData[2];
                    }
                    
                    // First triangle
                    processVertex(vertexIndices[0], uvIndices[0], normalIndices[0],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    processVertex(vertexIndices[1], uvIndices[1], normalIndices[1],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    processVertex(vertexIndices[2], uvIndices[2], normalIndices[2],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    
                    // Second triangle
                    processVertex(vertexIndices[0], uvIndices[0], normalIndices[0],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    processVertex(vertexIndices[2], uvIndices[2], normalIndices[2],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    processVertex(vertexIndices[3], uvIndices[3], normalIndices[3],
                            tempVertices, tempUVs, tempNormals, finalVertices, finalUVs, finalNormals, uniqueVertices, indices);
                    break;
            }
        }

        return new MeshData(
                floatListToArray(finalVertices),
                floatListToArray(finalUVs),
                floatListToArray(finalNormals),
                intListToArray(indices)
        );
    }

    private static int[] parseVertexData(String faceData) {
        String[] parts = faceData.split("/");
        int[] vertexData = new int[3];

        vertexData[0] = Integer.parseInt(parts[0]) - 1;  // Vertex index
        vertexData[1] = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) - 1 : -1;  // UV index
        vertexData[2] = parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) - 1 : -1;  // Normal index

        return vertexData;
    }

    private static float[] floatListToArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static int[] intListToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static void processVertex(int vertexIndex, int uvIndex, int normalIndex,
                                    List<float[]> tempVertices, List<float[]> tempUVs, List<float[]> tempNormals,
                                    List<Float> finalVertices, List<Float> finalUVs, List<Float> finalNormals,
                                    Map<String, Integer> uniqueVertices, List<Integer> indices) {
        String key = vertexIndex + "/" + uvIndex + "/" + normalIndex;
        if (!uniqueVertices.containsKey(key)) {
            float[] pos = tempVertices.get(vertexIndex);
            float[] uvData = uvIndex != -1 ? tempUVs.get(uvIndex) : new float[]{0, 0};
            float[] normal = normalIndex != -1 ? tempNormals.get(normalIndex) : new float[]{0, 0, 0};

            finalVertices.add(pos[0]);
            finalVertices.add(pos[1]);
            finalVertices.add(pos[2]);

            finalUVs.add(uvData[0]);
            finalUVs.add(uvData[1]);

            finalNormals.add(normal[0]);
            finalNormals.add(normal[1]);
            finalNormals.add(normal[2]);

            uniqueVertices.put(key, finalVertices.size() / 3 - 1);
        }
        indices.add(uniqueVertices.get(key));
    }
}
