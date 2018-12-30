package com.todo.app.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static IdGenerator instance;

    private AtomicLong counter = new AtomicLong();

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    private IdGenerator() {
    }

    public long getCounter() {
        return counter.incrementAndGet();
    }
}
