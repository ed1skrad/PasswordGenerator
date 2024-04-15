package com.password.generator.bsuir.config;

import com.password.generator.bsuir.model.GeneratedPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class PasswordCacheTest {

    private PasswordCache passwordCache;

    @BeforeEach
     void setup() {
        passwordCache = new PasswordCache();
    }

    @Test
     void testPutAndGet() {
        Long id = 1L;
        String password = "testPassword";
        passwordCache.put(id, password);

        String result = passwordCache.get(id);

        assertEquals(password, result);
    }

    @Test
     void testContains() {
        Long id = 1L;
        String password = "testPassword";
        passwordCache.put(id, password);

        boolean result = passwordCache.contains(id);

        assertTrue(result);
    }

    @Test
     void testContains_NotExist() {
        Long id = 1L;

        boolean result = passwordCache.contains(id);

        assertFalse(result);
    }

    @Test
     void testRemove() {
        Long id = 1L;
        String password = "testPassword";
        passwordCache.put(id, password);

        passwordCache.remove(id);

        assertNull(passwordCache.get(id));
    }

    @Test
     void testClear() {
        Long id1 = 1L;
        Long id2 = 2L;
        String password1 = "testPassword1";
        String password2 = "testPassword2";
        passwordCache.put(id1, password1);
        passwordCache.put(id2, password2);

        passwordCache.clear();

        assertNull(passwordCache.get(id1));
        assertNull(passwordCache.get(id2));
    }

    @Test
     void testGetAllCachedPasswords() {
        Long id1 = 1L;
        Long id2 = 2L;
        String password1 = "testPassword1";
        String password2 = "testPassword2";
        passwordCache.put(id1, password1);
        passwordCache.put(id2, password2);

        List<GeneratedPassword> result = passwordCache.getAllCachedPasswords();

        assertEquals(2, result.size());
        assertFalse(result.contains(new GeneratedPassword(id1, password1)));
        assertFalse(result.contains(new GeneratedPassword(id2, password2)));
    }
}

