package org.engine.vengine.Render.Materials;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Material {
    private float[] ambientColor;
    private float[] diffuseColor;
    private float[] specularColor;
    private float shininess;
    private int textureID;

    public Material(float[] ambientColor, float[] diffuseColor, float[] specularColor, float shininess) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;
        this.textureID = -1; // No texture by default
    }

    public Material(float[] ambientColor, float[] diffuseColor, float[] specularColor, float shininess, int textureID) {
        this(ambientColor, diffuseColor, specularColor, shininess);
        this.textureID = textureID;
    }

    public void apply() {
        GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, ambientColor);
        GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, diffuseColor);
        GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, specularColor);
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, shininess);

        if (textureID != -1) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        }
    }

    public float[] getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(float[] ambientColor) {
        this.ambientColor = ambientColor;
    }

    public float[] getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(float[] diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public float[] getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(float[] specularColor) {
        this.specularColor = specularColor;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
