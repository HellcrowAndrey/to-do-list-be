package com.todo.app.token.impl;

import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.token.IToken;

public class TokenGeneratorImpl extends PasswordsImpl implements IToken {

    private static final int TOKEN_LENGTH = 64;

    @Override
    public String nextToken() {
        return generateRandomPassword(TOKEN_LENGTH);
    }

}
