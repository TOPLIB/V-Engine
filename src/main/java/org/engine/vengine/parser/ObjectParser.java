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
                    for (int i = 1; i < tokens.length - 2; i++) {
                        int[][] faceVerts = new int[][]{
                                parseVertexData(tokens[1]),
                                parseVertexData(tokens[i + 1]),
                                parseVertexData(tokens[i + 2])
                        };

                        for (int[] fv : faceVerts) {
                            String key = fv[0] + "/" + fv[1] + "/" + fv[2];
                            if (!uniqueVertices.containsKey(key)) {
                                float[] pos = tempVertices.get(fv[0]);
                                float[] uvData = fv[1] != -1 ? tempUVs.get(fv[1]) : new float[]{0, 0};
                                float[] normal = fv[2] != -1 ? tempNormals.get(fv[2]) : new float[]{0, 0, 0};

                                finalVertices.add(pos[0]);
                                finalVertices.add(pos[1]);
                                finalVertices.add(pos[2]);

                                finalUVs.add(uvData[0]);
                                finalUVs.add(uvData[1]);

                                finalNormals.add(normal[0]);
                                finalNormals.add(normal[1]);
                                finalNormals.add(normal[2]);

                                uniqueVertices.put(key, index++);
                            }

                            indices.add(uniqueVertices.get(key));
                        }
                    }
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
}
