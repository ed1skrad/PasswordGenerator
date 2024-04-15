package com.password.generator.bsuir.model;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.security.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class GeneratedPasswordTest {

    @Test
     void testDefaultConstructor() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        assertNull(generatedPassword.getId());
        assertNull(generatedPassword.getPassword());
        assertNull(generatedPassword.getDifficulty());
        assertNull(generatedPassword.getUser());
    }

    @Test
     void testConstructorWithParameters() {
        String password = "testPassword";
        Difficulty difficulty = Difficulty.EASY;
        User user = new User();
        GeneratedPassword generatedPassword = new GeneratedPassword(password, difficulty, user);
        assertNull(generatedPassword.getId());
        assertEquals(password, generatedPassword.getPassword());
        assertEquals(difficulty, generatedPassword.getDifficulty());
        assertEquals(user, generatedPassword.getUser());
    }

    @Test
     void testConstructorWithIdAndPassword() {
        Long id = 1L;
        String password = "testPassword";
        GeneratedPassword generatedPassword = new GeneratedPassword(id, password);
        assertEquals(id, generatedPassword.getId());
        assertEquals(password, generatedPassword.getPassword());
        assertNull(generatedPassword.getDifficulty());
        assertNull(generatedPassword.getUser());
    }

    @Test
     void testConstructorWithIdAndPasswordAndDifficultyAndUser() {
        Long id = 1L;
        String password = "testPassword";
        Difficulty difficulty = Difficulty.EASY;
        User user = new User();
        GeneratedPassword generatedPassword = new GeneratedPassword(id, password, difficulty, user);
        assertEquals(id, generatedPassword.getId());
        assertEquals(password, generatedPassword.getPassword());
        assertEquals(difficulty, generatedPassword.getDifficulty());
        assertEquals(user, generatedPassword.getUser());
    }

    @Test
     void testSetId() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        Long id = 1L;
        generatedPassword.setId(id);
        assertEquals(id, generatedPassword.getId());
    }

    @Test
     void testSetPassword() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        String password = "testPassword";
        generatedPassword.setPassword(password);
        assertEquals(password, generatedPassword.getPassword());
    }

    @Test
     void testSetDifficulty() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        Difficulty difficulty = Difficulty.EASY;
        generatedPassword.setDifficulty(difficulty);
        assertEquals(difficulty, generatedPassword.getDifficulty());
    }

    @Test
     void testSetUser() {
        GeneratedPassword generatedPassword = new GeneratedPassword();
        User user = new User();
        generatedPassword.setUser(user);
        assertEquals(user, generatedPassword.getUser());
    }
}
