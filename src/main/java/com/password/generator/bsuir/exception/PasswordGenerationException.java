package com.password.generator.bsuir.exception;

/**
 * Custom exception for password generation errors.
 */
public class PasswordGenerationException extends RuntimeException {

    /**
     * Constructs a new PasswordGenerationException.
     *
     * @param message the error message
     */
    public PasswordGenerationException(String message) {
        super(message);
    }
}
