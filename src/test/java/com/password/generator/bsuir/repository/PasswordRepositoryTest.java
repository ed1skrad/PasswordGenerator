package com.password.generator.bsuir.repository;

import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class PasswordRepositoryTest {

    @Mock
    private PasswordRepository passwordRepository;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testFindByDifficulty() {
        Difficulty difficulty = Difficulty.EASY;
        List<GeneratedPassword> passwords = new ArrayList<>();
        when(passwordRepository.findByDifficulty(difficulty)).thenReturn(passwords);
        assertEquals(passwords, passwordRepository.findByDifficulty(difficulty));
        verify(passwordRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
     void testFindAllByUserUsername() {
        String username = "testUser";
        List<GeneratedPassword> passwords = new ArrayList<>();
        when(passwordRepository.findAllByUserUsername(username)).thenReturn(passwords);
        assertEquals(passwords, passwordRepository.findAllByUserUsername(username));
        verify(passwordRepository, times(1)).findAllByUserUsername(username);
    }
}
