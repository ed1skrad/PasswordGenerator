package com.password.generator.bsuir.security.exception;

/**
 * Custom exception class to handle cases where a role is not found.
 * This exception is thrown when a user tries
 * to access a resource that requires a role that does not exist.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new RoleNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
