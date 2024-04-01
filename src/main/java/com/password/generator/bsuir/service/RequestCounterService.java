package com.password.generator.bsuir.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 * Service class for counting the number of requests made to a particular endpoint or service.
 */
@Component
public class RequestCounterService {

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Increment the request counter by one.
     */
    public void increment() {
        counter.incrementAndGet();
    }

    /**
     * Get the current value of the request counter.
     *
     * @return the current value of the request counter.
     */
    public int getCounter() {
        return counter.get();
    }
}
