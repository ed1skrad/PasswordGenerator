package com.password.generator.bsuir.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {com.password.generator.bsuir.security.exception.BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(HttpServletRequest request, com.password.generator.bsuir.security.exception.BadCredentialsException exception) {
        String errorMessage = "Invalid username or password";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {com.password.generator.bsuir.security.exception.ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(HttpServletRequest request, com.password.generator.bsuir.security.exception.ForbiddenException exception) {
        String errorMessage = "You do not have permission to access this resource or your input incorrect!";
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(HttpServletRequest request, RuntimeException exception) {
        String errorMessage = "An unexpected error occurred";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
