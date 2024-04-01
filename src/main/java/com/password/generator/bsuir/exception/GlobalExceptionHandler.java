package com.password.generator.bsuir.exception;

import com.password.generator.bsuir.security.exception.BadCredentialsException;
import com.password.generator.bsuir.security.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles BadCredentialsException.
     *
     * @param request the HTTP servlet request
     * @param exception the BadCredentialsException
     * @return a ResponseEntity with an error message and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(HttpServletRequest request,
                                                                BadCredentialsException exception) {
        logger.error("BadCredentials occurred");
        String errorMessage = "Invalid username or password";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles ForbiddenException.
     *
     * @param e the ForbiddenException
     * @return a ResponseEntity with an error message and HTTP status FORBIDDEN
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
        logger.error("ForbiddenException occurred: " + e.getMessage(), e);
        return new ResponseEntity<>("Forbidden: Access is denied.", HttpStatus.FORBIDDEN);
    }

    /**
     * Handles RuntimeException.
     *
     * @param request the HTTP servlet request
     * @param exception the RuntimeException
     * @return a ResponseEntity with an error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(HttpServletRequest request,
                                                         RuntimeException exception) {
        String errorMessage = "An unexpected error occurred";
        logger.error("500 error occurred");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
