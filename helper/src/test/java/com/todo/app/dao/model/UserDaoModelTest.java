package com.todo.app.dao.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserDaoModelTest {

    private long idUser;

    private String login;

    private String email;

    private byte[] hash;

    private byte[] salt;

    private String token;

    private boolean enable;

    private String toString;

    public UserDaoModelTest(long idUser, String login, String email,
                            byte[] hash, byte[] salt, String token,
                            boolean enable, String toString) {
        this.idUser = idUser;
        this.login = login;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.token = token;
        this.enable = enable;
        this.toString = toString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //This is actual case
                {
                        1l, "login", "email@gmail.com", new byte[]{'2', '1', '3'}, new byte[]{'5','6','7'}, "Token2MoCk==", true,
                        "UserDaoModel{idUser=1, login='login', email='email@gmail.com', hash=[50, 49, 51], salt=[53, 54, 55], token='Token2MoCk==', enable=true}"
                }
        });
    }

    @Test
    public void userDaoModelGetTest() {
        UserDaoModel model = new UserDaoModel();

        model.setIdUser(idUser);
        model.setLogin(login);
        model.setEmail(email);
        model.setHash(hash);
        model.setSalt(salt);
        model.setToken(token);
        model.setEnable(enable);

        assertEquals(model.getIdUser(), idUser);
        assertEquals(model.getLogin(), login);
        assertEquals(model.getEmail(), email);
        assertEquals(model.getHash(), hash);
        assertEquals(model.getSalt(), salt);
        assertEquals(model.getToken(), token);
        assertEquals(model.isEnable(), enable);
    }

    @Test
    public void userDaoModelEqualsTrueTest() {
        UserDaoModel model1 = new UserDaoModel();
        UserDaoModel model2 = new UserDaoModel();

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        model2.setIdUser(idUser);
        model2.setLogin(login);
        model2.setEmail(email);
        model2.setHash(hash);
        model2.setSalt(salt);
        model2.setToken(token);
        model2.setEnable(enable);

        assertTrue(model1.equals(model2));
    }

    @Test
    public void userDaoModelEqualsFalseTest() {
        UserDaoModel model1 = new UserDaoModel();
        UserDaoModel model2 = new UserDaoModel();

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        model2.setLogin(login);
        model2.setEmail(email);
        model2.setHash(hash);
        model2.setSalt(salt);
        model2.setToken(token);
        model2.setEnable(enable);

        assertFalse(model1.equals(model2));
    }

    @Test
    public void userDaoModelEqualsNullTest() {
        UserDaoModel model1 = new UserDaoModel();
        UserDaoModel model2 = null;

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        assertFalse(model1.equals(model2));
    }

    @Test
    public void userDaoModelEqualsOneModelTest() {
        UserDaoModel model1 = new UserDaoModel();

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        assertTrue(model1.equals(model1));
    }

    @Test
    public void userDaoModelHasCodeTest() {
        UserDaoModel model1 = new UserDaoModel();
        UserDaoModel model2 = new UserDaoModel();

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        model2.setIdUser(idUser);
        model2.setLogin(login);
        model2.setEmail(email);
        model2.setHash(hash);
        model2.setSalt(salt);
        model2.setToken(token);
        model2.setEnable(enable);

        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    public void userDaoModelToStringTest() {
        UserDaoModel model1 = new UserDaoModel();

        model1.setIdUser(idUser);
        model1.setLogin(login);
        model1.setEmail(email);
        model1.setHash(hash);
        model1.setSalt(salt);
        model1.setToken(token);
        model1.setEnable(enable);

        assertEquals(toString, model1.toString());
    }

}