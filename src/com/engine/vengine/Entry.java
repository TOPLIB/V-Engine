package com.engine.vengine;

import com.engine.vengine.Storage.RamStorage;

public class Entry {
    public static void main(String[] args){
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--debug")) {
                System.out.println("Debug mode enabled");
            }
        }
    }
}
