package com.password.generator.bsuir.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordGenerationExceptionTest {

    @Test
    void testConstructor() {
        String message = "Test exception message";
        PasswordGenerationException exception = new PasswordGenerationException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
