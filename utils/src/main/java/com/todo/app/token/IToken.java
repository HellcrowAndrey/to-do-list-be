package com.todo.app.token;

/**
 * The IToken interface use if need do create user token.
 */
public interface IToken {

    /**
     * This method do return new token.
     *
     * @return user token
     */
    String nextToken();

}
