package com.todo.app.service.users;

import com.todo.app.dao.model.UserDaoModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface IServiceUsers {

    long create (UserDaoModel user);

    UserDaoModel read(String  user);

    long update(UserDaoModel user);

    long delete(String user);

}
