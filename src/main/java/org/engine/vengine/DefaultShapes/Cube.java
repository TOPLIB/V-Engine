package org.engine.vengine.DefaultShapes;

import org.engine.vengine.Engine.GameObject;
import org.engine.vengine.Render.Materials.Material;
import org.engine.vengine.Render.Renderable;
import org.engine.vengine.Scripting.Script;
import org.engine.vengine.Scripting.ScriptableObject;
import org.engine.vengine.Utils.Transform;

import java.util.List;

public class Cube implements Renderable, GameObject, ScriptableObject {

    private Transform transform;
    private Material material;

    public Cube(Transform transform, Material material) {
        this.transform = transform;
        this.material = material;
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public Transform setTransform(Transform transform) {
        this.transform = transform;
        return transform;
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean setActive(Boolean status) {
        return false;
    }

    @Override
    public List<Script> getScripts() {
        return List.of();
    }

    @Override
    public void addScript(Script script) {

    }

    @Override
    public Script getScriptById(int scriptId) {
        return null;
    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void render() {
    }

    @Override
    public void cleanup() {

    }
}