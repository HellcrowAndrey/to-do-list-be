package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;

import java.sql.*;

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
        String query = "INSERT INTO Users (LOGIN, EMAIL, HASH_LOGIN," +
                " HASH_EMAIL) VALUES (?, ?, ?, ?);";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getHashLoginPass());
            statement.setString(4, user.getHashEmailPass());
            result = statement.executeUpdate();
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
    public long read(String login, String email) {
        if (login == null || login.equals("") ||
                email == null || email.equals("")) {
            return 0;
        }
        long result = 0;
        String query = "SELECT ID FROM Users WHERE LOGIN = ? OR EMAIL = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getLong(1);
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
    public UserModel read(UserModel user) {
        if (user == null) {
            return null;
        }
        UserModel result = null;
        String query = "SELECT ID, LOGIN, EMAIL, HASH_LOGIN, HASH_EMAIL" +
                " FROM Users WHERE HASH_LOGIN = ? OR HASH_EMAIL = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getHashLoginPass());
            statement.setString(2, user.getHashEmailPass());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String hashLogin = resultSet.getString(4);
                String hashEmail = resultSet.getString(5);
                result = new UserModel(id, login, email, hashLogin, hashEmail);
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
        String query = "UPDATE Users SET LOGIN=?, EMAIL=?, HASH_LOGIN=?, HASH_EMAIL=?" +
                " WHERE LOGIN=? OR EMAIL=?;";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getHashEmailPass());
            statement.setString(4, user.getHashEmailPass());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getEmail());
            result = statement.executeUpdate();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(UserModel user) {
        if (user == null) {
            return 0;
        }
        String query = "DELETE FROM Users WHERE LOGIN=? OR EMAIL=?";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
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
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        return statement.executeUpdate();
    }

}
