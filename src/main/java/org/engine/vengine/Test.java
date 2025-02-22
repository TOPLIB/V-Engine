package org.engine.vengine;

import org.engine.vengine.debug.Logger;
import org.engine.vengine.filesystem.ENV;
import org.engine.vengine.render.window.Window;

public class Test {
    public static void main(String[] args) {
        ENV.set("app_should_close", false);






        Window window = new Window("Tests", 800, 800);
        Thread thread = new Thread(window);
        thread.start();
        Logger.print(thread.getState().toString());


//        if (thread.isAlive()) {
//            thread.interrupt();
//        }
    }
}
