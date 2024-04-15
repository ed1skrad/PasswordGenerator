package com.password.generator.bsuir.security.config;

import com.password.generator.bsuir.security.config.JwtAuthenticationFilter;
import com.password.generator.bsuir.security.config.InvalidTokenRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.JwtService;
import com.password.generator.bsuir.security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

 class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private InvalidTokenRepository invalidTokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userService, invalidTokenRepository);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
     void testDoFilterInternal_NoAuthHeader() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
     void testDoFilterInternal_InvalidAuthHeader() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn("Invalid");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
     void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn("Bearer invalidToken");
        when(invalidTokenRepository.isTokenInvalid("invalidToken")).thenReturn(true);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
     void testDoFilterInternal_ValidToken_AuthenticationAlreadySet() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn("Bearer validToken");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}
