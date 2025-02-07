package com.engine.vengine.Storage;

public interface DataStorage<V> {
    void set(String key, V value);
    V get(String key);
    boolean has(String key);
    void remove(String key);
}
