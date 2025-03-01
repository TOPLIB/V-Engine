package org.engine.vengine;

import org.engine.vengine.modules.Transform3d;
import org.engine.vengine.parser.ObjectParser;
import org.engine.vengine.render.window.Window;
import org.engine.vengine.utils.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger("Test");
    public static void main(String[] args) {
        logger.debug("Running new Engine Application");

        UnsafeMemoryExample.main(null);



        Window window = new Window();
        window.initialize("Tests", 800, 800);
        window.startRender();

    }
}
