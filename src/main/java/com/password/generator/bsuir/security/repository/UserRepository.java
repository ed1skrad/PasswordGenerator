package com.password.generator.bsuir.security.repository;

import com.password.generator.bsuir.security.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * Provides methods for basic CRUD operations and querying users by username and email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by its username.
     *
     * @param username the username of the user to find.
     * @return an Optional containing the user if it exists, or an empty Optional if it does not.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username already exists.
     *
     * @param username the username to check.
     * @return true if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email the email to check.
     * @return true if a user with the given email exists, false otherwise.
     */
    boolean existsByEmail(String email);

}
