package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.users.IDaoUsers;
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
    public long create(UserDaoModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "INSERT INTO Users (LOGIN, EMAIL, PASSWORD_HASH, SALT, TOKEN, ENABLE) VALUES (?, ?, ?, ?, ?, ?);";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getSalt());
            statement.setString(5, user.getToken());
            statement.setBoolean(6, user.isEnable());
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

    private PreparedStatement getStatement(final Connection connection,
                                           final String data) throws SQLException {
        final String query = "SELECT ID, LOGIN, EMAIL, PASSWORD_HASH, SALT, TOKEN, ENABLE" +
                " FROM Users WHERE LOGIN = ? OR EMAIL = ?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, data);
        statement.setString(2, data);
        return statement;
    }

    @Override
    public UserDaoModel read(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        final UserDaoModel result = new UserDaoModel();

        try (Connection connection = source.getConnect();
             PreparedStatement statement = getStatement(connection, data);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.setIdUser(resultSet.getLong(1));
                result.setLogin(resultSet.getString(2));
                result.setEmail(resultSet.getString(3));
                result.setPasswordHash(resultSet.getString(4));
                result.setSalt(resultSet.getString(5));
                result.setToken(resultSet.getString(6));
                result.setEnable(resultSet.getBoolean(7));
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
    public long update(UserDaoModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "UPDATE Users SET LOGIN=?, PASSWORD_HASH=?, ENABLE=? WHERE EMAIL=?;";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPasswordHash());
            statement.setBoolean(3, user.isEnable());
            statement.setString(4, user.getEmail());
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
    public long delete(String email) {
        if (email == null || email.equals("")) {
            return 0;
        }
        final String query = "DELETE FROM Users WHERE EMAIL=?";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
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
