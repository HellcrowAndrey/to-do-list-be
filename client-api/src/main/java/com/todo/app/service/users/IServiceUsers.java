package com.todo.app.service.users;

import com.todo.app.controller.model.user.UserModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface IServiceUsers {

    String create (UserModel user);

    long read(String login, String email);

    UserModel read(UserModel user);

    boolean update(UserModel user);

    boolean delete(UserModel user);

}
