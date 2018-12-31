package com.todo.app.jdbc.dao.users;

import com.todo.app.controller.model.user.UserModel;

public interface IDaoUsers {

    int create(UserModel user);

    long read(String login, String email);

    UserModel read(UserModel user);

    int update(UserModel user);

    int delete(UserModel user);

}
