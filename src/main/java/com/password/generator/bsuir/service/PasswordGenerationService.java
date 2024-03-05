package com.password.generator.bsuir.service;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.repository.PasswordRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordGenerationService {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";

    private static final Random random = new SecureRandom();
    private final PasswordRepository passwordRepository;

    private final UserService userService;

    @Autowired
    public PasswordGenerationService(PasswordRepository passwordRepository, UserService userService) {
        this.passwordRepository = passwordRepository;
        this.userService = userService;
    }


    public String generatePassword (PasswordGenerationDto dto){
        return generatePasswordString(dto);
    }

    private User getCurrentUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByUsername(username);
    }

    public String generatePasswordString (PasswordGenerationDto dto){
        StringBuilder password = new StringBuilder();
        String charPool = getCharacterPool(dto.getDifficulty());

        for (int i = 0; i < dto.getLength(); i++) {
            int randomIndex = random.nextInt(charPool.length());
            password.append(charPool.charAt(randomIndex));
        }

        String generatedPassword = password.toString();
        Difficulty difficulty = dto.getDifficulty();

        User currentUser = getCurrentUser();

        passwordRepository.save(new GeneratedPassword(generatedPassword, difficulty, currentUser));

        return generatedPassword;
    }



    private String getCharacterPool (Difficulty difficulty){
        return switch (difficulty) {
            case EASY -> LOWERCASE_CHARS;
            case NORMAL -> LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS;
            case HARD -> LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SYMBOLS;
        };
    }

    public Optional<GeneratedPassword> getPasswordById (Long id){
        return passwordRepository.findById(id);
    }

    public List<GeneratedPassword> getPasswordsByDifficulty (Difficulty difficulty){
        return passwordRepository.findByDifficulty(difficulty);
    }
    public List<GeneratedPassword> getAllGeneratedPasswords () {
        return passwordRepository.findAll();
    }

    public void deleteGeneratedPasswordById(Long generatedPasswordId) {
        passwordRepository.deleteById(generatedPasswordId);
    }
}

