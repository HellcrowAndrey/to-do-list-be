package com.todo.app.service.users.impl;

import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.jdbc.dao.users.impl.DaoUsersImpl;
import com.todo.app.utils.ControllerUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ServiceUsersImpl implements IServiceUsers {

    private IDataSource source;

    public ServiceUsersImpl(IDataSource source) {
        this.source = source;
    }

    @Override
    public long create(UserDaoModel  user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.create(user);
    }

    @Override
    public UserDaoModel read(String login, String email) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.read(login, email);
    }

    @Override
    public long update(UserDaoModel user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.update(user);
    }

    @Override
    public long delete(String email) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.delete(email);
    }

}
