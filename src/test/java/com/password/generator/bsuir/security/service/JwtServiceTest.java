package com.password.generator.bsuir.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        jwtService.jwtSigningKey = "secretsgkldfmklgsfkgmdklfmkl4234k32m4k32krkdsfjdfkgkk2k4k1234k13otjtjwrgrwnbsrfopbnkey";
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
    }

    @Test
    void testExtractUserName() {
        String token = generateToken();
        String userName = jwtService.extractUserName(token);
        assertEquals("testuser", userName);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid_ValidToken() {
        String token = generateToken();
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid_InvalidUser() {
        String token = generateToken();
        UserDetails invalidUserDetails = mock(UserDetails.class);
        when(invalidUserDetails.getUsername()).thenReturn("invaliduser");
        assertFalse(jwtService.isTokenValid(token, invalidUserDetails));
    }

    public void testIsTokenValid_ExpiredToken() {
        String expiredToken = generateExpiredToken();
        Assertions.assertThrows(ExpiredJwtException.class, () -> jwtService.extractUserName(expiredToken));
        assertFalse(jwtService.isTokenValid(expiredToken, userDetails));
    }

    private String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, jwtService.getSigningKey())
                .compact();
    }

    private String generateExpiredToken() {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(SignatureAlgorithm.HS256, jwtService.getSigningKey())
                .compact();
    }
}
