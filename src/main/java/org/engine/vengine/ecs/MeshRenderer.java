package org.engine.vengine.ecs;

import org.engine.vengine.render.Renderer;

/**
 * Simple component that delegates drawing to the engine renderer.
 * This is only a placeholder and does not implement a full feature set.
 */
public class MeshRenderer extends Component {
    private Renderer renderer;

    public MeshRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        if (renderer != null) {
            renderer.render();
        }
    }
}
