package com.password.generator.bsuir.repository;

import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for the GeneratedPassword entity.
 */
@Repository
public interface PasswordRepository extends JpaRepository<GeneratedPassword, Long> {

    /**
     * Finds all generated passwords with the given difficulty.
     *
     * @param difficulty the difficulty
     * @return a list of generated passwords
     */
    List<GeneratedPassword> findByDifficulty(Difficulty difficulty);

    /**
     * Finds all generated passwords for the user with the given username.
     *
     * @param username the username
     * @return a list of generated passwords
     */
    @Query("SELECT gp FROM GeneratedPassword gp JOIN gp.user u WHERE u.username = :username")
    List<GeneratedPassword> findAllByUserUsername(@Param("username") String username);
}
