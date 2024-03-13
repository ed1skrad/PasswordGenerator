package com.password.generator.bsuir.service;

import com.password.generator.bsuir.config.PasswordCache;
import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.repository.PasswordRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PasswordGenerationServiceTest {

    private PasswordGenerationService passwordGenerationService;

    @Mock
    private PasswordRepository passwordRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordCache passwordCache;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        passwordGenerationService = new PasswordGenerationService(passwordRepository, userService, passwordCache);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGeneratePassword() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(user);
        when(passwordRepository.save(any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String generatedPassword = passwordGenerationService.generatePassword(dto);

        assertNotNull(generatedPassword);
        assertEquals(10, generatedPassword.length());
        verify(passwordRepository, times(1)).save(any(GeneratedPassword.class));
        verify(passwordCache, never()).put(anyLong(), anyString());
    }

    @Test
    public void testGetPasswordById_PasswordNotInCache() {
        Long id = 1L;
        String password = "password";
        GeneratedPassword generatedPassword = new GeneratedPassword();
        generatedPassword.setId(id);
        generatedPassword.setPassword(password);
        when(passwordCache.contains(id)).thenReturn(false);
        when(passwordRepository.findById(id)).thenReturn(Optional.of(generatedPassword));

        Optional<GeneratedPassword> result = passwordGenerationService.getPasswordById(id);

        assertEquals(Optional.of(generatedPassword), result);
        verify(passwordRepository, times(1)).findById(anyLong());
        verify(passwordCache, times(1)).contains(anyLong());
        verify(passwordCache, times(1)).put(anyLong(), anyString());
    }

    @Test
    public void testGetPasswordsByDifficulty() {
        Difficulty difficulty = Difficulty.NORMAL;
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        when(passwordRepository.findByDifficulty(difficulty)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        assertEquals(generatedPasswords, result);
        verify(passwordRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
    public void testDeleteGeneratedPasswordById() {
        Long id = 1L;
        passwordGenerationService.deleteGeneratedPasswordById(id);

        verify(passwordRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetAllGeneratedPasswordsForUser() {
        String username = "testUser";
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        when(passwordRepository.findAllByUserUsername(username)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getAllGeneratedPasswordsForUser(username);

        assertEquals(generatedPasswords, result);
        verify(passwordRepository, times(1)).findAllByUserUsername(username);
    }
    @Test
    public void testDeleteAllGeneratedPasswords() {
        int n = 5;
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            GeneratedPassword generatedPassword = new GeneratedPassword();
            generatedPassword.setId((long) i);
            generatedPasswords.add(generatedPassword);
        }
        when(passwordRepository.findTopNOrderByIdDesc(n)).thenReturn(generatedPasswords);
        doNothing().when(passwordRepository).deleteById(anyLong());
        doNothing().when(passwordCache).remove(anyLong());

        passwordGenerationService.deleteAllGeneratedPasswords(n);

        verify(passwordRepository, times(1)).findTopNOrderByIdDesc(n);
        verify(passwordRepository, times(n)).deleteById(anyLong());
        verify(passwordCache, times(n)).remove(anyLong());
    }

}
