package org.engine.vengine.Render;

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

        this.xScale = 0;
        this.yScale = 0;
        this.zScale = 0;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getzPos() {
        return zPos;
    }

    public void setzPos(float zPos) {
        this.zPos = zPos;
    }

    public float getxRot() {
        return xRot;
    }

    public void setxRot(float xRot) {
        this.xRot = xRot;
    }

    public float getyRot() {
        return yRot;
    }

    public void setyRot(float yRot) {
        this.yRot = yRot;
    }

    public float getzRot() {
        return zRot;
    }

    public void setzRot(float zRot) {
        this.zRot = zRot;
    }

    public float getxScale() {
        return xScale;
    }

    public void setxScale(float xScale) {
        this.xScale = xScale;
    }

    public float getyScale() {
        return yScale;
    }

    public void setyScale(float yScale) {
        this.yScale = yScale;
    }

    public float getzScale() {
        return zScale;
    }

    public void setzScale(float zScale) {
        this.zScale = zScale;
    }
}
