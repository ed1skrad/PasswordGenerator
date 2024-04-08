package com.password.generator.bsuir.dto.response;

import org.springframework.http.HttpStatus;

/**
 * This record represents an error response object that contains an error message and an HTTP status code.
 * It is used to return information about errors that occur during the execution of the application.
 *
 */

public class ErrorResponse {

    private String message;
    private HttpStatus status;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
