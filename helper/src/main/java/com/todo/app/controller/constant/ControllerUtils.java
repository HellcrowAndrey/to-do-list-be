package com.todo.app.controller.constant;

public class ControllerUtils {

    //==================================================
    //=============== Message ==========================
    //==================================================

    public static final String CRETE_USER = "Start process for create user";
    public static final String RECEIVED_MESSAGE = "Received message in ";
    public static final String IS_NOT_VALID_PARAMS = "Not valid params in controller.";
    public static final String USER_EXIT = "User with this login or email exist.";
    public static final String USER_REGISTRATION_SUCCESS = "User registration success.";
    public static final String USER_REGISTRATION_FAILURE = "User registration failure.";
    public static final String USER_NOT_FOUNT = "User not found.";
    public static final String USER_AUTHORIZATION_SUCCESS = "User authorization success.";
    public static final String USER_AUTHORIZATION_FAILURE = "User authorization failure!";
    public static final String INCORRECT_DATA = "Incorrect login or password!";
    public static final String INCORRECT_TOKEN = "Incorrect token!";
    public static final String RESTART_ACCOUNT = "Restart account.";

    //==================================================
    //=============== Command ==========================
    //==================================================

    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    //==================================================
    //=============== Mapping ==========================
    //==================================================

    public static final String REGISTRATION = "/registration";
    public static final String AUTHORIZATION = "/authorization";
    public static final String TASKS = "/tasks";
    public static final String TASK_FOR_COMMAND = "/{command}/task";

    //==================================================
    //=============== PARAMS ==========================
    //==================================================

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String TOKEN = "token";
    public static final String DATA = "data";

}
