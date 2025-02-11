package org.engine.vengine.Utils;

public class Transform {

    private float xPos;
    private float yPos;
    private float zPos;

    private float xRot;
    private float yRot;
    private float zRot;

    private float xScale;
    private float yScale;
    private float zScale;

    public Transform() {
        this.xPos = 0;
        this.yPos = 0;
        this.zPos = 0;

        this.xRot = 0;
        this.yRot = 0;
        this.zRot = 0;

        this.xScale = 1;
        this.yScale = 1;
        this.zScale = 1;
    }

    public Transform(float xPos, float yPos, float zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xRot = 0;
        this.yRot = 0;
        this.zRot = 0;

        this.xScale = 1;
        this.yScale = 1;
        this.zScale = 1;
    }


    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getZPos() {
        return zPos;
    }

    public void setZPos(float zPos) {
        this.zPos = zPos;
    }

    public float getXRot() {
        return xRot;
    }

    public void setXRot(float xRot) {
        this.xRot = xRot;
    }

    public float getYRot() {
        return yRot;
    }

    public void setYRot(float yRot) {
        this.yRot = yRot;
    }

    public float getZRot() {
        return zRot;
    }

    public void setZRot(float zRot) {
        this.zRot = zRot;
    }

    public float getXScale() {
        return xScale;
    }

    public void setXScale(float xScale) {
        this.xScale = xScale;
    }

    public float getYScale() {
        return yScale;
    }

    public void setYScale(float yScale) {
        this.yScale = yScale;
    }

    public float getZScale() {
        return zScale;
    }

    public void setZScale(float zScale) {
        this.zScale = zScale;
    }

    @Override
    public String toString() {
        return "Transform{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                ", zPos=" + zPos +
                ", xRot=" + xRot +
                ", yRot=" + yRot +
                ", zRot=" + zRot +
                ", xScale=" + xScale +
                ", yScale=" + yScale +
                ", zScale=" + zScale +
                '}';
    }
}
