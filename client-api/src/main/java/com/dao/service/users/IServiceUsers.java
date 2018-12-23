package com.dao.service.users;

import com.helper.controller.model.user.UserModel;

public interface IServiceUsers {

    boolean create(UserModel user);

    UserModel read(UserModel user);

    boolean update(UserModel user);

    boolean delete(UserModel user);

}
