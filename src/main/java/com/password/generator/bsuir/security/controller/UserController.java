package com.password.generator.bsuir.security.controller;

import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller for managing users.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Deletes a user by ID.
     *
     * @param userIdToDelete the ID of the user to delete
     * @return a response entity with a success message
     */
    @DeleteMapping("/delete/{userIdToDelete}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userIdToDelete) {
        authenticationService.deleteUserById(userIdToDelete);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * Updates a user by ID.
     *
     * @param userIdToUpdate the ID of the user to update
     * @param updatedUser    the updated user data
     * @return a response entity with a success message
     */
    @PutMapping("/update/{userIdToUpdate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable Long userIdToUpdate,
                                             @Valid @RequestBody User updatedUser) {
        authenticationService.updateUser(userIdToUpdate, updatedUser);
        return ResponseEntity.ok("User updated successfully");
    }
}
