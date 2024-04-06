package com.password.generator.bsuir.dto.response;

import org.springframework.http.HttpStatus;

/**
 * This record represents an error response object that contains an error message and an HTTP status code.
 * It is used to return information about errors that occur during the execution of the application.
 *
 * @param message the error message
 * @param status  the HTTP status code
 */
public record ErrorResponse(String message, HttpStatus status) {
}
