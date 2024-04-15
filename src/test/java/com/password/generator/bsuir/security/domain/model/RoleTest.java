package com.password.generator.bsuir.security.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class RoleTest {

    private Role role;

    @BeforeEach
     void setup() {
        role = new Role(RoleEnum.ROLE_USER);
    }

    @Test
     void testGetId() {
        assertNull(role.getId());
    }

    @Test
     void testSetId() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
     void testGetName() {
        assertEquals(RoleEnum.ROLE_USER, role.getName());
    }

    @Test
     void testSetName() {
        role.setName(RoleEnum.ROLE_ADMIN);
        assertEquals(RoleEnum.ROLE_ADMIN, role.getName());
    }

    @Test
     void testConstructorWithName() {
        Role role = new Role(RoleEnum.ROLE_ADMIN);
        assertEquals(RoleEnum.ROLE_ADMIN, role.getName());
    }
}
