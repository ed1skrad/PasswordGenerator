package com.password.generator.bsuir.security.exception;

import java.lang.RuntimeException;

/**
 * Custom exception class to handle cases where a username is already taken.
 * This exception is thrown when
 * a user tries to register with a username that is already associated with an account.
 */
public class UsernameTakenException extends RuntimeException {

    /**
     * Constructs a new UsernameTakenException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UsernameTakenException(String message) {
        super(message);
    }
}
