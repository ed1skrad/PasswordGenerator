package com.password.generator.bsuir.security.domain.dto;

/**
 * Data transfer object for a message response.
 */
public final class MessageResponse {

    private final String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
