package com.todo.app.user;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.token.impl.TokenGeneratorImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserTest {

    @Test
    public void builderManyTest() {
        UserModel user1 = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        CreateUser createUser1 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user1).createUser().build();
        UserDaoModel actual1 = createUser1.getDaoModel();

        UserModel user2 = new UserModel(
                "kolia", "koliaaa@somewhere.com", "azazel");
        CreateUser createUser2 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user2).createUser().build();
        UserDaoModel actual2 = createUser2.getDaoModel();

        UserModel user3 = new UserModel(
                "petia", "peterovka@gmail.com", "3214eqwrDSAw");
        CreateUser createUser3 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user3).createUser().build();
        UserDaoModel actual3 = createUser3.getDaoModel();

        UserModel user4 = new UserModel(
                "veniaminstepanich", "venom@ya.ru", "testpassword");
        CreateUser createUser4 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user4).createUser().build();
        UserDaoModel actual4 = createUser4.getDaoModel();

        UserModel user5 = new UserModel(
                "katerinapupkina", "katerina@ram.com", "kulibakinsk");
        CreateUser createUser5 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user5).createUser().build();
        UserDaoModel actual5 = createUser5.getDaoModel();

        assertFalse(actual1.equals(actual2));
        assertFalse(actual1.equals(actual3));
        assertFalse(actual1.equals(actual4));
        assertFalse(actual1.equals(actual5));

        assertFalse(actual2.equals(actual3));
        assertFalse(actual2.equals(actual4));
        assertFalse(actual2.equals(actual5));

        assertFalse(actual3.equals(actual4));
        assertFalse(actual3.equals(actual5));

        assertFalse(actual4.equals(actual5));
    }

    @Test
    public void builderTwoTest() {
        UserModel user1 = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        CreateUser createUser1 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user1).createUser().build();
        UserDaoModel actual1 = createUser1.getDaoModel();

        UserModel user2 = new UserModel(
                "kolia", "koliaaa@somewhere.com", "azazel");
        CreateUser createUser2 = new CreateUser.UserBuilder()
                .init(new PasswordsImpl(), new TokenGeneratorImpl())
                .createParams(user2).createUser().build();
        UserDaoModel actual2 = createUser2.getDaoModel();

        assertFalse(actual1.equals(actual2));

    }

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