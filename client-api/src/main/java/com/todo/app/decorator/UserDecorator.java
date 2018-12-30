package com.todo.app.decorator;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.hash.MDFive;

public class UserDecorator {

    public UserModel createHash(UserModel user) {
        MDFive md = new MDFive();
        user.setHashEmailPass(md.getHash(user.getEmail() +
                user.getPassword()));
        user.setHashLoginPass(md.getHash(user.getLogin() +
                user.getPassword()));
        return user;
    }

}
