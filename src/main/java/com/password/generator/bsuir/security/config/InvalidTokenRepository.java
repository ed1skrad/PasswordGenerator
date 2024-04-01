package com.password.generator.bsuir.security.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;

/**
 * Repository for invalid authentication tokens.
 */
@Component
public class InvalidTokenRepository {

    private final Set<String> invalidTokens = Collections.synchronizedSet(new HashSet<>());
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Adds an invalid token to the repository.
     *
     * @param token the token to add
     */
    public void addToken(String token) {
        lock.lock();
        try {
            invalidTokens.add(token);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the given token is invalid.
     *
     * @param token the token to check
     * @return true if the token is invalid, false otherwise
     */
    public boolean isTokenInvalid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        lock.lock();
        try {
            return invalidTokens.contains(token);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes an invalid token from the repository.
     *
     * @param token the token to remove
     */
    public void removeToken(String token) {
        lock.lock();
        try {
            invalidTokens.remove(token);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Clears all invalid tokens from the repository.
     */
    public void clear() {
        lock.lock();
        try {
            invalidTokens.clear();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of invalid tokens in the repository.
     *
     * @return the number of invalid tokens
     */
    public int size() {
        lock.lock();
        try {
            return invalidTokens.size();
        } finally {
            lock.unlock();
        }
    }
}
