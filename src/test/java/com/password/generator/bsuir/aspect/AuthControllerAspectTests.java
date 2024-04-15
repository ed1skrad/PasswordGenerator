package com.password.generator.bsuir.aspect;

import com.password.generator.bsuir.security.controller.AuthController;
import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import com.password.generator.bsuir.security.domain.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
 class AuthControllerAspectTests {

    @Autowired
    private AuthController authController;

    @Mock
    private SignUpRequest signUpRequest;

    @Mock
    private SignInRequest signInRequest;

    @Mock
    private HttpServletRequest httpServletRequest;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testLogoutLogging() {
        when(httpServletRequest.getAttribute("username")).thenReturn("testUser");
        authController.logout(httpServletRequest);
        verify(httpServletRequest).getAttribute("username");
    }
}
