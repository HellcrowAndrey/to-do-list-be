package com.todo.app.service.users.impl;

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
    public String create(UserModel user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.create(user) > 0 ?
                ControllerUtils.USER_REGISTRATION_SUCCESS :
                ControllerUtils.USER_REGISTRATION_FAILURE;
    }

    @Override
    public long read(String login, String email) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.read(login, email);
    }

    @Override
    public UserModel read(UserModel user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.read(user);
    }

    @Override
    public boolean update(UserModel user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.update(user) > 0 ? true : false;
    }

    @Override
    public boolean delete(UserModel user) {
        IDaoUsers users = new DaoUsersImpl(source);
        return users.delete(user) > 0 ? true : false;
    }

}
