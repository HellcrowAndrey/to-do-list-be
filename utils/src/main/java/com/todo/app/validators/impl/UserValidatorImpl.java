package com.todo.app.validators.impl;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.validators.DataValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class UserValidatorImpl implements DataValidators {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidatorImpl.class);

    private static final String EMAIL_PATTERN =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                    "*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private static final String PASSWORD_PATTERN =
            "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,}" +
                    ")(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";

    private static final String USER_NAME_PATTERN = "[a-zA-Z0-9.\\\\-_]{3,}";

    @Override
    public boolean isValidEmailAddress(final String email) {
        LOGGER.info("Call to method isValidEmailAddress");
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return (email != null) && pattern.matcher(email).matches();
    }

    @Override
    public boolean isValidPassword(final String password) {
        LOGGER.info("Call to method isValidPassword");
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return (password != null) && (!password.equals(""))
                && pattern.matcher(PASSWORD_PATTERN).matches();
    }

    @Override
    public boolean isValidLogin(final String login) {
        LOGGER.info("Call to method isValidLogin");
        final Pattern pattern = Pattern.compile(USER_NAME_PATTERN);
        return (login != null) && pattern.matcher(login).matches();
    }

    @Override
    public boolean isUserDataValid(final UserModel model) {
        LOGGER.info("Call to method isUserDataValid");
        return isValidLogin(model.getLogin()) &&
                isValidEmailAddress(model.getEmail()) &&
                isValidPassword(model.getPassword());
    }
}
