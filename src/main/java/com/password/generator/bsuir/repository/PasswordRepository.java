package com.password.generator.bsuir.repository;

import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<GeneratedPassword, Long> {
    List<GeneratedPassword> findByDifficulty(Difficulty difficulty);
}
