package com.todo.app.service.users;

import com.todo.app.controller.model.user.UserModel;

public interface IServiceUsers {

    String create (UserModel user);

    long read(String login, String email);

    UserModel read(UserModel user);

    boolean update(UserModel user);

    boolean delete(UserModel user);

}
