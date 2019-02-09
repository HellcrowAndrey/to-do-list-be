package com.todo.app.service.users;

import com.todo.app.dao.model.UserDaoModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * The IServiceUsers interface use for encapsulation
 * call to dao class and methods.
 */
public interface IServiceUsers {

    /**
     * This method use for call to method create user
     * from IDaoUsers interface.
     *
     * @param user this obj encapsulation user info.
     * @return id row in db
     */
    long create(UserDaoModel user);

    /**
     * This method call to method read from IDaoUsers interface.
     *
     * @param user this is login or email, depend on user sent
     *             to controller
     * @return obj with user data
     */
    UserDaoModel read(String user);

    /**
     * This method call to method update from IDaoUsers interface.
     *
     * @param user obj with user data
     * @return id row in db
     */
    long update(UserDaoModel user);

    /**
     * This method call to method delete from IDaoUsers interface.
     *
     * @param email user
     * @return id row in db
     */
    long delete(String email);

}
