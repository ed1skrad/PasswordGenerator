package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.model.DifficultyEnum.Difficulty;

public class PasswordGenerationDto {
    private Difficulty difficulty;
    private int length;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public PasswordGenerationDto(Difficulty difficulty, int length) {
        this.difficulty = difficulty;
        this.length = length;
    }
}
