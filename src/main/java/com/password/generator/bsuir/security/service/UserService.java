package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}
