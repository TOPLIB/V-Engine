package org.engine.vengine.test;

import org.engine.vengine.core.Engine;
import org.engine.vengine.utils.Logger;

public class Test {
    private static final Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) {
        logger.info("Running new Engine Application");
        Engine engine = new Engine();
        engine.start();
    }
} 