package com.password.generator.bsuir.security.domain.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

 class SignUpRequestTest {

    private Validator validator;

    @Test
     void testValidSignUpRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("valid_username");
        request.setEmail("valid_email@example.com");
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
     void testNullUsername() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername(null);
        request.setEmail("valid_email@example.com");
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("Имя пользователя не может быть null", violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
    }

    @Test
     void testNullEmail() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("valid_username");
        request.setEmail(null);
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("Адрес электронной почты не может быть null", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
     void testInvalidEmail() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("valid_username");
        request.setEmail("invalid_email_address");
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("Email адрес должен быть в формате user@example.com", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
     void testNullPassword() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("valid_username");
        request.setEmail("valid_email@example.com");
        request.setPassword(null);

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("Пароль не может быть null", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
     void testPasswordTooShort() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("valid_username");
        request.setEmail("valid_email@example.com");
        request.setPassword("short");

        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("Длина пароля должна быть от 8 до 255 символов", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
