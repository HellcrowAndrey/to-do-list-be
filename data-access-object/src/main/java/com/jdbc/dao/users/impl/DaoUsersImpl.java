package com.jdbc.dao.users.impl;

import com.helper.controller.model.user.UserModel;
import com.jdbc.dao.data.source.IDataSource;
import com.jdbc.dao.users.IDaoUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUsersImpl implements IDaoUsers {

    private IDataSource source;

    public DaoUsersImpl(IDataSource source) {
        this.source = source;
    }

    @Override
    public int create(UserModel user) {
        if (user == null) {
            return 0;
        }
        String query = "INSERT INTO Users (USER, EMAIL, PASSWORD) VALUES (?, ?, ?);";
        return doStaffUser(user, query);
    }

    @Override
    public UserModel read(UserModel user) {
        if (user == null) {
            return null;
        }
        UserModel result = null;
        String query = "SELECT ID, USER, EMAIL, PASSWORD " +
                "FROM Users WHERE USER=? OR EMAIL=? AND PASSWORD=?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                result = new UserModel(id, login, email, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(UserModel user) {
        if (user == null) {
            return 0;
        }
        String query = "UPDATE Users SET  EMAIL=?, PASSWORD=? WHERE USER=?;";
        return doStaffUser(user, query);
    }

    @Override
    public int delete(UserModel user) {
        if (user == null) {
            return 0;
        }
        String query = "DELETE FROM Users WHERE USER=? OR EMAIL=? AND PASSWORD=?";
        return doStaffUser(user, query);
    }

    private int doStaffUser(UserModel user, String query) {
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            result = userFields(statement, user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int userFields(PreparedStatement statement, UserModel user) throws SQLException {
        statement.setString(1, user.getUser());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        return statement.executeUpdate();
    }

}
