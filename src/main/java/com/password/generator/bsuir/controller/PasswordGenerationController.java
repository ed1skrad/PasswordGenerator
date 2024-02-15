package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.GeneratedPassword;
import com.password.generator.bsuir.service.PasswordGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String generatedPassword = passwordGenerationService.generatePassword(dto);
        if (generatedPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate password");
        }
        return ResponseEntity.ok(generatedPassword);
    }

    @GetMapping("/getLastGeneratedPassword")
    public ResponseEntity<String> getLastGeneratedPassword() {
        String lastGeneratedPassword = passwordGenerationService.getLastGeneratedPassword();
        if (lastGeneratedPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found");
        }
        return ResponseEntity.ok(lastGeneratedPassword);
    }

    @GetMapping("/getAllGeneratedPasswords")
    public ResponseEntity<?> getAllGeneratedPasswords() {
        List<GeneratedPassword> generatedPasswords = passwordGenerationService.getAllGeneratedPasswords();
        if (generatedPasswords.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found");
        }
        return ResponseEntity.ok(generatedPasswords);
    }
}
