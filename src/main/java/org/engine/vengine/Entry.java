package org.engine.vengine;

import org.engine.vengine.Debug.LogLevel;
import org.engine.vengine.Debug.Logger;

public class Entry {
    public static void main(String[] args){
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--debug")) {
                Logger.print(LogLevel.INFO, "The debugging mode was enabled if you want to disable it then, remove it \"--debug\" argument");
                Logger.print(LogLevel.INFO, "LWJGL Version: 3.3.6");
                Logger.print(LogLevel.INFO, "OpenGL Version: 4.6.0 NVIDIA 388.13");
            }
        }
    }
}
