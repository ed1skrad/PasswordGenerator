package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;

public class BulkPasswordGenerationDto {
    private int count;
    private Difficulty difficulty;
    private int length;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

}
