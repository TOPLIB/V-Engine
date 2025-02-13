package org.engine.vengine;

import org.engine.vengine.debug.LogLevel;
import org.engine.vengine.debug.Logger;
import org.engine.vengine.render.window.Window;

public class Test {
    public static void main(String[] args) {
        Window window = new Window("Tests", 800, 600);
        Thread thread = new Thread(window);
        thread.start();
        Logger.print(thread.getState().toString());
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }
}
