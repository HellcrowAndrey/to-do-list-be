package com.todo.app.service.users.impl;

import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import app.jdbc.dao.data.source.IDataSource;
import app.jdbc.dao.data.source.impl.MySqlConnection;
import app.jdbc.dao.users.IDaoUsers;
import app.jdbc.dao.users.impl.DaoUsersImpl;
import com.todo.app.utils.ControllerUtils;

public class ServiceUsersImpl implements IServiceUsers {

    @Override
    public String create(UserModel user) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.create(user) > 0 ?
                ControllerUtils.USER_REGISTRATION_SUCCESS :
                ControllerUtils.USER_REGISTRATION_FAILURE;
    }

    @Override
    public long read(String login, String email) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.read(login, email);
    }

    @Override
    public UserModel read(UserModel user) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.read(user);
    }

    @Override
    public boolean update(UserModel user) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.update(user) > 0 ? true : false;
    }

    @Override
    public boolean delete(UserModel user) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.delete(user) > 0 ? true : false;
    }

}
