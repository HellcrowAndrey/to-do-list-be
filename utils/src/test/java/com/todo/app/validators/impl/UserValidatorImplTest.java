package com.todo.app.validators.impl;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.validators.DataValidators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserValidatorImplTest.IsValidEmail.class,
        UserValidatorImplTest.IsValidPassword.class,
        UserValidatorImplTest.IsValidLogin.class,
        UserValidatorImplTest.IsUserDataValid.class
})
public class UserValidatorImplTest {

    //==========================================================
    //========== This test case for testing method       =======
    //========== isValidEmail(String email)              =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsValidEmail {

        //this is user email
        private String email;
        //this field info about exp result
        private boolean expected;

        public IsValidEmail(String email, boolean expected) {
            this.email = email;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: is Valid Email Address")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            "john@somewhere.com", true
                    },
                    {
                            "john.foo@somewhere.com", true
                    },
                    {
                            "@someserver", false
                    },
                    {
                            null, false
                    },
                    {
                            "", false
                    }
            });
        }

        @Test
        public void isValidEmailTest() {
            DataValidators validators = new UserValidatorImpl();
            boolean actual = validators.isValidEmailAddress(email);
            assertEquals(expected, actual);
        }

    }

    //==========================================================
    //========== This test case for testing method       =======
    //========== isValidPassword(String password)        =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsValidPassword {

        //this is user password
        private String password;
        //this field info about exp result
        private boolean expected;

        public IsValidPassword(String password, boolean expected) {
            this.password = password;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: is Valid Email Address")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            "aaZZa44@", true
                    },
                    {
                            "12312Yewqdsa", true
                    },
                    {
                            "ReqwdsdSAd23", true
                    },
                    {
                            null, false
                    },
                    {
                            "", false
                    }
            });
        }

        @Test
        public void isValidPasswordTest() {
            DataValidators validators = new UserValidatorImpl();
            boolean actual = validators.isValidPassword(password);
            assertEquals(expected, actual);
        }

    }

    //==========================================================
    //========== This test case for testing method       =======
    //========== isValidLogin(String login)              =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsValidLogin {

        //this is user login
        private String login;
        //this field info about exp result
        private boolean expected;

        public IsValidLogin(String login, boolean expected) {
            this.login = login;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: is Valid Email Address")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            "vasia", true
                    },
                    {
                            "Yelise", true
                    },
                    {
                            "kolia", true
                    },
                    {
                            "kolia%", false
                    },
                    {
                            "k@lia", false
                    },
                    {
                            "\"kolia", false
                    },
                    {
                            "ko", false
                    },
                    {
                            null, false
                    },
                    {
                            "", false
                    }
            });
        }

        @Test
        public void isValidPasswordTest() {
            DataValidators validators = new UserValidatorImpl();
            boolean actual = validators.isValidLogin(login);
            assertEquals(expected, actual);
        }

    }

    //==========================================================
    //========== This test case for testing method       =======
    //========== isUserDataValid(String login)           =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsUserDataValid {

        //this is user data
        private UserModel model;

        //this field info about exp result
        private boolean expected;

        public IsUserDataValid(UserModel model, boolean expected) {
            this.model = model;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: is Valid Email Address")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            new UserModel("vasia", "john@somewhere.com", "aaZZa44@"), true
                    },
                    {
                            new UserModel("", "john@somewhere.com", "aaZZa44@"), false
                    },
                    {
                            new UserModel("vasia", "@someserver", "aaZZa44@"), false
                    },
                    {
                            new UserModel("vasia", "john@somewhere.com", ""), false
                    },
                    {
                            new UserModel("", "", ""), false
                    },
            });
        }

        @Test
        public void isValidPasswordTest() {
            DataValidators validators = new UserValidatorImpl();
            boolean actual = validators.isUserDataValid(model);
            assertEquals(expected, actual);
        }
    }

}