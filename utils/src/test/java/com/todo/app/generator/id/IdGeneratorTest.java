package com.todo.app.generator.id;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class IdGeneratorTest {

    @Test
    public void genId() {
        IdGenerator generator = IdGenerator.getInstance();
        long id = generator.getCounter();
        assertThat("This is actual id", id, is(1l));
    }

    @Test
    public void genTwoId() {
        IdGenerator generator = IdGenerator.getInstance();
        long id1 = generator.getCounter();
        long id2 = generator.getCounter();
        assertTrue(id2 > 1);
    }
}