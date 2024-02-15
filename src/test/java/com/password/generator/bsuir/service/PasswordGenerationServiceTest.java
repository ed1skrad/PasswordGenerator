package com.password.generator.bsuir.service;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.repository.PasswordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PasswordGenerationServiceTest {

    @Mock
    private PasswordRepository passwordRepository;

    @InjectMocks
    private PasswordGenerationService passwordGenerationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGeneratePasswordValidInput() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 8);
        String generatedPassword = passwordGenerationService.generatePassword(dto);
        assertEquals(8, generatedPassword.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeneratePasswordInvalidInput() {
        PasswordGenerationDto dto = new PasswordGenerationDto(null, -1);
        passwordGenerationService.generatePassword(dto);
    }

    @Test
    public void testGetLastGeneratedPasswordNotFound() {
        when(passwordRepository.findTopByOrderByIdDesc()).thenReturn(null);
        String result = passwordGenerationService.getLastGeneratedPassword();
        assertEquals(null, result);
    }

    @Test
    public void testGetAllGeneratedPasswordsNotFound() {
        when(passwordRepository.findAll()).thenReturn(Collections.emptyList());
        List<GeneratedPassword> result = passwordGenerationService.getAllGeneratedPasswords();
        assertEquals(Collections.emptyList(), result);
    }
}

