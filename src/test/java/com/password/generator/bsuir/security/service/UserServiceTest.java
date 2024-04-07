package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.repository.UserRepository;
import com.password.generator.bsuir.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.repository.UserRepository;
import com.password.generator.bsuir.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

 class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetByUsername_userFound() {
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setEmail("test@example.com");
        expectedUser.setPassword("password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User result = userService.getByUsername(username);

        assertEquals(expectedUser, result);
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void testGetByUsername_userNotFound() {
        String username = "nonexistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void testUserDetailsService_userFound() {
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setEmail("test@example.com");
        expectedUser.setPassword("password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        UserDetailsService userDetailsService = userService.userDetailsService();
        org.springframework.security.core.userdetails.UserDetails result =
                (org.springframework.security.core.userdetails.UserDetails) userDetailsService.loadUserByUsername(username);

        assertEquals(expectedUser.getUsername(), result.getUsername());
        assertEquals(expectedUser.getPassword(), result.getPassword());
        Mockito.verify(userRepository).findByUsername(username);
    }

}

