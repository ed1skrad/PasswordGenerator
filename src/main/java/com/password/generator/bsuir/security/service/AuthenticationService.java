package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.config.InvalidTokenRepository;
import com.password.generator.bsuir.security.config.JwtAuthenticationFilter;
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
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service class for handling authentication operations such as sign up, sign in, and logout.
 */

@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InvalidTokenRepository invalidTokenRepository;

    /**
     * Constructor.
     */
    @Autowired
    public AuthenticationService(UserService userService, JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 UserRepository userRepository, RoleRepository roleRepository,
                                 InvalidTokenRepository invalidTokenRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.invalidTokenRepository = invalidTokenRepository;
    }

    /**
     * Signs up a new user with the given sign up request.
     *
     * @param request the sign up request containing the user's details.
     * @return a JWT authentication response containing the user's token.
     * @throws UsernameTakenException if the username is already taken.
     * @throws EmailInUseException if the email is already in use.
     */

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameTakenException("Error: username is taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailInUseException("Error: email already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException("Error. User not found."));
        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Error. Admin not found."));

        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        roles.add(adminRole);

        user.setRole(roles);
        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Signs in a user with the given sign in request.
     *
     * @param request the sign in request containing the user's username and password.
     * @return a JWT authentication response containing the user's token.
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete.
     */
    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        user.getRole().clear();

        userRepository.delete(user);
    }

    /**
     * Updates a user's details with the given user object.
     *
     * @param userId the ID of the user to update.
     * @param updatedUser the updated user object containing the new details.
     */
    public void updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        if (!updatedUser.getPassword().equals(user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
    }

    /**
     * Logs out a user by clearing the security context'
     * and adding the JWT token to the invalid token repository.
     *
     * @param request the HTTP servlet request containing the JWT token.
     */
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader(JwtAuthenticationFilter.HEADER_NAME);
        if (authHeader != null && authHeader.startsWith(JwtAuthenticationFilter.BEARER_PREFIX)) {
            String jwt = authHeader.substring(JwtAuthenticationFilter.BEARER_PREFIX.length());
            invalidTokenRepository.addToken(jwt);
        }
        SecurityContextHolder.clearContext();
    }

    /**
     * Finds the roles of a user by their username.
     *
     * @param username the username of the user to find the roles for.
     * @return a list of the user's roles.
     * @throws RoleNotFoundException if the user has no roles.
     */
    public List<RoleEnum> findUserRolesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found with username: " + username));

        List<Role> userRoles = user.getRole();
        if (userRoles == null || userRoles.isEmpty()) {
            throw new RoleNotFoundException("Error. Role not found.");
        }

        return userRoles.stream()
                .map(Role::getName)
                .toList();
    }
}
