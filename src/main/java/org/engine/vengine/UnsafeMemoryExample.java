
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

package org.engine.vengine;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeMemoryExample {
    public static void main(String[] args) {
        Unsafe unsafe = getUnsafe();

        long size = 8; // Выделяем 8 байт (для long)
        System.out.println(size);
        long address = unsafe.allocateMemory(size); // Выделение памяти
        System.out.println(address);

        try {
            unsafe.putLong(address, 123456789L); // Записываем значение
            long value = unsafe.getLong(address); // Читаем значение

            System.out.println("Записанное значение: " + value);
        } finally {
            unsafe.freeMemory(address); // Освобождаем память
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить Unsafe", e);
        }
    }
}
