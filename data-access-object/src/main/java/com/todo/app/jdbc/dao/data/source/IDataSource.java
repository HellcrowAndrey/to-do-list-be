package com.todo.app.jdbc.dao.data.source;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public interface IDataSource {
    Connection getConnect() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException;
}
