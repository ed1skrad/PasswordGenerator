package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;

/**
 * Data transfer object for bulk password generation.
 */
public class BulkPasswordGenerationDto {

    private int count;
    private Difficulty difficulty;
    private int length;

    /**
     * Constructs a new BulkPasswordGenerationDto.
     *
     * @param count the number of passwords to generate
     * @param difficulty the difficulty of the passwords to generate
     * @param length the length of the passwords to generate
     */
    public BulkPasswordGenerationDto(int count, Difficulty difficulty, int length) {
        this.count = count;
        this.difficulty = difficulty;
        this.length = length;
    }

    /**
     * Gets the number of passwords to generate.
     *
     * @return the number of passwords to generate
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the number of passwords to generate.
     *
     * @param count the number of passwords to generate
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the difficulty of the passwords to generate.
     *
     * @return the difficulty of the passwords to generate
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the passwords to generate.
     *
     * @param difficulty the difficulty of the passwords to generate
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the length of the passwords to generate.
     *
     * @return the length of the passwords to generate
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the passwords to generate.
     *
     * @param length the length of the passwords to generate
     */
    public void setLength(int length) {
        this.length = length;
    }
}
