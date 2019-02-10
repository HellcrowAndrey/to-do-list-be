package com.todo.app.user;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.token.impl.TokenGeneratorImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserTest {

    @Test
    public void builderTest() {
        UserModel user = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        CreateUser createUser = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user).createUser().build();
        UserDaoModel actual = createUser.getDaoModel();
        assertEquals(user.getLogin(), actual.getLogin());
        assertEquals(user.getEmail(), actual.getEmail());
        assertNotNull(actual.getToken());
        assertNotNull(actual.getHash());
        assertNotNull(actual.getSalt());
    }

}