package com.jdbc.dao.users;

import com.helper.controller.model.user.UserModel;

public interface IDaoUsers {

    int create(UserModel user);

    UserModel read(UserModel user);

    int update(UserModel user);

    int delete(UserModel user);

}
