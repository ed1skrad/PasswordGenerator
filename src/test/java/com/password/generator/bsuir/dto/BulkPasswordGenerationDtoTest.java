package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class BulkPasswordGenerationDtoTest {

    @Test
     void testConstructor() {
        BulkPasswordGenerationDto dto = new BulkPasswordGenerationDto(10, Difficulty.EASY, 8);
        assertEquals(10, dto.getCount());
        assertEquals(Difficulty.EASY, dto.getDifficulty());
        assertEquals(8, dto.getLength());
    }

    @Test
     void testSetGetCount() {
        BulkPasswordGenerationDto dto = new BulkPasswordGenerationDto(10, Difficulty.EASY, 8);
        dto.setCount(20);
        assertEquals(20, dto.getCount());
    }

    @Test
     void testSetGetDifficulty() {
        BulkPasswordGenerationDto dto = new BulkPasswordGenerationDto(10, Difficulty.EASY, 8);
        dto.setDifficulty(Difficulty.NORMAL);
        assertEquals(Difficulty.NORMAL, dto.getDifficulty());
    }

    @Test
     void testSetGetLength() {
        BulkPasswordGenerationDto dto = new BulkPasswordGenerationDto(10, Difficulty.EASY, 8);
        dto.setLength(16);
        assertEquals(16, dto.getLength());
    }
}
