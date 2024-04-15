package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.service.RequestCounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class RequestCounterControllerTest {

    private RequestCounterController requestCounterController;

    @Mock
    private RequestCounterService requestCounterService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
        requestCounterController = new RequestCounterController(requestCounterService);
    }

    @Test
     void testGetRequestCount_Success() {
        int expectedCount = 10;
        when(requestCounterService.getCounter()).thenReturn(expectedCount);

        int result = requestCounterController.getRequestCount();

        assertEquals(expectedCount, result);
        verify(requestCounterService).getCounter();
    }
}
