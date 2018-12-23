package com.dao.service.users.impl;

import com.dao.service.users.IServiceUsers;
import com.helper.controller.model.user.UserModel;
import com.jdbc.dao.data.source.IDataSource;
import com.jdbc.dao.data.source.impl.MySqlConnection;
import com.jdbc.dao.users.IDaoUsers;
import com.jdbc.dao.users.impl.DaoUsersImpl;

public class ServiceUsersImpl implements IServiceUsers {

    @Override
    public boolean create(UserModel user) {
        IDataSource source = new MySqlConnection();
        IDaoUsers users = new DaoUsersImpl(source);
        return users.create(user) > 0 ? true : false;
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
