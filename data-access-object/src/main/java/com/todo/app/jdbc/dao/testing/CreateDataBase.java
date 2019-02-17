package com.todo.app.jdbc.dao.testing;

import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.users.impl.DaoUsersImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoUsersImpl.class);

    private IDataSource source;

    public CreateDataBase(IDataSource source) {
        this.source = source;
    }

    public void createTableUsers() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE Users (" +
                    "  ID int NOT NULL AUTO_INCREMENT," +
                    "  LOGIN VARCHAR (50) NOT NULL," +
                    "  EMAIL VARCHAR (100) NOT NULL," +
                    "  HASH BINARY (255) NOT NULL," +
                    "  SALT BINARY (255) NOT NULL," +
                    "  TOKEN VARCHAR (255) NOT NULL," +
                    "  ENABLE BOOLEAN, " +
                    "  PRIMARY KEY (ID));";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void createTableTasks() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE Tasks(" +
                    "  ID INT NOT NULL AUTO_INCREMENT," +
                    "  NAME VARCHAR (50) NOT NULL," +
                    "  TASK VARCHAR (255) NOT NULL," +
                    "  STATUS BIT NOT NULL," +
                    "  ID_USER INT NOT NULL," +
                    "  PRIMARY KEY (ID)," +
                    "  FOREIGN KEY (ID_USER) REFERENCES Users(ID));";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void dropTableUsers() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "DROP TABLE Users;";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void dropTableTasks() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "DROP TABLE Tasks";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
