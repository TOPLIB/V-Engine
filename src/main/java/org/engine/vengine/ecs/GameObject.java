package org.engine.vengine.ecs;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private final List<Component> components = new ArrayList<>();
    private final Transform transform = new Transform();

    public GameObject() {
        addComponent(transform);
    }

    public <T extends Component> T addComponent(T component) {
        component.setGameObject(this);
        components.add(component);
        component.start();
        return component;
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component c : components) {
            if (type.isInstance(c)) {
                return type.cast(c);
            }
        }
        return null;
    }

    public void update(float deltaTime) {
        for (Component c : components) {
            c.update(deltaTime);
        }
    }

    public Transform getTransform() {
        return transform;
    }
}
