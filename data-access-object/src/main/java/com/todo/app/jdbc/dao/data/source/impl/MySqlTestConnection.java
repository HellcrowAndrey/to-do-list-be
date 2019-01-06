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
@Profile("integration-tests")
@PropertySource("classpath:it/application.properties")
public class MySqlTestConnection implements IDataSource {

    @Value("${spring.jdbc_driver}")
    private String JDBC_DRIVER;

    @Value("${spring.db_url}")
    private String DB_URL;

    @Value("${spring.user}")
    private String USER;

    @Value("${spring.pass}")
    private String PASS;

    @Override
    public Connection getConnect() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
