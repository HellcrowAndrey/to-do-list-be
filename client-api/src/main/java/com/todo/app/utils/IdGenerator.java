package com.todo.app.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static volatile IdGenerator instance;

    private AtomicLong counter = new AtomicLong();

    public static synchronized IdGenerator getInstance() {
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
