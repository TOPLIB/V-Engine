
/*
 * V-Engine
 * Copyright (C) 2025
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.engine.vengine.debug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void print(String message) { print(LogLevel.INFO, message); }

    public static void print(LogLevel level, String message){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = currentTime.format(formatter);

        System.out.printf("%n[%s] [%s] %s", level, time, message);
    }

    public static void exit(int status) {
        print("The program caused an exit with the status: " + status);
        System.exit(-1);
    }

    public static void error(LogLevel level, String message){
        switch (level){
            case WARNING:
                print(level, String.format("An error was occurred during the execution of the program: '%s'. The execution of the program continues. " +
                        "If this is repeated too often write about the problem in more detail: https://github.com/toplib/v-engine/issues", message));
                break;
            case SEVERE:
                print(level, String.format("An error was occurred during the execution of the program: '%s'. " +
                        "The execution of the program will be discontinued. " +
                        "If this is repeated too often write about the problem in more detail: https://github.com/toplib/v-engine/issues", message));
                exit(-1);
                break;
            case CRITICAL:
                print(level, String.format("A critical error occurred, the execution of the program is further impossible, " +
                        "the request will turn 'https://github.com/toplib/v-engine/issues' for help, or report on the bug. Error code: '%s'", message));
                exit(-1);
                break;
            default:
                print(level, String.format("An error was occurred '%s', please inform you about it 'https://github.com/toplib/v-engine/issues'" , message));
                break;
        }
    }

}
