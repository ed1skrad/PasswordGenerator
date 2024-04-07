package com.password.generator.bsuir.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestCounterServiceTest {

    private RequestCounterService requestCounterService;

    @BeforeEach
    void setUp() {
        requestCounterService = new RequestCounterService();
    }

    @Test
    void testIncrement() {
        requestCounterService.increment();
        assertEquals(1, requestCounterService.getCounter());

        requestCounterService.increment();
        assertEquals(2, requestCounterService.getCounter());
    }

    @Test
    void testGetCounter() {
        assertEquals(0, requestCounterService.getCounter());

        requestCounterService.increment();
        assertEquals(1, requestCounterService.getCounter());

        requestCounterService.increment();
        assertEquals(2, requestCounterService.getCounter());
    }
}
