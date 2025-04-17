
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

package org.engine.vengine.render.materials;

import org.engine.vengine.render.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Material {
    private final Shader shader;
    private final Map<String, Object> uniforms = new HashMap<>();
    private final Map<String, Texture2D> textures = new HashMap<>();

    public Material(Shader shader) {
        this.shader = shader;
    }

    public void set(String name, int value) {
        uniforms.put(name, value);
    }

    public void set(String name, float value) {
        uniforms.put(name, value);
    }

    public void set(String name, Vector3f value) {
        uniforms.put(name, value);
    }

    public void set(String name, Matrix4f value) {
        uniforms.put(name, value);
    }

    public void setTexture(String name, Texture2D texture) {
        textures.put(name, texture);
    }

    public void apply() {
        shader.use();
        for (Map.Entry<String, Object> entry : uniforms.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Integer)
                shader.setInt(name, (int) value);
            else if (value instanceof Float)
                shader.setFloat(name, (float) value);
            else if (value instanceof Vector3f)
                shader.setVector3f(name, (Vector3f) value);
            else if (value instanceof Matrix4f)
                shader.setMatrix4f(name, (Matrix4f) value);
        }

        // bind texture
        int texUnit = 0;
        for (Map.Entry<String, Texture2D> entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + texUnit);
            glBindTexture(GL_TEXTURE_2D, entry.getValue().getTexture());
            shader.setInt(entry.getKey(), texUnit);
            texUnit++;
        }
    }

    public Shader getShader() {
        return shader;
    }
}
