package app.jdbc.dao.data.source.impl;

import app.jdbc.dao.data.source.IDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection implements IDataSource {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/db_user?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "root";

    @Override
    public Connection getConnect() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


}
