package com.password.generator.bsuir.security.exception;

/**
 * Custom exception class to handle forbidden access.
 * This exception is thrown when
 * a user tries to access a resource that they do not have permission to access.
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Constructs a new ForbiddenException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
