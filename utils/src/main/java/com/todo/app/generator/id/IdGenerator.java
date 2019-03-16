package com.todo.app.generator.id;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static volatile IdGenerator instance;

    private AtomicLong counter = new AtomicLong();

    private IdGenerator() {
    }

    public static synchronized IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public long getCounter() {
        return counter.incrementAndGet();
    }

}
