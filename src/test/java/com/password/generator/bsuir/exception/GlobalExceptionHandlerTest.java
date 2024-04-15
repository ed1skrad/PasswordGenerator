package com.password.generator.bsuir.exception;

import com.password.generator.bsuir.dto.response.ErrorResponse;
import com.password.generator.bsuir.security.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Test
     void testHandleBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBadCredentialsException(request, exception);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody().getMessage());
    }

    @Test
     void testHandleEmailInUseException() {
        EmailInUseException exception = new EmailInUseException("Email already in use");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleEmailInUseException(request, exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is already in use", response.getBody().getMessage());
    }

    @Test
     void testHandleForbiddenException() {
        ForbiddenException exception = new ForbiddenException("Access denied");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleForbiddenException(request, exception);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access is denied", response.getBody().getMessage());
    }

    @Test
     void testHandleRoleNotFoundException() {
        RoleNotFoundException exception = new RoleNotFoundException("Role not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRoleNotFoundException(request, exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Role not found", response.getBody().getMessage());
    }

    @Test
     void testHandleUsernameTakenException() {
        UsernameTakenException exception = new UsernameTakenException("Username already taken");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUsernameTakenException(request, exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username is already taken", response.getBody().getMessage());
    }

    @Test
     void testHandleRuntimeException() {
        RuntimeException exception = new RuntimeException("Unexpected error");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(request, exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
    }

    @Test
     void testHandlePasswordGenerationException() {
        PasswordGenerationException exception = new PasswordGenerationException("Error generating password");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handlePasswordGenerationException(request, exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error generating password: Error generating password", response.getBody().getMessage());
    }
}
