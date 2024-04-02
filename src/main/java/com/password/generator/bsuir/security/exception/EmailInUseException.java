package com.password.generator.bsuir.security.exception;

/**
 * Custom exception class to handle cases where an email is already in use.
 * This exception is thrown when
 * a user tries to register with an email that is already associated with an account.
 */
public class EmailInUseException extends RuntimeException {

    /**
     * Constructs a new EmailInUseException with the specified detail message.
     *
     * @param message the detail message.
     */
    public EmailInUseException(String message) {
        super(message);
    }
}
