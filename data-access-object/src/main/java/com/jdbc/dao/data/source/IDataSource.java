package com.jdbc.dao.data.source;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataSource {
    Connection getConnect() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException;
}
