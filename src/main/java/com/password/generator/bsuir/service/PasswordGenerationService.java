package com.password.generator.bsuir.service;

import com.password.generator.bsuir.config.PasswordCache;
import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.repository.PasswordRepository;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.UserService;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Service class for handling password generation operations.
 */
@Service
public class PasswordGenerationService {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";

    private static final Random random = new SecureRandom();
    private final PasswordRepository passwordRepository;
    private final UserService userService;
    private final PasswordCache passwordCache;
    private final RequestCounterService requestCounterService;

    /**
     *Constructor.
     */
    @Autowired
    public PasswordGenerationService(PasswordRepository passwordRepository,
                                     UserService userService,
                                     PasswordCache passwordCache,
                                     RequestCounterService requestCounterService) {
        this.passwordRepository = passwordRepository;
        this.userService = userService;
        this.passwordCache = passwordCache;
        this.requestCounterService = requestCounterService;
    }

    /**
     * Generates a password based on the provided parameters.
     *
     * @param dto the password generation parameters.
     * @return the generated password.
     */
    public String generatePassword(PasswordGenerationDto dto) {
        return generatePasswordString(dto);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the currently authenticated user.
     */
    User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByUsername(username);
    }

    /**
     * Generates a password string based on the provided parameters.
     *
     * @param dto the password generation parameters.
     * @return the generated password string.
     */
    String generatePasswordString(PasswordGenerationDto dto) {
        User currentUser = getCurrentUser();
        StringBuilder password = new StringBuilder();
        String charPool = getCharacterPool(dto.getDifficulty());

        for (int i = 0; i < dto.getLength(); i++) {
            int randomIndex = random.nextInt(charPool.length());
            password.append(charPool.charAt(randomIndex));
        }

        String generatedPassword = password.toString();
        Difficulty difficulty = dto.getDifficulty();

        passwordRepository.save(new GeneratedPassword(generatedPassword, difficulty, currentUser));
        requestCounterService.increment();
        return generatedPassword;
    }

    /**
     * Retrieves the character pool for the provided difficulty level.
     *
     * @param difficulty the difficulty level.
     * @return the character pool.
     */
    String getCharacterPool(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> LOWERCASE_CHARS;
            case NORMAL -> LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS;
            case HARD -> LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SYMBOLS;
        };
    }

    /**
     * Retrieves a generated password by its ID.
     *
     * @param id the ID of the generated password.
     * @return the generated password.
     */
    public Optional<GeneratedPassword> getPasswordById(Long id) {
        if (passwordCache.contains(id)) {
            String cachedPassword = passwordCache.get(id);
            GeneratedPassword cachedGeneratedPassword = new GeneratedPassword(id, cachedPassword);
            Optional<GeneratedPassword> optionalGeneratedPassword = passwordRepository.findById(id);
            if (optionalGeneratedPassword.isPresent()) {
                GeneratedPassword password = optionalGeneratedPassword.get();
                cachedGeneratedPassword.setDifficulty(password.getDifficulty());
                cachedGeneratedPassword.setUser(password.getUser());
            }
            return Optional.of(cachedGeneratedPassword);
        } else {
            Optional<GeneratedPassword> generatedPassword = passwordRepository.findById(id);
            generatedPassword.ifPresent(password ->
                    passwordCache.put(id, String.valueOf(password)));
            return generatedPassword;
        }
    }

    /**
     * Retrieves a list of generated passwords by difficulty level.
     *
     * @param difficulty the difficulty level.
     * @return the list of generated passwords.
     */
    public List<GeneratedPassword> getPasswordsByDifficulty(Difficulty difficulty) {
        List<GeneratedPassword> generatedPasswords =
                passwordRepository.findByDifficulty(difficulty);

        if (generatedPasswords.size() > passwordCache.getAllCachedPasswords().size()) {
            generatedPasswords.forEach(password -> {
                if (!passwordCache.contains(password.getId())) {
                    passwordCache.put(password.getId(), password.getPassword());
                }
            });
        }

        return generatedPasswords;
    }

    /**
     * Retrieves a list of all generated passwords.
     *
     * @return the list of all generated passwords.
     */
    public List<GeneratedPassword> getAllGeneratedPasswords() {
        List<GeneratedPassword> generatedPasswords = passwordRepository.findAll();

        if (generatedPasswords.size() > passwordCache.getAllCachedPasswords().size()) {
            generatedPasswords.forEach(password -> {
                if (!passwordCache.contains(password.getId())) {
                    passwordCache.put(password.getId(), password.getPassword());
                }
            });
        }

        return generatedPasswords;
    }

    /**
     * Deletes a generated password by its ID.
     *
     * @param generatedPasswordId the ID of the generated password to delete.
     */
    public void deleteGeneratedPasswordById(Long generatedPasswordId) {
        passwordRepository.deleteById(generatedPasswordId);
    }

    /**
     * Retrieves a list of all generated passwords for the currently authenticated user.
     *
     * @return the list of all generated passwords for the currently authenticated user.
     */
    public List<GeneratedPassword> getAllGeneratedPasswordsForUser(String username) {
        return passwordRepository.findAllByUserUsername(username);
    }

    /**
     * Generates a list of bulk passwords based on the provided parameters.
     *
     * @param bulkPasswordGenerationDto the bulk password generation parameters.
     * @return the list of generated passwords.
     */
    public List<GeneratedPassword> generateBulkPasswords(BulkPasswordGenerationDto
                                                                 bulkPasswordGenerationDto) {
        List<GeneratedPassword> generatedPasswords = Stream.generate(() -> {
                    PasswordGenerationDto passwordGenerationDto =
                            new PasswordGenerationDto(bulkPasswordGenerationDto.getDifficulty(),
                                    bulkPasswordGenerationDto.getLength());
                    String generatedPassword = generatePasswordString(passwordGenerationDto);
                    User currentUser = getCurrentUser();
                    return new GeneratedPassword(generatedPassword,
                            bulkPasswordGenerationDto.getDifficulty(), currentUser);
                })
                .limit(bulkPasswordGenerationDto.getCount())
                .toList();

        passwordRepository.saveAll(generatedPasswords);

        return generatedPasswords;
    }

    /**
     * Deletes all generated passwords.
     */
    public void deleteAllGeneratedPasswords() {
        List<Long> passwordIds = passwordRepository.findAll().stream()
                .map(GeneratedPassword::getId)
                .toList();

        passwordRepository.deleteAllByIdInBatch(passwordIds);
        passwordCache.clear();
    }

    /**
     * Generates a list of passwords based on a list of password generation parameters.
     *
     * @param dtos the list of password generation parameters.
     * @param count the number of passwords to generate for each set of parameters.
     * @return the list of generated passwords.
     */
    public List<String> generatePasswords(List<PasswordGenerationDto> dtos, int count) {
        return dtos.stream()
                .flatMap(dto -> Stream.generate(() -> generatePasswordString(dto))
                        .limit(count))
                .toList();
    }
}
