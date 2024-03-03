package com.password.generator.bsuir.security.service;

import com.password.generator.bsuir.security.domain.model.Role;
import com.password.generator.bsuir.security.domain.model.RoleEnum;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.repository.RoleRepository;
import com.password.generator.bsuir.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public User save(User user) {
        return repository.save(user);
    }


    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleEnum.ROLE_ADMIN));
        user.setRole(roles);

        save(user);
    }

    @Transactional
    public Role findRoleByName(RoleEnum roleName) {
        return roleRepository.findByName(roleName)
                .orElse(null);
    }

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
