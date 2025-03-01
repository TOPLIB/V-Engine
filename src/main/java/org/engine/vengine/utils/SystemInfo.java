
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

package org.engine.vengine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SystemInfo {

    private static String OPENGL_VERSION;
    private static String GPU_VENDOR;
    private static String GPU_RENDERER;
    private static String GLFW_VERSION;
    private static String OS_NAME;
    private static String OS_VERSION;
    private static String CPU_ARCHITECTURE;
    private static int CPU_PHYSICAL_CORES;
    private static int CPU_LOGICAL_CORES;

    public static void initialize(){
        OPENGL_VERSION = glGetString(GL_VERSION);
        GPU_VENDOR = glGetString(GL_VENDOR);
        GPU_RENDERER = glGetString(GL_RENDERER);
        GLFW_VERSION = glfwGetVersionString();
        OS_NAME = System.getProperty("os.name");
        OS_VERSION = System.getProperty("os.version");
        CPU_ARCHITECTURE = System.getProperty("os.arch");
        CPU_PHYSICAL_CORES = getPhysicalCoresWindows();
        CPU_LOGICAL_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static int getPhysicalCoresWindows() {
        try {
            String command = "wmic cpu get NumberOfCores";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> result = new ArrayList<>();
            while (((line = reader.readLine()) != null)) {
                if(line.isEmpty()) continue;
                result.add(line);
            }

            result.remove(0);
            process.waitFor();
            return Integer.parseInt(String.valueOf(result.get(0).toCharArray()[0]));
        } catch (Exception ignored) {}
        return -1;
    }

    public static String OPENGL_VERSION() {
        return OPENGL_VERSION;
    }
    public static String GPU_VENDOR() {
        return GPU_VENDOR;
    }
    public static String GPU_RENDERER() {
        return GPU_RENDERER;
    }
    public static String GLFW_VERSION() {
        return GLFW_VERSION;
    }
    public static String OS_NAME() {
        return OS_NAME;
    }
    public static String OS_VERSION() {
        return OS_VERSION;
    }
    public static String CPU_ARCHITECTURE() {
        return CPU_ARCHITECTURE;
    }
    public static int CPU_PHYSICAL_CORES() {
        return CPU_PHYSICAL_CORES;
    }
}
