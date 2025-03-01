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

import org.engine.vengine.Test;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final File file;
    private ByteBuffer image;
    private int width;
    private int height;
    private int channels;

    int texture;

    public Texture(File file, boolean flipVertically) {
        this.file = file;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            IntBuffer channelsBuffer = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(flipVertically);
            image = STBImage.stbi_load(file.getPath(), widthBuffer, heightBuffer, channelsBuffer, 0);

            if (image == null) {
                logger.warn("Cannot load image: {}", file.getPath());
                return;
            }

            this.width = widthBuffer.get(0);
            this.height = heightBuffer.get(0);
            this.channels = channelsBuffer.get(0);
        }
        registerTexture();
    }

    private void registerTexture() {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        int format = (channels == 4) ? GL_RGBA : GL_RGB;
        int internalFormat = (channels == 4) ? GL_RGBA8 : GL_RGB8;

        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, image);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glGenerateMipmap(GL_TEXTURE_2D);

        STBImage.stbi_image_free(image);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanup() {
        if (texture != 0) {
            glDeleteTextures(texture);
        }
    }

    // Getters
    public int getTexture() {
        return texture;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getChannels() {
        return channels;
    }
    public ByteBuffer getImage() {
        return image;
    }
}
