package com.todo.app.jdbc.dao.data.source.impl;

import com.todo.app.jdbc.dao.data.source.IDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlTestConnection implements IDataSource {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://0.0.0.0:6603/firstdb";
    private static final String USER = "user";
    private static final String PASS = "pass123";

    @Override
    public Connection getConnect() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}