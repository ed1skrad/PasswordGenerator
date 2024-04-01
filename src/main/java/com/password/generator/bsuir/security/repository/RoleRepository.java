package com.password.generator.bsuir.security.repository;

import com.password.generator.bsuir.security.domain.model.Role;
import com.password.generator.bsuir.security.domain.model.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Role entity.
 * Provides methods for basic CRUD operations and querying roles by name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to find.
     * @return an Optional containing the role if it exists, or an empty Optional if it does not.
     */
    Optional<Role> findByName(RoleEnum roleName);
}
