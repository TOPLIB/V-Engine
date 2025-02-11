package org.engine.vengine.Debug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void print(String message) { print(LogLevel.INFO, message); }

    public static void print(LogLevel level, String message){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = currentTime.format(formatter);

        System.out.printf("%n[%s - %s] %s", level, time, message);
    }
}
