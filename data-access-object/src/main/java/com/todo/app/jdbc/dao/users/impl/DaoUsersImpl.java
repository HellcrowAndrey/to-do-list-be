package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DaoUsersImpl implements IDaoUsers {

    private final IDataSource source;

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoUsersImpl.class);

    public DaoUsersImpl(final IDataSource source) {
        this.source = source;
    }

    @Override
    public int create(final UserModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "INSERT INTO Users (LOGIN, EMAIL, HASH_LOGIN," +
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
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    private boolean isParams(final String login, final String email) {
        return login == null || login.equals("") || email == null || email.equals("");
    }

    @Override
    public long read(final String login, final String email) {
        if (isParams(login, email)) {
            return 0;
        }
        long result = 0;
        final String query = "SELECT ID FROM Users WHERE LOGIN = ? OR EMAIL = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, login);
            statement.setString(2, login);
            while (resultSet.next()) {
                result = resultSet.getLong(1);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    private PreparedStatement getStatement(Connection connection,
                                           String query,
                                           UserModel user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getHashLoginPass());
        statement.setString(2, user.getHashEmailPass());
        return statement;
    }

    @Override
    public UserModel read(final UserModel user) {
        if (user == null) {
            return null;
        }
        final UserModel result = new UserModel();
        final String query = "SELECT ID, LOGIN, EMAIL, HASH_LOGIN, HASH_EMAIL" +
                " FROM Users WHERE HASH_LOGIN = ? OR HASH_EMAIL = ?";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = getStatement(connection, query, user);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.setIdUser(resultSet.getLong(1));
                result.setLogin(resultSet.getString(2));
                result.setEmail(resultSet.getString(3));
                result.setHashLoginPass(resultSet.getString(4));
                result.setHashEmailPass(resultSet.getString(5));
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public int update(final UserModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "UPDATE Users SET LOGIN=?, EMAIL=?, HASH_LOGIN=?, HASH_EMAIL=?" +
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
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public int delete(final UserModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "DELETE FROM Users WHERE LOGIN=? OR EMAIL=?";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

}
