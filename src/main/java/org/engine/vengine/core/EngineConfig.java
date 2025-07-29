package org.engine.vengine.core;

/**
 * Simple configuration object for Engine parameters.
 */
public class EngineConfig {
    private final String title;
    private final int width;
    private final int height;

    public EngineConfig(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Creates configuration with default values.
     */
    public static EngineConfig defaultConfig() {
        return new EngineConfig("V-Engine", 800, 600);
    }
}
