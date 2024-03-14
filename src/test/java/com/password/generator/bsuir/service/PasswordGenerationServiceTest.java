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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PasswordGenerationServiceTest {

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

    @MockBean
    private PasswordGenerationService passwordGenerationService;
    @Mock
    private RequestCounterService requestCounterService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        passwordGenerationService = new PasswordGenerationService(passwordRepository, userService, passwordCache, requestCounterService);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGeneratePassword() {
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
    void testGetPasswordByIdPasswordNotInCache() {
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
    void testGetPasswordsByDifficulty() {
        Difficulty difficulty = Difficulty.NORMAL;
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        when(passwordRepository.findByDifficulty(difficulty)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        assertEquals(generatedPasswords, result);
        verify(passwordRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
    void testDeleteGeneratedPasswordById() {
        Long id = 1L;
        passwordGenerationService.deleteGeneratedPasswordById(id);

        verify(passwordRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllGeneratedPasswordsForUser() {
        String username = "testUser";
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        when(passwordRepository.findAllByUserUsername(username)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getAllGeneratedPasswordsForUser(username);

        assertEquals(generatedPasswords, result);
        verify(passwordRepository, times(1)).findAllByUserUsername(username);
    }
    @Test
    void testDeleteAllGeneratedPasswords() {
        int n = 5;
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            GeneratedPassword generatedPassword = new GeneratedPassword();
            generatedPassword.setId((long) i);
            generatedPasswords.add(generatedPassword);
        }
        when(passwordRepository.findTopNOrderByIdDesc()).thenReturn(generatedPasswords);
        doNothing().when(passwordRepository).deleteById(anyLong());
        doNothing().when(passwordCache).remove(anyLong());

        passwordGenerationService.deleteAllGeneratedPasswords();

        verify(passwordRepository, times(1)).findTopNOrderByIdDesc();
        verify(passwordRepository, times(n)).deleteById(anyLong());
        verify(passwordCache, times(n)).remove(anyLong());
    }

    @Test
    void testGetPasswordsByDifficulty_NoPasswordsFound() {
        Difficulty difficulty = Difficulty.HARD;
        when(passwordRepository.findByDifficulty(difficulty)).thenReturn(Collections.emptyList());

        List<GeneratedPassword> result = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        assertTrue(result.isEmpty());
        verify(passwordRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
    void testGeneratePasswordString() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(user);
        when(passwordRepository.save(any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String generatedPassword = passwordGenerationService.generatePasswordString(dto);

        assertNotNull(generatedPassword);
        assertEquals(10, generatedPassword.length());
        verify(passwordRepository, times(1)).save(any(GeneratedPassword.class));
        verify(passwordCache, never()).put(anyLong(), anyString());
    }

    @Test
    void testGenerateBulkPasswords() {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(2, Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(user);
        when(passwordRepository.save(any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        assertNotNull(generatedPasswords);
        assertEquals(2, generatedPasswords.size());
        verify(passwordRepository, times(2)).save(any(GeneratedPassword.class));
        verify(passwordCache, never()).put(anyLong(), anyString());
    }

    @Test
    void testGeneratePasswords() {
        List<PasswordGenerationDto> dtos = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(user);
        when(passwordRepository.save(any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));
        dtos.add(new PasswordGenerationDto(Difficulty.NORMAL, 10));
        int count = 2;

        List<String> generatedPasswords = passwordGenerationService.generatePasswords(dtos, count);

        assertNotNull(generatedPasswords);
        assertEquals(2, generatedPasswords.size());
        verify(passwordRepository, times(2)).save(any(GeneratedPassword.class));
        verify(passwordCache, never()).put(anyLong(), anyString());
    }

    @Test
    void testGetCurrentUser() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userService.getByUsername(username)).thenReturn(user);

        User currentUser = passwordGenerationService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(user, currentUser);
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(userService, times(1)).getByUsername(username);
    }

    @Test
    void testGetCharacterPool() {
        PasswordGenerationService passwordGenerationService = new PasswordGenerationService(passwordRepository, userService, passwordCache, requestCounterService);
        assertEquals("abcdefghijklmnopqrstuvwxyz", passwordGenerationService.getCharacterPool(Difficulty.EASY));
        assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", passwordGenerationService.getCharacterPool(Difficulty.NORMAL));
        assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+", passwordGenerationService.getCharacterPool(Difficulty.HARD));
    }
}