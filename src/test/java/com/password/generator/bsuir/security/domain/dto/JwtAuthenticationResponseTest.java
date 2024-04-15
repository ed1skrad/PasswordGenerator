package com.password.generator.bsuir.security.domain.dto;

import com.password.generator.bsuir.security.domain.dto.JwtAuthenticationResponse;
import com.password.generator.bsuir.security.domain.model.RoleEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

 class JwtAuthenticationResponseTest {

    @Test
     void testGetToken() {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse("token");
        assertEquals("token", response.getToken());
    }

    @Test
     void testSetToken() {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken("token");
        assertEquals("token", response.getToken());
    }

    @Test
     void testGetUsername() {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setUsername("username");
        assertEquals("username", response.getUsername());
    }

    @Test
     void testSetUsername() {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setUsername("username");
        assertEquals("username", response.getUsername());
    }

    @Test
     void testGetRoles() {
        List<RoleEnum> roles = Arrays.asList(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setRole(roles);
        assertEquals(roles, response.getRoles());
    }

    @Test
     void testSetRoles() {
        List<RoleEnum> roles = Arrays.asList(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setRole(roles);
        assertEquals(roles, response.getRoles());
    }
}
