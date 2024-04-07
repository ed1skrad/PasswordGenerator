package com.password.generator.bsuir.service;

import com.password.generator.bsuir.config.PasswordCache;
import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.repository.PasswordRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;

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
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String generatedPassword = passwordGenerationService.generatePassword(dto);

        Assertions.assertNotNull(generatedPassword);
        Assertions.assertEquals(10, generatedPassword.length());
        Mockito.verify(passwordRepository, Mockito.times(1)).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetPasswordByIdPasswordNotInCache() {
        Long id = 1L;
        String password = "password";
        GeneratedPassword generatedPassword = new GeneratedPassword();
        generatedPassword.setId(id);
        generatedPassword.setPassword(password);
        Mockito.when(passwordCache.contains(id)).thenReturn(false);
        Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.of(generatedPassword));

        Optional<GeneratedPassword> result = passwordGenerationService.getPasswordById(id);

        Assertions.assertEquals(Optional.of(generatedPassword), result);
        Mockito.verify(passwordRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(passwordCache, Mockito.times(1)).contains(ArgumentMatchers.anyLong());
        Mockito.verify(passwordCache, Mockito.times(1)).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetPasswordsByDifficulty() {
        Difficulty difficulty = Difficulty.NORMAL;
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        Mockito.when(passwordRepository.findByDifficulty(difficulty)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        Assertions.assertEquals(generatedPasswords, result);
        Mockito.verify(passwordRepository, Mockito.times(1)).findByDifficulty(difficulty);
    }

    @Test
    void testDeleteGeneratedPasswordById() {
        Long id = 1L;
        passwordGenerationService.deleteGeneratedPasswordById(id);

        Mockito.verify(passwordRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testGetAllGeneratedPasswordsForUser() {
        String username = "testUser";
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        Mockito.when(passwordRepository.findAllByUserUsername(username)).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getAllGeneratedPasswordsForUser(username);

        Assertions.assertEquals(generatedPasswords, result);
        Mockito.verify(passwordRepository, Mockito.times(1)).findAllByUserUsername(username);
    }

    @Test
    void testGetPasswordsByDifficulty_NoPasswordsFound() {
        Difficulty difficulty = Difficulty.HARD;
        Mockito.when(passwordRepository.findByDifficulty(difficulty)).thenReturn(Collections.emptyList());

        List<GeneratedPassword> result = passwordGenerationService.getPasswordsByDifficulty(difficulty);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(passwordRepository, Mockito.times(1)).findByDifficulty(difficulty);
    }

    @Test
    void testGeneratePasswordString() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String generatedPassword = passwordGenerationService.generatePasswordString(dto);

        Assertions.assertNotNull(generatedPassword);
        Assertions.assertEquals(10, generatedPassword.length());
        Mockito.verify(passwordRepository, Mockito.times(1)).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGenerateBulkPasswords() {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(2, Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        Assertions.assertNotNull(generatedPasswords);
        Assertions.assertEquals(2, generatedPasswords.size());
        Mockito.verify(passwordRepository, Mockito.times(2)).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }
    @Test
    void testGetCurrentUser() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(username);
        Mockito.when(userService.getByUsername(username)).thenReturn(user);

        User currentUser = passwordGenerationService.getCurrentUser();

        Assertions.assertNotNull(currentUser);
        Assertions.assertEquals(user, currentUser);
        Mockito.verify(securityContext, Mockito.times(1)).getAuthentication();
        Mockito.verify(authentication, Mockito.times(1)).getName();
        Mockito.verify(userService, Mockito.times(1)).getByUsername(username);
    }

    @Test
    void testGetCharacterPool() {
        PasswordGenerationService passwordGenerationService = new PasswordGenerationService(passwordRepository, userService, passwordCache, requestCounterService);
        Assertions.assertEquals("abcdefghijklmnopqrstuvwxyz", passwordGenerationService.getCharacterPool(Difficulty.EASY));
        Assertions.assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", passwordGenerationService.getCharacterPool(Difficulty.NORMAL));
        Assertions.assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+", passwordGenerationService.getCharacterPool(Difficulty.HARD));
    }

    @Test
    void testGetPasswordByIdPasswordNotFound() {
        Long id = 1L;
        Mockito.when(passwordCache.contains(id)).thenReturn(false);
        Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.empty());

        Optional<GeneratedPassword> result = passwordGenerationService.getPasswordById(id);

        Assertions.assertEquals(Optional.empty(), result);
        Mockito.verify(passwordRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(passwordCache, Mockito.times(1)).contains(ArgumentMatchers.anyLong());
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetAllGeneratedPasswords() {
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        Mockito.when(passwordRepository.findAll()).thenReturn(generatedPasswords);

        List<GeneratedPassword> result = passwordGenerationService.getAllGeneratedPasswords();

        Assertions.assertEquals(generatedPasswords, result);
        Mockito.verify(passwordRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testDeleteGeneratedPasswordByIdPasswordNotFound() {
        Long id = 1L;
        Mockito.doNothing().when(passwordRepository).deleteById(id);

        passwordGenerationService.deleteGeneratedPasswordById(id);

        Mockito.verify(passwordRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testGeneratePasswords() {
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));
        List<PasswordGenerationDto> dtos = List.of(
                new PasswordGenerationDto(Difficulty.EASY, 5),
                new PasswordGenerationDto(Difficulty.EASY, 3),
                new PasswordGenerationDto(Difficulty.EASY, 2)
        );
        int count = 3;
        List<String> passwords = passwordGenerationService.generatePasswords(dtos, count);

        Assertions.assertEquals(9, passwords.size());
        Assertions.assertTrue(passwords.stream()
                .allMatch(password -> password.length() == 5 || password.length() == 3 || password.length() == 2));
        Assertions.assertTrue(passwords.stream()
                .allMatch(password -> password.matches("^[a-z]*$") || password.matches("^[0-9]*$") || password.matches("^[!@#]*$")));
    }
    @Test
    void testGeneratePasswordEasy() {
        PasswordGenerationDto dto = new PasswordGenerationDto(Difficulty.EASY, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String generatedPassword = passwordGenerationService.generatePassword(dto);

        Assertions.assertNotNull(generatedPassword);
        Assertions.assertEquals(10, generatedPassword.length());
        Mockito.verify(passwordRepository, Mockito.times(1)).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGenerateBulkPasswordsDifferentDifficulty() {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(2, Difficulty.HARD, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        Assertions.assertNotNull(generatedPasswords);
        Assertions.assertEquals(2, generatedPasswords.size());
        Mockito.verify(passwordRepository, Mockito.times(2)).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testDeleteGeneratedPasswordByIdPasswordNotExists() {
        Long id = 1L;
        Mockito.doThrow(new RuntimeException()).when(passwordRepository).deleteById(id);

        Assertions.assertThrows(RuntimeException.class, () -> passwordGenerationService.deleteGeneratedPasswordById(id));

        Mockito.verify(passwordRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testGenerateBulkPasswordsWithZeroCount() {
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(0, Difficulty.NORMAL, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        Mockito.when(passwordRepository.save(ArgumentMatchers.any(GeneratedPassword.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        Assertions.assertTrue(generatedPasswords.isEmpty());
        Mockito.verify(passwordRepository, Mockito.never()).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testGeneratePasswordString_NullDifficulty() {
        PasswordGenerationDto dto = new PasswordGenerationDto(null, 10);
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);

        Assertions.assertThrows(NullPointerException.class, () -> passwordGenerationService.generatePasswordString(dto));

        Mockito.verify(passwordRepository, Mockito.never()).save(ArgumentMatchers.any(GeneratedPassword.class));
        Mockito.verify(passwordCache, Mockito.never()).put(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void testDeleteAllGeneratedPasswords() {
        List<GeneratedPassword> generatedPasswords = new ArrayList<>();
        generatedPasswords.add(new GeneratedPassword());
        generatedPasswords.add(new GeneratedPassword());
        Mockito.when(passwordRepository.findAll()).thenReturn(generatedPasswords);

        passwordGenerationService.deleteAllGeneratedPasswords();

        Mockito.verify(passwordRepository, Mockito.times(1)).findAll();
        Mockito.verify(passwordRepository, Mockito.times(1)).deleteAllByIdInBatch(ArgumentMatchers.anyList());
        Mockito.verify(passwordCache, Mockito.times(1)).clear();
    }

    @Test
    void testGenerateBulkPasswords_Difficulty() {
        User user = new User();
        user.setId(1L);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.getByUsername("testUser")).thenReturn(user);
        BulkPasswordGenerationDto bulkPasswordGenerationDto = new BulkPasswordGenerationDto(5, Difficulty.HARD, 10);
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        Assertions.assertTrue(generatedPasswords.stream().allMatch(password -> password.getDifficulty() == Difficulty.HARD));
    }
}