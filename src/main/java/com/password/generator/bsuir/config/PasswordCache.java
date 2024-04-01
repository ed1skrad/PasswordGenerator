package com.password.generator.bsuir.config;

import com.password.generator.bsuir.model.GeneratedPassword;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;
/**
 * Class for caching generated passwords.
 */

@Component
public class PasswordCache {

    private final ConcurrentMap<Long, String> cache = new ConcurrentHashMap<>();

    /**
     * Adds a generated password to the cache.
     *
     * @param id the ID of the generated password
     * @param password the generated password
     */
    public void put(Long id, String password) {
        cache.put(id, password);
    }

    /**
     * Retrieves a generated password from the cache.
     *
     * @param id the ID of the generated password
     * @return the generated password, or null if not found
     */
    public String get(Long id) {
        return cache.get(id);
    }

    /**
     * Checks if the cache contains a generated password with the given ID.
     *
     * @param id the ID of the generated password
     * @return true if the cache contains the generated password, false otherwise
     */
    public boolean contains(Long id) {
        return cache.containsKey(id);
    }

    /**
     * Removes a generated password from the cache.
     *
     * @param id the ID of the generated password
     */
    public void remove(Long id) {
        cache.remove(id);
    }

    /**
     * Clears the cache of all generated passwords.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Retrieves all cached generated passwords.
     *
     * @return a list of all cached generated passwords
     */
    public List<GeneratedPassword> getAllCachedPasswords() {
        return cache.entrySet().stream()
                .map(entry -> new GeneratedPassword(entry.getKey(), entry.getValue()))
                .toList();
    }
}
