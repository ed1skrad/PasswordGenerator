package com.password.generator.bsuir.security.domain.dto;

import com.password.generator.bsuir.security.domain.model.RoleEnum;
import java.util.List;
import java.util.Objects;

/**
 * Data transfer object for JWT authentication response.
 */
public class JwtAuthenticationResponse {

    private String token;

    private String username;

    private List<RoleEnum> roles;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    /**
     * Gets the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the JWT token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of roles.
     *
     * @return an unmodifiable list of roles
     */
    public List<RoleEnum> getRoles() {
        return List.copyOf(roles);
    }

    /**
     * Sets the list of roles.
     *
     * @param roles the list of roles
     */
    public void setRole(List<RoleEnum> roles) {
        this.roles = Objects.requireNonNullElseGet(roles, List::of);
    }
}
