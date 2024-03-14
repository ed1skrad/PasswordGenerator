package com.password.generator.bsuir.service;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounterService {

    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized void increment() {
        counter.incrementAndGet();
    }

    public synchronized int getCounter() {
        return counter.get();
    }
}
