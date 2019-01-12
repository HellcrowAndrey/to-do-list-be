package com.todo.app.decorator;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.hash.MDFive;

public class UserDecorator {

    public UserModel createHash(UserModel user) {
        MDFive md = new MDFive();
        if (user.getEmail() == null || user.getEmail().equals("")) {
            user.setHashLoginPass(md.getHash(user.getLogin() + user.getPassword()));
        } else if (user.getLogin() == null || user.getLogin().equals("")) {
            user.setHashEmailPass(md.getHash(user.getEmail() + user.getPassword()));
        }
        return user;
    }

}
