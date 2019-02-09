package com.todo.app.client.api.delegat;

import com.todo.app.controller.model.user.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class RegistrationDelegateTest {

    //==========================================================
    //========== This test case for testing method       =======
    //========== isParams(UserModel model)               =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class isParams {

        //this is user data
        private UserModel model;

        //this field info about exp result
        private ResponseEntity expected;

        public isParams(UserModel model, ResponseEntity expected) {
            this.model = model;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: is Valid Email Address")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            new UserModel("vasia", "john@somewhere.com", "aaZZa44@"), null
                    }
            });
        }

        @Test
        public void isParamsTest() {

        }

    }

}