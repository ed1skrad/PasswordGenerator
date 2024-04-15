package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.exception.PasswordGenerationException;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.service.PasswordGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class PasswordGenerationControllerTest {

    @Mock
    private PasswordGenerationService passwordGenerationService;

    private PasswordGenerationController passwordGenerationController;

    @BeforeEach
     void setup() {
        passwordGenerationController = new PasswordGenerationController(passwordGenerationService);
    }

    @Test
     void testGeneratePassword_success() throws PasswordGenerationException {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 10);
        String expectedGeneratedPassword = "Test123";
        when(passwordGenerationService.generatePassword(dto)).thenReturn(expectedGeneratedPassword);

        ResponseEntity<String> response = passwordGenerationController.generatePassword(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedGeneratedPassword, response.getBody());
        verify(passwordGenerationService, times(1)).generatePassword(dto);
    }

    @Test
     void testGeneratePassword_lengthExceeded() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 256);

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.generatePassword(dto));
        verify(passwordGenerationService, never()).generatePassword(dto);
    }

    @Test
     void testGetPasswordById_success() throws PasswordGenerationException {
        Long id = 1L;
        GeneratedPassword expectedGeneratedPassword = new GeneratedPassword();
        expectedGeneratedPassword.setId(id);
        expectedGeneratedPassword.setPassword("Test123");
        when(passwordGenerationService.getPasswordById(id)).thenReturn(Optional.of(expectedGeneratedPassword));

        ResponseEntity<Object> response = passwordGenerationController.getPasswordById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<GeneratedPassword> actualGeneratedPasswordOptional = (Optional<GeneratedPassword>) response.getBody();
        assertTrue(actualGeneratedPasswordOptional.isPresent());
        assertEquals(expectedGeneratedPassword, actualGeneratedPasswordOptional.get());
        verify(passwordGenerationService, times(1)).getPasswordById(id);
    }


    @Test
     void testGetPasswordById_notFound() throws PasswordGenerationException {
        Long id = 1L;
        when(passwordGenerationService.getPasswordById(id)).thenReturn(Optional.empty());

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.getPasswordById(id));
        verify(passwordGenerationService, times(1)).getPasswordById(id);
    }

    @Test
     void testGetPasswordsByDifficulty_success() throws PasswordGenerationException {
        Difficulty difficulty = Difficulty.EASY;
        List<GeneratedPassword> expectedGeneratedPasswords = new ArrayList<>();
        expectedGeneratedPasswords.add(new GeneratedPassword());
        expectedGeneratedPasswords.add(new GeneratedPassword());
        when(passwordGenerationService.getPasswordsByDifficulty(difficulty)).thenReturn(expectedGeneratedPasswords);

        ResponseEntity<Object> response = passwordGenerationController.getPasswordsByDifficulty(difficulty);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedGeneratedPasswords, response.getBody());
        verify(passwordGenerationService, times(1)).getPasswordsByDifficulty(difficulty);
    }

    @Test
     void testGetPasswordsByDifficulty_notFound() throws PasswordGenerationException {
        Difficulty difficulty = Difficulty.EASY;
        when(passwordGenerationService.getPasswordsByDifficulty(difficulty)).thenReturn(new ArrayList<>());

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.getPasswordsByDifficulty(difficulty));
        verify(passwordGenerationService, times(1)).getPasswordsByDifficulty(difficulty);
    }

    @Test
     void testGetAllGeneratedPasswords_success() throws PasswordGenerationException {
        List<GeneratedPassword> expectedGeneratedPasswords = new ArrayList<>();
        expectedGeneratedPasswords.add(new GeneratedPassword());
        expectedGeneratedPasswords.add(new GeneratedPassword());
        when(passwordGenerationService.getAllGeneratedPasswords()).thenReturn(expectedGeneratedPasswords);

        ResponseEntity<Object> response = passwordGenerationController.getAllGeneratedPasswords();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedGeneratedPasswords, response.getBody());
        verify(passwordGenerationService, times(1)).getAllGeneratedPasswords();
    }

    @Test
     void testGetAllGeneratedPasswords_notFound() throws PasswordGenerationException {
        when(passwordGenerationService.getAllGeneratedPasswords()).thenReturn(new ArrayList<>());

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.getAllGeneratedPasswords());
        verify(passwordGenerationService, times(1)).getAllGeneratedPasswords();
    }

    @Test
     void testDeleteGeneratedPassword_success() {
        Long passwordId = 1L;

        passwordGenerationController.deleteGeneratedPassword(passwordId);

        verify(passwordGenerationService, times(1)).deleteGeneratedPasswordById(passwordId);
    }

    @Test
     void testGetAllGeneratedPasswordsForUser_success() throws PasswordGenerationException {
        String username = "testUser";
        List<GeneratedPassword> expectedGeneratedPasswords = new ArrayList<>();
        expectedGeneratedPasswords.add(new GeneratedPassword());
        expectedGeneratedPasswords.add(new GeneratedPassword());
        when(passwordGenerationService.getAllGeneratedPasswordsForUser(username)).thenReturn(expectedGeneratedPasswords);

        ResponseEntity<Object> response = passwordGenerationController.getAllGeneratedPasswordsForUser(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedGeneratedPasswords, response.getBody());
        verify(passwordGenerationService, times(1)).getAllGeneratedPasswordsForUser(username);
    }

    @Test
     void testGetAllGeneratedPasswordsForUser_notFound() throws PasswordGenerationException {
        String username = "testUser";
        when(passwordGenerationService.getAllGeneratedPasswordsForUser(username)).thenReturn(new ArrayList<>());

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.getAllGeneratedPasswordsForUser(username));
        verify(passwordGenerationService, times(1)).getAllGeneratedPasswordsForUser(username);
    }

    @Test
     void testGenerateBulkPasswords_success() throws PasswordGenerationException {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(5, Difficulty.EASY, 10);
        List<GeneratedPassword> expectedGeneratedPasswords = new ArrayList<>();
        expectedGeneratedPasswords.add(new GeneratedPassword());
        expectedGeneratedPasswords.add(new GeneratedPassword());
        when(passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto)).thenReturn(expectedGeneratedPasswords);

        ResponseEntity<Object> response = passwordGenerationController.generateBulkPasswords(bulkPasswordGenerationDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedGeneratedPasswords, response.getBody());
        verify(passwordGenerationService, times(1)).generateBulkPasswords(bulkPasswordGenerationDto);
    }

    @Test
     void testGenerateBulkPasswords_noPasswordsGenerated() throws PasswordGenerationException {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(5, Difficulty.EASY, 10);
        when(passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto)).thenReturn(new ArrayList<>());

        assertThrows(PasswordGenerationException.class, () -> passwordGenerationController.generateBulkPasswords(bulkPasswordGenerationDto));
        verify(passwordGenerationService, times(1)).generateBulkPasswords(bulkPasswordGenerationDto);
    }

    @Test
     void testDeleteBulkPasswords_success() {
        passwordGenerationController.deleteBulkPasswords();

        verify(passwordGenerationService, times(1)).deleteAllGeneratedPasswords();
    }
}
