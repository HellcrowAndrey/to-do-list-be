package com.todo.app.validators;


import com.todo.app.controller.model.user.UserModel;

public interface DataValidators {

    boolean isValidEmailAddress(String email);

    boolean isValidPassword(String password);

    boolean isValidLogin(String login);

    boolean isUserDataValid(UserModel model);

}
