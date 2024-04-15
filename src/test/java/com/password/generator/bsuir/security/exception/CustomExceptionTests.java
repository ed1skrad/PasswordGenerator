package com.password.generator.bsuir.security.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class CustomExceptionTests {

    @Test
     void testBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");
        assertNotNull(exception);
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
     void testEmailInUseException() {
        EmailInUseException exception = new EmailInUseException("Email already in use");
        assertNotNull(exception);
        assertEquals("Email already in use", exception.getMessage());
    }

    @Test
     void testForbiddenException() {
        ForbiddenException exception = new ForbiddenException("Access denied");
        assertNotNull(exception);
        assertEquals("Access denied", exception.getMessage());
    }

    @Test
     void testRoleNotFoundException() {
        RoleNotFoundException exception = new RoleNotFoundException("Role not found");
        assertNotNull(exception);
        assertEquals("Role not found", exception.getMessage());
    }

    @Test
     void testUsernameTakenException() {
        UsernameTakenException exception = new UsernameTakenException("Username already taken");
        assertNotNull(exception);
        assertEquals("Username already taken", exception.getMessage());
    }
}
