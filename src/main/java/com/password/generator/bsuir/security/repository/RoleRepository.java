package com.password.generator.bsuir.security.repository;

import com.password.generator.bsuir.security.domain.model.Role;
import com.password.generator.bsuir.security.domain.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum roleName);

}
