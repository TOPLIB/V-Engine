package org.engine.vengine.Render;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {
    private List<Renderable> renderables;

    public RenderQueue() {
        renderables = new ArrayList<>();
    }

    public void add(Renderable renderable) {
        renderables.add(renderable);
    }

    public void remove(Renderable renderable) {
        renderables.remove(renderable);
    }

    public void renderAll() {
        for (Renderable renderable : renderables) {
            renderable.render();
        }
    }

    public void cleanup() {
        for (Renderable renderable : renderables) {
            renderable.cleanup();
        }
        renderables.clear();
    }
}
