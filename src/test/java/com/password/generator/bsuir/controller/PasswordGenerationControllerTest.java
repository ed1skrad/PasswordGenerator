package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.service.PasswordGenerationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PasswordGenerationControllerTest {

    @Mock
    private PasswordGenerationService passwordGenerationService;

    @InjectMocks
    private PasswordGenerationController passwordGenerationController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGeneratePasswordValidInput() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 8);
        when(passwordGenerationService.generatePassword(dto)).thenReturn("password");
        ResponseEntity<String> response = passwordGenerationController.generatePassword(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("password", response.getBody());
    }

    @Test
    public void testGeneratePasswordInvalidInput() {
        PasswordGenerationDto dto = new PasswordGenerationDto(null, -1);
        ResponseEntity<String> response = passwordGenerationController.generatePassword(dto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllGeneratedPasswordsNotFound() {
        when(passwordGenerationService.getAllGeneratedPasswords()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = passwordGenerationController.getAllGeneratedPasswords();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Password not found", response.getBody());
    }
}
