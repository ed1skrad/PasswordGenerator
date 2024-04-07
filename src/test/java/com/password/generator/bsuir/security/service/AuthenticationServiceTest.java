package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.config.InvalidTokenRepository;
import com.password.generator.bsuir.security.domain.dto.JwtAuthenticationResponse;
import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import com.password.generator.bsuir.security.domain.dto.SignUpRequest;
import com.password.generator.bsuir.security.domain.model.Role;
import com.password.generator.bsuir.security.domain.model.RoleEnum;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.exception.EmailInUseException;
import com.password.generator.bsuir.security.exception.RoleNotFoundException;
import com.password.generator.bsuir.security.exception.UsernameTakenException;
import com.password.generator.bsuir.security.repository.RoleRepository;
import com.password.generator.bsuir.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private InvalidTokenRepository invalidTokenRepository;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(userService, jwtService, passwordEncoder,
                authenticationManager, userRepository, roleRepository, invalidTokenRepository);

        Mockito.when(userService.userDetailsService()).thenReturn(userDetailsService);

    }

    @Test
     void testSignUp_success() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        Role userRole = new Role();
        userRole.setName(RoleEnum.ROLE_USER);

        Mockito.when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.of(userRole));
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        Mockito.when(jwtService.generateToken(ArgumentMatchers.any(User.class))).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signUp(request);

        Assertions.assertEquals("jwtToken", response.getToken());
    }

    @Test
     void testSignUp_usernameTaken() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        Mockito.when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        Assertions.assertThrows(UsernameTakenException.class, () -> authenticationService.signUp(request));
    }

    // ... (previous imports and test class setup)

    @Test
     void testSignUp_emailInUse() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        Mockito.when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        Assertions.assertThrows(EmailInUseException.class, () -> authenticationService.signUp(request));
    }

    @Test
     void testSignUp_roleNotFound() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        Mockito.when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.empty());

        Assertions.assertThrows(RoleNotFoundException.class, () -> authenticationService.signUp(request));
    }

    @Test
     void testSignIn_success() {
        SignInRequest request = new SignInRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
        Mockito.when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signIn(request);

        Assertions.assertEquals("jwtToken", response.getToken());
    }

    @Test
     void testDeleteUserById_success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        authenticationService.deleteUserById(userId);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
     void testDeleteUserById_userNotFound() {
        Long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> authenticationService.deleteUserById(userId));
    }

    @Test
     void testUpdateUser_success() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("newUsername");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("old@example.com");
        existingUser.setPassword("oldPassword");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");

        authenticationService.updateUser(userId, updatedUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
        Assertions.assertEquals("newUsername", existingUser.getUsername());
        Assertions.assertEquals("new@example.com", existingUser.getEmail());
        Assertions.assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
     void testUpdateUser_userNotFound() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("newUsername");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> authenticationService.updateUser(userId, updatedUser));
    }

    @Test
     void testFindUserRolesByUsername_success() {
        String username = "testUser";
        Role userRole = new Role();
        userRole.setName(RoleEnum.ROLE_USER);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);

        User user = new User();
        user.setUsername(username);
        user.setRole(userRoles);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        List<RoleEnum> roles = authenticationService.findUserRolesByUsername(username);

        Assertions.assertEquals(1, roles.size());
        Assertions.assertEquals(RoleEnum.ROLE_USER, roles.get(0));
    }

    @Test
     void testFindUserRolesByUsername_userNotFound() {
        String username = "testUser";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> authenticationService.findUserRolesByUsername(username));
    }

    @Test
     void testFindUserRolesByUsername_roleNotFound() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setRole(new ArrayList<>());

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Assertions.assertThrows(RoleNotFoundException.class, () -> authenticationService.findUserRolesByUsername(username));
    }
}
