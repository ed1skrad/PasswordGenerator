package com.password.generator.bsuir.service;

import com.password.generator.bsuir.config.PasswordCache;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.repository.PasswordRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PasswordGenerationService.class})
class PasswordGenerationServiceTest {

    @Autowired
    private PasswordGenerationService passwordGenerationService;

    @MockBean
    private PasswordRepository passwordRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordCache passwordCache;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("test@example.com");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(user);
    }


    @Test
    void testGeneratePasswordWithCache() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.HARD, 15);
        String cachedPassword = "testCachedPassword";
        when(passwordCache.contains(eq(user.getId()))).thenReturn(true);
        when(passwordCache.get(eq(user.getId()))).thenReturn(cachedPassword);

        String generatedPassword = passwordGenerationService.generatePassword(dto);

        assertEquals(cachedPassword, generatedPassword);
        verify(passwordRepository, never()).save(any(GeneratedPassword.class));
        verify(passwordCache, times(1)).get(eq(user.getId()));
    }

    @Test
    void testGetPasswordById() {
        Long passwordId = 1L;
        String expectedPassword = "testPassword";
        when(passwordCache.contains(eq(passwordId))).thenReturn(false);
        when(passwordRepository.findById(passwordId)).thenReturn(Optional.of(new GeneratedPassword(passwordId, expectedPassword)));

        Optional<GeneratedPassword> generatedPassword = passwordGenerationService.getPasswordById(passwordId);

        assertTrue(generatedPassword.isPresent());
        assertEquals(expectedPassword, generatedPassword.get().getPassword());
        verify(passwordCache, times(1)).put(eq(passwordId), eq(expectedPassword));
    }

    @Test
    void testGetPasswordByIdWithCache() {
        Long passwordId = 1L;
        String expectedPassword = "testPassword";
        when(passwordCache.contains(eq(passwordId))).thenReturn(true);
        when(passwordCache.get(eq(passwordId))).thenReturn(expectedPassword);

        Optional<GeneratedPassword> generatedPassword = passwordGenerationService.getPasswordById(passwordId);

        assertTrue(generatedPassword.isPresent());
        assertEquals(expectedPassword, generatedPassword.get().getPassword());
        verify(passwordRepository, never()).findById(passwordId);
        verify(passwordCache, times(1)).get(eq(passwordId));
    }

    @Test
    void testGetPasswordByIdNotFound() {
        Long passwordId = 1L;
        when(passwordCache.contains(eq(passwordId))).thenReturn(false);
        when(passwordRepository.findById(passwordId)).thenReturn(Optional.empty());

        Optional<GeneratedPassword> generatedPassword = passwordGenerationService.getPasswordById(passwordId);

        assertFalse(generatedPassword.isPresent());
        verify(passwordCache, never()).put(eq(passwordId), anyString());
    }

    @Test
    void testGetPasswordsByDifficulty() {
        Difficulty difficulty = Difficulty.HARD;
        List<GeneratedPassword> expectedPasswords = new ArrayList<>();
        expectedPasswords.add(new GeneratedPassword(1L, "testPassword1"));
        expectedPasswords.add(new GeneratedPassword(2L, "testPassword2"));
        when(passwordRepository.findByDifficulty(difficulty)).thenReturn(expectedPasswords);

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        assertEquals(expectedPasswords, generatedPasswords);
        verify(passwordCache, times(2)).put(anyLong(), anyString());
    }

    @Test
    void testGetAllGeneratedPasswords() {
        List<GeneratedPassword> expectedPasswords = new ArrayList<>();
        expectedPasswords.add(new GeneratedPassword(1L, "testPassword1"));
        expectedPasswords.add(new GeneratedPassword(2L, "testPassword2"));
        when(passwordRepository.findAll()).thenReturn(expectedPasswords);

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswords();

        assertEquals(expectedPasswords, generatedPasswords);
        verify(passwordCache, times(2)).put(anyLong(), anyString());
    }

    @Test
    void testDeleteGeneratedPasswordById() {
        Long passwordId = 1L;

        passwordGenerationService.deleteGeneratedPasswordById(passwordId);

        verify(passwordRepository, times(1)).deleteById(passwordId);
    }

    @Test
    void testGetAllGeneratedPasswordsForUser() {
        List<GeneratedPassword> expectedPasswords = new ArrayList<>();
        expectedPasswords.add(new GeneratedPassword(1L, "testPassword1"));
        expectedPasswords.add(new GeneratedPassword(2L, "testPassword2"));
        when(passwordRepository.findAllByUserUsername(user.getUsername())).thenReturn(expectedPasswords);

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswordsForUser(user.getUsername());

        assertEquals(expectedPasswords, generatedPasswords);
    }
}
