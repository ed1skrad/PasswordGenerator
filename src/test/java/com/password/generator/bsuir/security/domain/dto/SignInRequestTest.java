package com.password.generator.bsuir.security.domain.dto;

import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

 class SignInRequestTest {

    private Validator validator;

    @Test
     void testValidSignInRequest() {
        SignInRequest request = new SignInRequest();
        request.setUsername("valid_username");
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
     void testNullUsername() {
        SignInRequest request = new SignInRequest();
        request.setUsername(null);
        request.setPassword("valid_password");

        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignInRequest> violation = violations.iterator().next();
        assertEquals("Имя пользователя не может быть null", violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
    }

    @Test
     void testNullPassword() {
        SignInRequest request = new SignInRequest();
        request.setUsername("valid_username");
        request.setPassword(null);

        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignInRequest> violation = violations.iterator().next();
        assertEquals("Пароль не может быть null", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
     void testPasswordTooShort() {
        SignInRequest request = new SignInRequest();
        request.setUsername("valid_username");
        request.setPassword("short");

        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<SignInRequest> violation = violations.iterator().next();
        assertEquals("Длина пароля должна быть от 8 до 255 символов", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
