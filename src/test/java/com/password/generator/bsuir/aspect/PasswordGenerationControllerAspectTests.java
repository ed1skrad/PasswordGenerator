package com.password.generator.bsuir.aspect;

import com.password.generator.bsuir.controller.PasswordGenerationController;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.exception.PasswordGenerationException;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
 class PasswordGenerationControllerAspectTests {

    @Autowired
    private PasswordGenerationControllerAspect passwordGenerationControllerAspect;

    @MockBean
    private PasswordGenerationController passwordGenerationController;

    @BeforeEach
     void setup() {
        Mockito.reset(passwordGenerationController);
    }

    @Test
     void testValidateDifficulty_nullDifficulty() {
        Difficulty difficulty = null;
        Assertions.assertThrows(PasswordGenerationException.class, () -> {
            passwordGenerationControllerAspect.validateDifficulty(difficulty);
        });
        verify(passwordGenerationController, Mockito.never()).generatePassword(any(PasswordGenerationDto.class));
    }

    @Test
     void testValidateUsername_nullUsername() {
        String username = null;
        Assertions.assertThrows(PasswordGenerationException.class, () -> {
            passwordGenerationControllerAspect.validateUsername(username);
        });
        verify(passwordGenerationController, Mockito.never()).generatePassword(any(PasswordGenerationDto.class));
    }

    @Test
     void testValidateUsername_emptyUsername() {
        String username = "";
        Assertions.assertThrows(PasswordGenerationException.class, () -> {
            passwordGenerationControllerAspect.validateUsername(username);
        });
        verify(passwordGenerationController, Mockito.never()).generatePassword(any(PasswordGenerationDto.class));
    }
}
