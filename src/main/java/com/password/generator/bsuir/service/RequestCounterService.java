package com.password.generator.bsuir.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestCounterService {

    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized void increment() {
        counter.incrementAndGet();
    }

    public synchronized int getCounter() {
        return counter.get();
    }
}
