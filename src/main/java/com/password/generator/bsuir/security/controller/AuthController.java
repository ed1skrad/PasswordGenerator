package com.password.generator.bsuir.security.controller;

import com.password.generator.bsuir.security.domain.dto.JwtAuthenticationResponse;
import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import com.password.generator.bsuir.security.domain.dto.SignUpRequest;
import com.password.generator.bsuir.security.exception.ForbiddenException;
import com.password.generator.bsuir.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user authentication.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Signs up a new user and returns a JWT authentication response.
     *
     * @param signUpRequest the sign-up request containing user details
     * @return the JWT authentication response
     * @throws ForbiddenException if an error occurs during authentication processing
     */
    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        try {
            JwtAuthenticationResponse response = authenticationService.signUp(signUpRequest);
            setUsernameAndRole(response, signUpRequest.getUsername());
            return response;
        } catch (ForbiddenException e) {
            throw new ForbiddenException(
                    "Some error occurred while auth processing: " + e.getMessage());
        }
    }

    /**
     * Signs in a user and returns a JWT authentication response.
     *
     * @param signInRequest the sign-in request containing user credentials
     * @return the JWT authentication response
     * @throws ForbiddenException if an error occurs during authentication processing
     */
    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        try {
            JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
            setUsernameAndRole(response, signInRequest.getUsername());
            return response;
        } catch (Exception e) {
            throw new ForbiddenException("Invalid username or password");
        }
    }

    /**
     * Logs out a user and clears their authentication token.
     *
     * @param request the HTTP servlet request
     * @return a response entity with a 200 OK status
     * @throws ForbiddenException if the user does not have permission to access the resource
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid HttpServletRequest request) {
        try {
            authenticationService.logout(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
    }

    /**
     * Sets the username and role in the JWT authentication response.
     *
     * @param response the JWT authentication response
     * @param username the username
     */
    void setUsernameAndRole(JwtAuthenticationResponse response, String username) {
        response.setUsername(username);
        response.setRole(authenticationService.findUserRolesByUsername(username));
    }
}
