package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for request counter functionality.
 */
@RestController
@RequestMapping("/api/test")
public class RequestCounterController {

    private final RequestCounterService requestCounterService;

    /**
     * Constructs a new RequestCounterController.
     *
     * @param requestCounterService the request counter service
     */
    @Autowired
    public RequestCounterController(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    /**
     * Retrieves the current request count.
     *
     * @return the current request count
     */
    @GetMapping("/requestCount")
    @PreAuthorize("hasRole('ADMIN')")
    public int getRequestCount() {
        return requestCounterService.getCounter();
    }
}
