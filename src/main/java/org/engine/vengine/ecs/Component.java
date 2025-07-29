package org.engine.vengine.ecs;

public abstract class Component {
    private GameObject gameObject;

    void setGameObject(GameObject obj) {
        this.gameObject = obj;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {}

    public void update(float deltaTime) {}
}
