package org.engine.vengine.Engine;

import java.util.ArrayList;

public class Scene {
    private ArrayList<GameObject> hierarchy;
    private int sceneId;

    public Scene(int sceneId) {
        this.sceneId = sceneId;
    }

    public ArrayList<GameObject> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(ArrayList<GameObject> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public int getSceneId() {
        return sceneId;
    }
}
