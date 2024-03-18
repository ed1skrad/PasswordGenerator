package com.password.generator.bsuir.controller;

import com.password.generator.bsuir.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestCounterController {

    private final RequestCounterService requestCounterService;

    @Autowired
    public RequestCounterController(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @GetMapping("/requestCount")
    @PreAuthorize("hasRole('ADMIN')")
    public int getRequestCount() {
        return requestCounterService.getCounter();
    }
}