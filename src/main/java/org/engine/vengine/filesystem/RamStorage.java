
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

package org.engine.vengine.filesystem;

import java.util.HashMap;

public class RamStorage {
    private static HashMap<String, Object> ram = new HashMap<>();

    public static Object put(String key, Object value){
        ram.put(key, value);
        return value;
    }
    public static void remove(String key){
        ram.remove(key);
    }
    public static Object get(String key){
        return ram.get(key);
    }
    public static Object set(String key, Object value){
        ram.remove(key);
        ram.put(key, value);
        return ram.get(key);
    }
    public static boolean has(String key, Object value){
        return ram.containsKey(key);
    }

}
