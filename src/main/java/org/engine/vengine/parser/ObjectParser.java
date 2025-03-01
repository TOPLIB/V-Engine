package org.engine.vengine.parser;

import org.engine.vengine.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectParser {

    public static final String COMMENT             = "#";
    public static final String VERTEX              = "v";
    public static final String TEXTURE_COORDINATE  = "vt";
    public static final String NORMAL              = "vn";
    public static final String FACE                = "f";

    private File file;
    private List<String> lines = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger("ObjectParser");

    private List<Vertex> vertices = new ArrayList<>();
    private List<TextureCoordinate> textureCoordinates = new ArrayList<>();
    private List<Normal> normals = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();

    public ObjectParser(File file) throws IOException {
        this.file = file;
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = bf.readLine()) != null ){
                lines.add(line);
            }
        }
    }

    public void parse(){
        for(String line : lines) {
            line = line.trim();
            if(line.isEmpty() || line.startsWith(COMMENT)) {
                continue;
            }

            String[] tokens = line.split("\\s+");
            String type = tokens[0];

            switch(type) {
                case VERTEX:
                    if(tokens.length >= 4) {
                        try {
                            float x = Float.parseFloat(tokens[1]);
                            float y = Float.parseFloat(tokens[2]);
                            float z = Float.parseFloat(tokens[3]);
                            vertices.add(new Vertex(x, y, z));
                        } catch (NumberFormatException e) {
                            logger.error("Vertex parsing error: " + line, e);
                        }
                    }
                    break;
                case TEXTURE_COORDINATE:
                    if(tokens.length >= 3) {
                        try {
                            float u = Float.parseFloat(tokens[1]);
                            float v = Float.parseFloat(tokens[2]);
                            textureCoordinates.add(new TextureCoordinate(u, v));
                        } catch (NumberFormatException e) {
                            logger.error("Texture coordinate parsing error: " + line, e);
                        }
                    }
                    break;
                case NORMAL:
                    if(tokens.length >= 4) {
                        try {
                            float nx = Float.parseFloat(tokens[1]);
                            float ny = Float.parseFloat(tokens[2]);
                            float nz = Float.parseFloat(tokens[3]);
                            normals.add(new Normal(nx, ny, nz));
                        } catch (NumberFormatException e) {
                            logger.error("Error parsing normal: " + line, e);
                        }
                    }
                    break;
                case FACE:
                    Face face = new Face();
                    for (int i = 1; i < tokens.length; i++) {
                        String[] parts = tokens[i].split("/");
                        try {
                            int vertexIndex = Integer.parseInt(parts[0]);
                            Integer texCordIndex = (parts.length > 1 && !parts[1].isEmpty()) ? Integer.parseInt(parts[1]) : null;
                            Integer normalIndex = (parts.length > 2 && !parts[2].isEmpty()) ? Integer.parseInt(parts[2]) : null;
                            face.addFaceElement(new FaceElement(vertexIndex, texCordIndex, normalIndex));
                        } catch (NumberFormatException e) {
                            logger.error("Cannot parse border " + tokens[i], e);
                        }
                    }
                    faces.add(face);
                    break;
                default:
                    logger.info("Unknown type: " + type);
                    break;
            }
        }
    }

    public Vertex[] getVertices() {
        return vertices.toArray(new Vertex[0]);
    }

    public TextureCoordinate[] getTextureCoordinates() {
        return textureCoordinates.toArray(new TextureCoordinate[0]);
    }

    public Normal[] getNormals() {
        return normals.toArray(new Normal[0]);
    }

    public Face[] getFaces() {
        return faces.toArray(new Face[0]);
    }

}
