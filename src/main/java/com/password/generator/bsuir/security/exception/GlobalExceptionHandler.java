package com.password.generator.bsuir.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(HttpServletRequest request, BadCredentialsException exception) {
        String errorMessage = "Invalid username or password";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException exception) {
        String errorMessage = "You do not have permission to access this resource";
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(HttpServletRequest request, RuntimeException exception) {
        String errorMessage = "An unexpected error occurred";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}