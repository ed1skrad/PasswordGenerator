package com.password.generator.bsuir.model;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.security.domain.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity representing a generated password.
 */
@Entity
@Table(name = "generated_password")
public class GeneratedPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Default constructor.
     */
    public GeneratedPassword() {
    }

    /**
     * Constructs a new GeneratedPassword with the given password, difficulty, and user.
     *
     * @param password the password
     * @param difficulty the difficulty
     * @param user the user
     */
    public GeneratedPassword(String password, Difficulty difficulty, User user) {
        this.password = password;
        this.difficulty = difficulty;
        this.user = user;
    }

    /**
     * Constructs a new GeneratedPassword with the given ID and password.
     *
     * @param id the ID
     * @param password the password
     */
    public GeneratedPassword(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Constructs a new GeneratedPassword with the given ID, password, difficulty, and user.
     *
     * @param id the ID
     * @param password the password
     * @param difficulty the difficulty
     * @param user the user
     */
    public GeneratedPassword(Long id, String password, Difficulty difficulty, User user) {
        this.id = id;
        this.password = password;
        this.difficulty = difficulty;
        this.user = user;
    }

    /**
     * Gets the ID of the generated password.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the generated password.
     *
     * @param id the ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the difficulty of the generated password.
     *
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the generated password.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the user who generated the password.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who generated the password.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
