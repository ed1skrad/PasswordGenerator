package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.exception.PasswordGenerationException;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.service.PasswordGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/password")
public class PasswordGenerationController {

    private static final String errorMessage = "Something went wrong";

    private final PasswordGenerationService passwordGenerationService;

    @Autowired
    public PasswordGenerationController(PasswordGenerationService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    @PostMapping("/generatePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> generatePassword(@RequestBody PasswordGenerationDto dto) {
        try {
            if (dto.getLength() > 255) {
                throw new PasswordGenerationException("Password length should not more than 255 characters");
            }
            String generatedPassword = passwordGenerationService.generatePassword(dto);
            if (generatedPassword.isEmpty()) {
                throw new PasswordGenerationException("Failed to generate password");
            }
            return ResponseEntity.ok(generatedPassword);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getPasswordById(@PathVariable Long id) {
        try {
            Optional<GeneratedPassword> generatedPassword = passwordGenerationService.getPasswordById(id);
            if (generatedPassword.isEmpty()) {
                throw new PasswordGenerationException("Password not found for id: " + id);
            }
            return ResponseEntity.ok(generatedPassword);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage + e.getMessage());
        }
    }

    @GetMapping("/difficulty/{difficulty}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getPasswordsByDifficulty(@PathVariable Difficulty difficulty) {
        try {
            List<GeneratedPassword> generatedPasswords = passwordGenerationService.getPasswordsByDifficulty(difficulty);
            if (generatedPasswords.isEmpty()) {
                throw new PasswordGenerationException("Passwords not found for difficulty: " + difficulty);
            }
            return ResponseEntity.ok(generatedPasswords);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage + e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllGeneratedPasswords() {
        try {
            List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswords();
            if (generatedPasswords.isEmpty()) {
                throw new PasswordGenerationException("Password not found");
            }
            return ResponseEntity.ok(generatedPasswords);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{passwordId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteGeneratedPassword(@PathVariable Long passwordId) {
        try {
            passwordGenerationService.deleteGeneratedPasswordById(passwordId);
            return ResponseEntity.ok("Generated password deleted successfully");
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage + e.getMessage());
        }
    }

    @GetMapping("/user/{username}/passwords")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllGeneratedPasswordsForUser(@PathVariable String username) {
        try {
            List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswordsForUser(username);
            if (generatedPasswords.isEmpty()) {
                throw new PasswordGenerationException("Passwords for user " + username + " not found");
            }
            return ResponseEntity.ok(generatedPasswords);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage + e.getMessage());
        }
    }

    @PostMapping("/generateBulkPasswords")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> generateBulkPasswords(@RequestBody BulkPasswordGenerationDto bulkPasswordGenerationDto) {
        try {
            List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);
            if (generatedPasswords.isEmpty()) {
                throw new PasswordGenerationException("No passwords generated");
            }
            return new ResponseEntity<>(generatedPasswords, HttpStatus.OK);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + e.getMessage());
        }
    }

    @DeleteMapping("/deleteAllBulkPasswords")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteBulkPasswords(){
        try {
            passwordGenerationService.deleteAllGeneratedPasswords();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage + e.getMessage());
        }
    }

    @PostMapping("/generatePasswords/{count}")
    public ResponseEntity<List<String>> generatePasswords(@RequestBody List<PasswordGenerationDto> dtos, @PathVariable int count) {
        try {
            List<String> generatedPasswords = passwordGenerationService.generatePasswords(dtos, count);
            return ResponseEntity.ok(generatedPasswords);
        } catch (PasswordGenerationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(errorMessage + e.getMessage()));
        }
    }
}
