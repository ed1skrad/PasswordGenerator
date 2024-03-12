package com.password.generator.bsuir.config;

import com.password.generator.bsuir.model.GeneratedPassword;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PasswordCache {

    private final ConcurrentMap<Long, String> cache = new ConcurrentHashMap<>();

    private List<GeneratedPassword> allGeneratedPasswords = null;

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

    public List<GeneratedPassword> getAllGeneratedPasswords() {
        return allGeneratedPasswords;
    }

    public void putAllGeneratedPasswords(List<GeneratedPassword> generatedPasswords) {
        allGeneratedPasswords = generatedPasswords;
        generatedPasswords.forEach(password -> cache.put(password.getId(), password.getPassword()));
    }
}

