package org.engine.vengine.Render;

public class Point {
    private double X;
    private double Y;
    private double Z;

    public Point(double x, double y, double z) {
        X = x;
        Y = y;
        Z = z;
    }

    public double X() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double Y() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double Z() {
        return Z;
    }

    public void setZ(double z) {
        Z = z;
    }
}
