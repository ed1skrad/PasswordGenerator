package com.password.generator.bsuir.security.config;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class InvalidTokenRepository {

    private final Set<String> invalidTokens = Collections.synchronizedSet(new HashSet<>());
    private final ReentrantLock lock = new ReentrantLock();

    public void addToken(String token) {
        lock.lock();
        try {
            invalidTokens.add(token);
        } finally {
            lock.unlock();
        }
    }

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

    public void removeToken(String token) {
        lock.lock();
        try {
            invalidTokens.remove(token);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            invalidTokens.clear();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return invalidTokens.size();
        } finally {
            lock.unlock();
        }
    }
}
