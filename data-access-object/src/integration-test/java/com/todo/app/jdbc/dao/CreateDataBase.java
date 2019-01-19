package com.todo.app.jdbc.dao;

import com.todo.app.jdbc.dao.data.source.IDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBase {

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
                    "  PASSWORD_HASH VARCHAR (255) NOT NULL," +
                    "  SALT VARCHAR (255) NOT NULL," +
                    "  TOKEN VARCHAR (255) NOT NULL," +
                    "  ENABLE BOOLEAN, " +
                    "  PRIMARY KEY (ID));";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dropTableUsers() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "DROP TABLE Users;";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dropTableTasks() {
        try (Connection conn = source.getConnect();
             Statement stmt = conn.createStatement()) {
            String sql = "DROP TABLE Tasks";
            stmt.executeUpdate(sql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
