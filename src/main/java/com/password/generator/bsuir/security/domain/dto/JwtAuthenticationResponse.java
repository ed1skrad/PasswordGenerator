package com.password.generator.bsuir.security.domain.dto;

import com.password.generator.bsuir.security.domain.model.Role;
import com.password.generator.bsuir.security.domain.model.RoleEnum;

public class JwtAuthenticationResponse {

    private String token;

    private String username;

    private RoleEnum role;

    public JwtAuthenticationResponse() {
    }
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
