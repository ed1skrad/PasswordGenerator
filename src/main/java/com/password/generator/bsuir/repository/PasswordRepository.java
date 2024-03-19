package com.password.generator.bsuir.repository;

import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<GeneratedPassword, Long> {
    List<GeneratedPassword> findByDifficulty(Difficulty difficulty);

    @Query("SELECT gp FROM GeneratedPassword gp JOIN gp.user u WHERE u.username = :username")
    List<GeneratedPassword> findAllByUserUsername(@Param("username") String username);

    @Query("SELECT gp FROM GeneratedPassword gp ORDER BY gp.id DESC")
    List<GeneratedPassword> findTopNOrderByIdDesc();
}
