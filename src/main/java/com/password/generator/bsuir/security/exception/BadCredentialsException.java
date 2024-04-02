package com.password.generator.bsuir.security.exception;

/**
 * Custom exception class to handle bad credentials.
 * This exception is thrown when the provided credentials are invalid.
 */
public class BadCredentialsException extends RuntimeException {

    /**
     * Constructs a new BadCredentialsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BadCredentialsException(String message) {
        super(message);
    }
}
