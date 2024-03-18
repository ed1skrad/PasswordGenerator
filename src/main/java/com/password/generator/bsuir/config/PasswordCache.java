package com.password.generator.bsuir.config;

import com.password.generator.bsuir.model.GeneratedPassword;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class PasswordCache {

    private final ConcurrentMap<Long, String> cache = new ConcurrentHashMap<>();

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

    public List<GeneratedPassword> getAllCachedPasswords() {
        return cache.entrySet().stream()
                .map(entry -> new GeneratedPassword(entry.getKey(), entry.getValue()))
                .toList();
    }
}
