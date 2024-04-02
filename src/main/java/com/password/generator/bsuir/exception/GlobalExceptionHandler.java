package com.password.generator.bsuir.exception;

import com.password.generator.bsuir.security.exception.BadCredentialsException;
import com.password.generator.bsuir.security.exception.ForbiddenException;
import com.password.generator.bsuir.security.exception.EmailInUseException;
import com.password.generator.bsuir.security.exception.RoleNotFoundException;
import com.password.generator.bsuir.security.exception.UsernameTakenException;
import java.nio.file.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles BadCredentialsException.
     *
     * @param request the HTTP servlet request
     * @param exception the BadCredentialsException
     * @return a ResponseEntity with an error message and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(HttpServletRequest request,
                                                                BadCredentialsException exception) {
        logger.error("BadCredentials occurred: " + exception.getMessage());
        String errorMessage = "Invalid username or password";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles EmailInUseException.
     *
     * @param request the HTTP servlet request
     * @param exception the EmailInUseException
     * @return a ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(value = {EmailInUseException.class})
    public ResponseEntity<Object> handleEmailInUseException(HttpServletRequest request,
                                                            EmailInUseException exception) {
        logger.error("EmailInUseException occurred: " + exception.getMessage());
        String errorMessage = "Email is already in use";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ForbiddenException.
     *
     * @param request the HTTP servlet request
     * @param exception the ForbiddenException
     * @return a ResponseEntity with an error message and HTTP status FORBIDDEN
     */
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(HttpServletRequest request,
                                                           ForbiddenException exception) {
        logger.error("ForbiddenException occurred: " + exception.getMessage());
        String errorMessage = "Access is denied";
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles RoleNotFoundException.
     *
     * @param request the HTTP servlet request
     * @param exception the RoleNotFoundException
     * @return a ResponseEntity with an error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> handleRoleNotFoundException(HttpServletRequest request,
                                                              RoleNotFoundException exception) {
        logger.error("RoleNotFoundException occurred: " + exception.getMessage());
        String errorMessage = "Role not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UsernameTakenException.
     *
     * @param request the HTTP servlet request
     * @param exception the UsernameTakenException
     * @return a ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(value = {UsernameTakenException.class})
    public ResponseEntity<Object> handleUsernameTakenException(HttpServletRequest request,
                                                               UsernameTakenException exception) {
        logger.error("UsernameTakenException occurred: " + exception.getMessage());
        String errorMessage = "Username is already taken";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access is denied.");
    }


    /**
     * Handles RuntimeException.
     *
     * @param request the HTTP servlet request
     * @param exception the RuntimeException
     * @return a ResponseEntity with an error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(HttpServletRequest request,
                                                         RuntimeException exception) {
        logger.error("RuntimeException occurred: " + exception.getMessage());
        String errorMessage = "An unexpected error occurred";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
