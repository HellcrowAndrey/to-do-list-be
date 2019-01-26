package com.todo.app.jdbc.dao.data.source.impl;

import com.todo.app.jdbc.dao.data.source.IDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@Profile("debug")
@PropertySource("classpath:debug/application.properties")
public class MySqlDebugConnection implements IDataSource {

    @Value("${spring.jdbc_driver}")
    private String jdbcDriver;

    @Value("${spring.db_url}")
    private String dbUrl;

    @Value("${spring.user}")
    private String user;

    @Value("${spring.pass}")
    private String pass;

    @Override
    public Connection getConnect() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {
        Class.forName(jdbcDriver).newInstance();
        return DriverManager.getConnection(dbUrl, user, pass);
    }
}
