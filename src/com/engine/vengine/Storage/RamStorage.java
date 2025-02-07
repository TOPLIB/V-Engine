package com.engine.vengine.Storage;

import java.util.HashMap;
import java.util.Map;

public class RamStorage<V> implements DataStorage<V> {
    private final Map<String, V> storage;

    public RamStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public void set(String key, V value) {
        storage.put(key, value);
    }

    @Override
    public V get(String key) {
        return storage.get(key);
    }

    @Override
    public boolean has(String key) {
        return storage.containsKey(key);
    }

    @Override
    public void remove(String key) {
        storage.remove(key);
    }
}
