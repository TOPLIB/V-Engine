package org.engine.vengine;

import org.engine.vengine.filesystem.Env;
import org.engine.vengine.render.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
        logger.debug("Running new Engine Application");
        Env.set("app_should_close", false);

        Window window = new Window("Tests", 800, 800);
        Thread thread = new Thread(window);
        thread.start();
        logger.info(thread.getState().toString());

        logger.debug("a");

//        if (thread.isAlive()) {
//            thread.interrupt();
//        }
    }
}
