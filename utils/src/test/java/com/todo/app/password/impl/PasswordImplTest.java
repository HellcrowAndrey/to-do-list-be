package com.todo.app.password.impl;

import com.todo.app.password.IPasswords;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PasswordsImpl.class, IPasswords.class})
public class PasswordImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordImplTest.class);

    @Test
    public void getSaltTest() {
        IPasswords passwords = new PasswordsImpl();
        final byte[] bytes = passwords.getSalt(0);
        int arrayLength = bytes.length;
        assertThat("Expected length is ", arrayLength, is(64));
    }

    @Test
    public void getSalt64Test() {
        IPasswords passwords = new PasswordsImpl();
        final byte[] bytes = passwords.getSalt64();
        int arrayLength = bytes.length;
        assertThat("Expected length is ", arrayLength, is(64));
    }

    @Test
    public void getSalt32Test() {
        IPasswords passwords = new PasswordsImpl();
        final byte[] bytes = passwords.getSalt32();
        int arrayLength = bytes.length;
        assertThat("Expected length is ", arrayLength, is(32));
    }

    @Test
    public void hashTest() {
        IPasswords passwords = new PasswordsImpl();
        final byte[] hash = passwords.hash("password", passwords.getSalt64());
        assertThat("Array is not null", hash, Matchers.notNullValue());
    }

    @Test
    public void hashExceptionTest() throws Exception {
        String password = "password";
        IPasswords passwordsSpy = spy(new PasswordsImpl());
        final byte[] salt64 = passwordsSpy.getSalt64();
        when(passwordsSpy, "getAll", password, salt64).thenThrow(Exception.class);
        final byte[] hash = passwordsSpy.hash(password, salt64);
        assertTrue(hash.length == 0);
    }

    @Test
    public void isExpectedPasswordIncorrectTest() {
        String password = "password";
        IPasswords passwords = new PasswordsImpl();
        final byte[] salt64 = passwords.getSalt64();
        final byte[] hash = passwords.hash(password, salt64);
        //The salt and the hash go to database
        final boolean isPasswordCorrect = passwords.isExpectedPassword("invalidpassword", salt64, hash);
        assertThat("Password is not correct", isPasswordCorrect, is(false));
    }

    @Test
    public void isExpectedPasswordCorrectTest() {
        String password = "password";
        IPasswords passwords = new PasswordsImpl();
        final byte[] salt64 = passwords.getSalt64();
        final byte[] hash = passwords.hash(password, salt64);
        //The salt and the hash go to database
        final boolean isPasswordCorrect = passwords.isExpectedPassword("password", salt64, hash);
        assertThat("Password is correct", isPasswordCorrect, is(true));
    }

    @Test
    public void isExpectedPasswordExceptionTest() throws Exception {
        String password = "password";
        IPasswords passwordsSpy = spy(new PasswordsImpl());
        final byte[] salt64 = passwordsSpy.getSalt64();
        final byte[] hash = passwordsSpy.hash(password, salt64);
        when(passwordsSpy, "getAll", password, salt64).thenThrow(Exception.class);
        final boolean isPasswordCorrect = passwordsSpy.isExpectedPassword("password", salt64, hash);
        assertThat("Password is correct", isPasswordCorrect, is(false));
    }

    @Test
    public void isExpectedPasswordSaltNullTest() {
        String password = "password";
        IPasswords passwords = new PasswordsImpl();
        final byte[] salt64 = passwords.getSalt64();
        final byte[] hash = passwords.hash(password, salt64);
        //The salt and the hash go to database
        final boolean isPasswordCorrect = passwords.isExpectedPassword("password", null, hash);
        assertThat("Password is correct", isPasswordCorrect, is(false));
    }

    @Test
    public void isExpectedPasswordNullTest() {
        String password = null;
        IPasswords passwords = new PasswordsImpl();
        final byte[] salt64 = passwords.getSalt64();
        final byte[] hash = passwords.hash(password, salt64);
        //The salt and the hash go to database
        final boolean isPasswordCorrect = passwords.isExpectedPassword(null, salt64, hash);
        assertThat("Password is null", isPasswordCorrect, is(false));
    }

    @Test
    public void isExpectedPasswordEmptyTest() {
        String password = "password";
        IPasswords passwords = new PasswordsImpl();
        final byte[] salt64 = passwords.getSalt64();
        final byte[] hash = passwords.hash(password, salt64);
        //The salt and the hash go to database
        final boolean isPasswordCorrect = passwords.isExpectedPassword("", salt64, hash);
        assertThat("Password is empty", isPasswordCorrect, is(false));
    }

    @Test
    public void generatePasswordTest() {
        IPasswords passwords = new PasswordsImpl();
        final String randomPassword = passwords.generateRandomPassword(10);
        LOGGER.info(randomPassword);
        assertThat("Random password is not null", randomPassword, Matchers.notNullValue());
    }

    @Test
    public void generatePasswordIncorrectLengthTest() {
        IPasswords passwords = new PasswordsImpl();
        final String randomPassword = passwords.generateRandomPassword(0);
        LOGGER.info(randomPassword);
        assertThat("Random password is null", randomPassword, Matchers.nullValue());
    }
}