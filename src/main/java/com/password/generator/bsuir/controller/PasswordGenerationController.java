package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import com.password.generator.bsuir.service.PasswordGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/password")
public class PasswordGenerationController {

    private final PasswordGenerationService passwordGenerationService;

    @Autowired
    public PasswordGenerationController(PasswordGenerationService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    @PostMapping("/generatePassword")
    public ResponseEntity<String> generatePassword(@RequestBody PasswordGenerationDto dto) {
        if (dto.getLength() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password length should not more than 255 characters");
        }
        String generatedPassword = passwordGenerationService.generatePassword(dto);
        if (generatedPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to generate password");
        }
        return ResponseEntity.ok(generatedPassword);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getPasswordById(@PathVariable Long id) {
        Optional<GeneratedPassword> generatedPassword = passwordGenerationService.getPasswordById(id);
        if (generatedPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found for id: " + id);
        }
        return ResponseEntity.ok(generatedPassword);
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<Object> getPasswordsByDifficulty(@PathVariable Difficulty difficulty) {
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getPasswordsByDifficulty(difficulty);
        if (generatedPasswords.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passwords not found for difficulty: " + difficulty);
        }
        return ResponseEntity.ok(generatedPasswords);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllGeneratedPasswords() {
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswords();
        if (generatedPasswords.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found");
        }
        return ResponseEntity.ok(generatedPasswords);
    }

    @DeleteMapping("/delete/{passwordId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteGeneratedPassword(@PathVariable Long passwordId) {
        passwordGenerationService.deleteGeneratedPasswordById(passwordId);
        return ResponseEntity.ok("Generated password deleted successfully");
    }

    @GetMapping("/user/{username}/passwords")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GeneratedPassword>> getAllGeneratedPasswordsForUser(@PathVariable String username) {
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswordsForUser(username);
        if (generatedPasswords.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(generatedPasswords);
    }

    @PostMapping("/generatePasswords/{count}")
    public ResponseEntity<List<String>> generatePasswords(@RequestBody List<PasswordGenerationDto> dtos, @PathVariable int count) {
        List<String> generatedPasswords = passwordGenerationService.generatePasswords(dtos, count);
        return ResponseEntity.ok(generatedPasswords);
    }

    @PostMapping("/generate-bulk-passwords")
    public ResponseEntity<Object> generateBulkPasswords(@RequestBody BulkPasswordGenerationDto bulkPasswordGenerationDto) {
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.generateBulkPasswords(bulkPasswordGenerationDto);

        if (generatedPasswords.isEmpty()) {
            return new ResponseEntity<>("No passwords generated", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(generatedPasswords, HttpStatus.OK);
    }

    @DeleteMapping("/delete-bulk-passwords/{n}")
    public ResponseEntity<Object> deleteBulkPasswords(@PathVariable int n){
        passwordGenerationService.deleteLastGeneratedPasswords(n);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
