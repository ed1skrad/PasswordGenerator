package com.password.generator.bsuir.repository;

import com.password.generator.bsuir.model.GeneratedPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<GeneratedPassword, Long> {
    GeneratedPassword findTopByOrderByIdDesc();
}
