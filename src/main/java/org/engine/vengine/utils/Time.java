package org.engine.vengine.utils;

public class Time {
    private static double lastFrameTime;
    private static float deltaTime;

    public static void init() {
        lastFrameTime = getTime();
    }

    public static void update() {
        double currentFrameTime = getTime();
        deltaTime = (float) (currentFrameTime - lastFrameTime);
        lastFrameTime = currentFrameTime;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    private static double getTime() {
        return System.nanoTime() / 1_000_000_000.0;
    }
} 