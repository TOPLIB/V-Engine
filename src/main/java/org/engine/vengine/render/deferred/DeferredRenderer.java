package org.engine.vengine.render.deferred;

import org.engine.vengine.core.Window;

/**
 * Placeholder for a deferred rendering pipeline.
 * This implementation is minimal and intended for future expansion.
 */
public class DeferredRenderer {
    private final Window window;

    public DeferredRenderer(Window window) {
        this.window = window;
    }

    public void init() {
        // TODO: setup G-Buffer and load PBR shaders
    }

    public void render() {
        // TODO: implement geometry and lighting passes
    }
}
