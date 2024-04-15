package com.password.generator.bsuir.security.config;

import com.password.generator.bsuir.security.config.InvalidTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class InvalidTokenRepositoryTest {

    private InvalidTokenRepository invalidTokenRepository;

    @BeforeEach
     void setup() {
        invalidTokenRepository = new InvalidTokenRepository();
    }

    @Test
     void testAddToken() {
        String token = "testToken";
        invalidTokenRepository.addToken(token);
        assertTrue(invalidTokenRepository.isTokenInvalid(token));
    }

    @Test
     void testIsTokenInvalid_NullToken() {
        assertFalse(invalidTokenRepository.isTokenInvalid(null));
    }

    @Test
     void testIsTokenInvalid_EmptyToken() {
        assertFalse(invalidTokenRepository.isTokenInvalid(""));
    }

    @Test
     void testIsTokenInvalid_ValidToken() {
        String token = "testToken";
        invalidTokenRepository.addToken(token);
        assertTrue(invalidTokenRepository.isTokenInvalid(token));
    }

    @Test
     void testRemoveToken() {
        String token = "testToken";
        invalidTokenRepository.addToken(token);
        invalidTokenRepository.removeToken(token);
        assertFalse(invalidTokenRepository.isTokenInvalid(token));
    }

    @Test
     void testClear() {
        String token1 = "testToken1";
        String token2 = "testToken2";
        invalidTokenRepository.addToken(token1);
        invalidTokenRepository.addToken(token2);
        invalidTokenRepository.clear();
        assertFalse(invalidTokenRepository.isTokenInvalid(token1));
        assertFalse(invalidTokenRepository.isTokenInvalid(token2));
    }

    @Test
     void testSize() {
        String token1 = "testToken1";
        String token2 = "testToken2";
        invalidTokenRepository.addToken(token1);
        invalidTokenRepository.addToken(token2);
        assertEquals(2, invalidTokenRepository.size());
    }
}
