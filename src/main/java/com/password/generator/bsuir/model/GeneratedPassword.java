package com.password.generator.bsuir.model;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.security.domain.model.User;
import jakarta.persistence.*;

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

    public GeneratedPassword() {
    }


    public Long getId () {
        return id;
    }

    public void setId (Long id){
        this.id = id;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public Difficulty getDifficulty () {
        return difficulty;
    }

    public void setDifficulty (Difficulty difficulty){
        this.difficulty = difficulty;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user){
        this.user = user;
    }

    public GeneratedPassword(String password, Difficulty difficulty, User user) {
            this.password = password;
            this.difficulty = difficulty;
            this.user = user;
    }

    public GeneratedPassword(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}

