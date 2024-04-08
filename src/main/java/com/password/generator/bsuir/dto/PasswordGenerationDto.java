package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import jakarta.validation.constraints.Min;

/**
 * Data transfer object for password generation.
 */
public class PasswordGenerationDto {

    private Difficulty difficulty;
    @Min(value = 1, message = "Password length must be at least 1")
    private int length;

    /**
     * Constructs a new PasswordGenerationDto.
     *
     * @param difficulty the difficulty of the password to generate
     * @param length the length of the password to generate
     */
    public PasswordGenerationDto(Difficulty difficulty, int length) {
        this.difficulty = difficulty;
        this.length = length;
    }

    /**
     * Gets the difficulty of the password to generate.
     *
     * @return the difficulty of the password to generate
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the password to generate.
     *
     * @param difficulty the difficulty of the password to generate
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the length of the password to generate.
     *
     * @return the length of the password to generate
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the password to generate.
     *
     * @param length the length of the password to generate
     */
    public void setLength(int length) {
        this.length = length;
    }
}
