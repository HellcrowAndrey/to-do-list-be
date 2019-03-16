package com.todo.app.token.impl;

import com.todo.app.token.IToken;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenGeneratorImplTest {

    @Test
    public void tokenNotNullTest() {
        IToken token = new TokenGeneratorImpl();
        String str = token.nextToken();
        assertThat("This is token", str, Matchers.notNullValue());
    }

    @Test
    public void tokenLengthTest() {
        IToken token = new TokenGeneratorImpl();
        String str = token.nextToken();
        int expected = 64;
        assertEquals(expected, str.length());
    }
}