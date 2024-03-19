package com.password.generator.bsuir.security.domain.dto;

import com.password.generator.bsuir.security.domain.model.RoleEnum;

import java.util.List;

public class JwtAuthenticationResponse {

    private String token;

    private String username;

    private List<RoleEnum> role;

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

    public List<RoleEnum> getRole() {
        return role;
    }

    public void setRole(List<RoleEnum> role) {
        this.role = role;
    }
}
