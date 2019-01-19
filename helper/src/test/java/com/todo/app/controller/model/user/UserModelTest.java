package com.todo.app.controller.model.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserModelTest {

    private String login;
    private String email;
    private String password;
    private String toString;

    public UserModelTest(String login, String email, String password, String toString) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.toString = toString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // This is case with valid params
                {
                        "login", "email@gmail.com", "123432213",
                        "UserModel{login='login', email='email@gmail.com', password='123432213'}"
                },
                // login is null
                {
                        null, "email@gmail.com", "123432213",
                        "UserModel{login='null', email='email@gmail.com', password='123432213'}"
                }
        });
    }

    @Test
    public void userModelGetTest() {
        UserModel actual = new UserModel(login, email, password);
        assertEquals(actual.getLogin(), login);
        assertEquals(actual.getEmail(), email);
        assertEquals(actual.getPassword(), password);
    }

    @Test
    public void userModelSetTest() {
        UserModel actual = new UserModel();
        actual.setLogin(login);
        actual.setEmail(email);
        actual.setPassword(password);
        assertEquals(actual.getLogin(), login);
        assertEquals(actual.getEmail(), email);
        assertEquals(actual.getPassword(), password);
    }

    @Test
    public void userModelEqualsTest() {
        UserModel first = new UserModel(login, email, password);
        UserModel second = new UserModel(login, email, password);
        assertTrue(first.equals(second));
    }

    @Test
    public void userModelEqualsFailFirstTest() {
        UserModel first = new UserModel(login, email, password);
        UserModel second = null;
        assertFalse(first.equals(second));
    }

    @Test
    public void userModelEqualsFailSecondTest() {
        UserModel first = new UserModel(login, email, password);
        assertTrue(first.equals(first));
    }

    @Test
    public void userModelToStringTest() {
        UserModel model = new UserModel(login, email, password);
        assertEquals(toString , model.toString());
    }

    @Test
    public void userModelHasCodeTest() {
        UserModel model1 = new UserModel(login, email, password);
        UserModel model2 = new UserModel(login, email, password);
        assertEquals(model1.hashCode(), model2.hashCode());
    }

}
