package com.password.generator.bsuir.dto;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Set;
class PasswordGenerationDtoTests {

    private Validator validator;

    @org.junit.jupiter.api.BeforeEach
     void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidPasswordGenerationDto() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 10);
        Set<ConstraintViolation<PasswordGenerationDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
     void testInvalidLength() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 0);
        Set<ConstraintViolation<PasswordGenerationDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
        ConstraintViolation<PasswordGenerationDto> violation = violations.iterator().next();
        Assertions.assertEquals("Password length must be at least 1", violation.getMessage());
    }

    @Test
     void testNullLength() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 0);
        dto.setLength(0);
        Set<ConstraintViolation<PasswordGenerationDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
        ConstraintViolation<PasswordGenerationDto> violation = violations.iterator().next();
        Assertions.assertNotEquals("must not be null", violation.getMessage());
    }
}
