package com.password.generator.bsuir.security.controller;

import com.password.generator.bsuir.security.domain.dto.JwtAuthenticationResponse;
import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import com.password.generator.bsuir.security.domain.dto.SignUpRequest;
import com.password.generator.bsuir.security.exception.ForbiddenException;
import com.password.generator.bsuir.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authenticationService);
    }

    @Test
    void testSignUp_Success() throws ForbiddenException {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testUser");
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        when(authenticationService.signUp(signUpRequest)).thenReturn(response);

        JwtAuthenticationResponse result = authController.signUp(signUpRequest);

        assertEquals(response, result);
        verify(authenticationService, times(1)).signUp(signUpRequest);
    }

    @Test
    void testSignUp_ForbiddenException() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testUser");
        when(authenticationService.signUp(signUpRequest)).thenThrow(ForbiddenException.class);

        assertThrows(ForbiddenException.class, () -> authController.signUp(signUpRequest));
        verify(authenticationService, times(1)).signUp(signUpRequest);
    }

    @Test
    void testSignIn_Success() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("testUser");
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        when(authenticationService.signIn(signInRequest)).thenReturn(response);

        JwtAuthenticationResponse result = authController.signIn(signInRequest);

        assertEquals(response, result);
        verify(authenticationService, times(1)).signIn(signInRequest);
    }

    @Test
    void testLogout_Success() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        doNothing().when(authenticationService).logout(request);

        ResponseEntity<Void> result = authController.logout(request);

        assertEquals(ResponseEntity.ok().build(), result);
        verify(authenticationService, times(1)).logout(request);
    }
}
