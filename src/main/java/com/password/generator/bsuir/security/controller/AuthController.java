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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        try {
            JwtAuthenticationResponse response = authenticationService.signUp(request);
            response.setUsername(request.getUsername());
            response.setRole(authenticationService.findUserRolesByUsername(request.getUsername()));
            return response;
        } catch (ForbiddenException e) {
            throw new ForbiddenException("Some error occurred while auth processing: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        try {
            JwtAuthenticationResponse response = authenticationService.signIn(request);
            response.setUsername(request.getUsername());
            response.setRole(authenticationService.findUserRolesByUsername(request.getUsername()));
            return response;
        } catch (Exception e) {
            throw new ForbiddenException("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid HttpServletRequest request) {
        try {
            authenticationService.logout(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
    }
}
