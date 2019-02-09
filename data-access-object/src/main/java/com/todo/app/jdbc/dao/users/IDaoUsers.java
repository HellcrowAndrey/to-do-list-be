package com.todo.app.jdbc.dao.users;

import com.todo.app.dao.model.UserDaoModel;

/**
 * The IDaoUsers interface use if need create class for work with
 * sql date base and table users. Has next method create, read,
 * update, delete. Method create received UserDaoModel and do
 * create user in date base, after that do return id (number of
 * row in db). Method read received email or login and do read
 * users in db. Method update received UserDaoModel and do update
 * task in db. Method delete received email and do delete user in db.
 */
public interface IDaoUsers {

    /**
     * This is method create. Received UserDaoModel and do create user
     * in db. Field login, email and token in db are unique. After call
     * to this method do check on null if received object is null do
     * return zero else do create user in date base. In this method do
     * handlers on date base exceptions if catch it do return zero.
     *
     * @param user object user model in date base.
     * @return id or zero if received exception or received null object
     * in param.
     */
    long create(UserDaoModel user);

    /**
     * This method do read user in date base received login or email in
     * param. After call to this method do check on null or empty and if
     * data valid do read user in db else return null. In this method
     * do handlers on date base exception if catch it do return null.
     *
     * @param data This is login or email.
     * @return null or data about user.
     */
    UserDaoModel read(String data);

    /**
     * This method do update user in db, received UserDaoModel. After call to this
     * method do check on null, if data null do return zero else do update user in
     * data base. In this method do handlers on data base exception if catch it do
     * return zero.
     *
     * @param user object user model in date base.
     * @return id or zero if received wrong data or received exception.
     */
    long update(UserDaoModel user);

    /**
     * This method do delete user in db, received use email. After call to this
     * method do check on null or empty. If data don't valid do return zero else
     * do delete user in data base. In this method do handlers data base exception
     * if catch it do return zero.
     *
     * @param email this is user email.
     * @return id or zero if received wrong data or catch exception.
     */
    long delete(String email);

}
