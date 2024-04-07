package com.password.generator.bsuir.exception;

import com.password.generator.bsuir.dto.response.ErrorResponse;
import com.password.generator.bsuir.security.exception.BadCredentialsException;
import com.password.generator.bsuir.security.exception.EmailInUseException;
import com.password.generator.bsuir.security.exception.ForbiddenException;
import com.password.generator.bsuir.security.exception.RoleNotFoundException;
import com.password.generator.bsuir.security.exception.UsernameTakenException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is a global exception handler for the application.
 * It provides a centralized place to handle exceptions
 * that occur during the execution of the application.
 * Each exception handler method returns a ResponseEntity with an
 * ErrorResponse object that contains the error message and HTTP status code.
 */

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles BadCredentialsException.
     *
     * @param request   the HTTP servlet request
     * @param exception the BadCredentialsException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code UNAUTHORIZED
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            HttpServletRequest request, BadCredentialsException exception) {
        logger.error("BadCredentials occurred!");
        ErrorResponse errorResponse =
                new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles EmailInUseException.
     *
     * @param request   the HTTP servlet request
     * @param exception the EmailInUseException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code BAD_REQUEST
     */
    @ExceptionHandler(value = {EmailInUseException.class})
    public ResponseEntity<ErrorResponse> handleEmailInUseException(
            HttpServletRequest request, EmailInUseException exception) {
        logger.error("EmailInUseException occurred!");
        ErrorResponse errorResponse =
                new ErrorResponse("Email is already in use", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ForbiddenException.
     *
     * @param request   the HTTP servlet request
     * @param exception the ForbiddenException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code FORBIDDEN
     */
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            HttpServletRequest request, ForbiddenException exception) {
        logger.error("ForbiddenException occurred!");
        ErrorResponse errorResponse = new ErrorResponse("Access is denied", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles RoleNotFoundException.
     *
     * @param request   the HTTP servlet request
     * @param exception the RoleNotFoundException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code NOT_FOUND
     */
    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(
            HttpServletRequest request, RoleNotFoundException exception) {
        logger.error("RoleNotFoundException occurred!");
        ErrorResponse errorResponse = new ErrorResponse("Role not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UsernameTakenException.
     *
     * @param request   the HTTP servlet request
     * @param exception the UsernameTakenException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code BAD_REQUEST
     */
    @ExceptionHandler(value = {UsernameTakenException.class})
    public ResponseEntity<ErrorResponse> handleUsernameTakenException(
            HttpServletRequest request, UsernameTakenException exception) {
        logger.error("UsernameTakenException occurred!");
        ErrorResponse errorResponse =
                new ErrorResponse("Username is already taken", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles RuntimeException.
     *
     * @param request   the HTTP servlet request
     * @param exception the RuntimeException
     * @return a ResponseEntity with an ErrorResponse
     * object that contains the error message and HTTP status code INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(HttpServletRequest request,
                                                                RuntimeException exception) {
        logger.error("RuntimeException occurred! Message: {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}