
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

package org.engine.vengine.filesystem;

import org.engine.vengine.render.materials.Material;
import org.engine.vengine.render.shader.Shader;

public class Default {

    public static String VERTEX_SHADER_GLSL = "#version 330 core\n" +
            "layout (location = 0) in vec3 position;\n" +
            "layout (location = 1) in vec3 color;\n" +
            "layout (location = 2) in vec2 texCoord;\n" +
            "\n" +
            "out vec3 ourColor;\n" +
            "out vec2 TexCoord;\n" +
            "\n" +
            "uniform mat4 model;\n" +
            "uniform mat4 view;\n" +
            "uniform mat4 projection;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = projection * view * model * vec4(position, 1.0f);\n" +
            "    ourColor = color;\n" +
            "    TexCoord = vec2(texCoord.x, 1.0 - texCoord.y);\n" +
            "}";

    public static String FRAGMENT_SHADER_GLSL = "#version 330 core\n" +
            "in vec3 ourColor;\n" +
            "in vec2 TexCoord;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "uniform sampler2D ourTexture1;\n" +
            "uniform sampler2D ourTexture2;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = mix(texture(ourTexture2, TexCoord), texture(ourTexture1, TexCoord), 0.5);\n" +
            "}";


    public static Material MATERIAL = new Material(new Shader(VERTEX_SHADER_GLSL, FRAGMENT_SHADER_GLSL));

}
