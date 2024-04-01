package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     *Constructor.
     */
    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve.
     * @return the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    }

    /**
     *Constructor.
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}
