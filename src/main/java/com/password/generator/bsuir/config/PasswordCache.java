package com.password.generator.bsuir.config;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordCache {

    private final Map<Long, String> cache = new HashMap<>();

    public void put(Long id, String password) {
        cache.put(id, password);
    }

    public String get(Long id) {
        return cache.get(id);
    }

    public boolean contains(Long id) {
        return cache.containsKey(id);
    }

    public void remove(Long id) {
        cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }
}
