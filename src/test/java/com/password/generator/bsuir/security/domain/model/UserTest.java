package com.password.generator.bsuir.security.domain.model;

import com.password.generator.bsuir.model.GeneratedPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 class UserTest {

    private User user;

    @BeforeEach
     void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("testpassword");
        user.setRole(new ArrayList<>());
        user.setGeneratedPasswords(new HashSet<>());
    }

    @Test
     void testGetId() {
        assertEquals(1L, user.getId());
    }

    @Test
     void testSetId() {
        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
     void testGetUsername() {
        assertEquals("testuser", user.getUsername());
    }

    @Test
     void testSetUsername() {
        user.setUsername("newtestuser");
        assertEquals("newtestuser", user.getUsername());
    }

    @Test
     void testGetEmail() {
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
     void testSetEmail() {
        user.setEmail("newtestuser@example.com");
        assertEquals("newtestuser@example.com", user.getEmail());
    }

    @Test
     void testGetPassword() {
        assertEquals("testpassword", user.getPassword());
    }

    @Test
     void testSetPassword() {
        user.setPassword("newtestpassword");
        assertEquals("newtestpassword", user.getPassword());
    }

    @Test
     void testGetRoles() {
        assertEquals(0, user.getRole().size());
        Role role = new Role(RoleEnum.ROLE_USER);
        user.getRole().add(role);
        assertEquals(1, user.getRole().size());
    }

    @Test
     void testSetRoles() {
        Role role = new Role(RoleEnum.ROLE_USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRole(roles);
        assertEquals(1, user.getRole().size());
    }

    @Test
     void testGetGeneratedPasswords() {
        assertEquals(0, user.getGeneratedPasswords().size());
        GeneratedPassword generatedPassword = new GeneratedPassword();
        user.getGeneratedPasswords().add(generatedPassword);
        assertEquals(1, user.getGeneratedPasswords().size());
    }

    @Test
     void testSetGeneratedPasswords() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        Set<GeneratedPassword> generatedPasswords = new HashSet<>();
        generatedPasswords.add(generatedPassword);
        user.setGeneratedPasswords(generatedPasswords);
        assertEquals(1, user.getGeneratedPasswords().size());
    }

    @Test
     void testGetAuthorities() {
        Role role = new Role(RoleEnum.ROLE_USER);
        user.getRole().add(role);
        assertEquals(1, user.getAuthorities().size());
        assertEquals("ROLE_USER", user.getAuthorities().iterator().next().getAuthority());
    }

    @Test
     void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
     void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
     void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
     void testIsEnabled() {
        assertTrue(user.isEnabled());
    }
}
