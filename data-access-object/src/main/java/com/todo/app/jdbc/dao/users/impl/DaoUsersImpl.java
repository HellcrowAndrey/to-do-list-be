package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * The DaoUsersImpl class do implements interface IDaoUsers and
 * release these method. This class has linc on IDataSource interface,
 * exemplar class Logger, constructor class with param IDataSource.
 * Has next method:
 * - create with param UserDaoModel and do create user in date base (db);
 * - getStatement with params connection, data after call to this method
 * do create PreparedStatement.
 * - read with param do read user in db.
 * - update with param UserDaoModel do update user in db.
 * - delete with param do delete user in db.
 */
public class DaoUsersImpl implements IDaoUsers {

    /**
     * This is link on IDataSource interface. This interface has
     * method getConnect(). After call to this method do return
     * connection with params by profile.
     */
    private final IDataSource source;

    /**
     * This field is logger use for show error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoUsersImpl.class);

    /**
     * This is constructor class DaoUsersImpl with param. Received IDataSource interface
     * in param and do init source link.
     *
     * @param source this is interface IDataSource.
     */
    public DaoUsersImpl(final IDataSource source) {
        this.source = source;
    }

    /**
     * This is method create. Received UserDaoModel and do create user
     * in db. Field login, email and token in db are unique. After call
     * to this method do check on null if received object is null do
     * return zero else do create user in date base. In this method do
     * handlers on date base exceptions if catch it do return zero.
     *
     * @param user object user model in date base.
     * @return id or zero if received exception or received null object
     * in param.
     */
    @Override
    public long create(final UserDaoModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "INSERT INTO Users (LOGIN, EMAIL, HASH," +
                " SALT, TOKEN, ENABLE) VALUES (?, ?, ?, ?, ?, ?);";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection
                     .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setBytes(3, user.getHash());
            statement.setBytes(4, user.getSalt());
            statement.setString(5, user.getToken());
            statement.setBoolean(6, user.isEnable());
            result = getId(statement);
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

    private long getId(PreparedStatement statement) throws SQLException {
        long result = 0;
        ResultSet rs = null;
        try {
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }

    /**
     * This method do create PreparedStatement object. Received in params
     * connection and data.
     *
     * @param connection class connection.
     * @param login      This is login.
     * @param email      This is email.
     * @return object PreparedStatement
     * @throws SQLException An exception that provides information on
     *                      a database access error or other errors.
     */
    private PreparedStatement getStatement(final Connection connection,
                                           final String login,
                                           final String email) throws SQLException {
        final String query = "SELECT ID, LOGIN, EMAIL, HASH, SALT, TOKEN, ENABLE" +
                " FROM Users WHERE LOGIN = ? OR EMAIL = ?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        statement.setString(2, email);
        return statement;
    }

    /**
     * This method do read user in date base received login or email in
     * param. After call to this method do check on null or empty and if
     * data valid do read user in db else return null. In this method
     * do handlers on date base exception if catch it do return null.
     *
     * @param login This is login.
     * @param email This is email.
     * @return null or data about user.
     */
    @Override
    public UserDaoModel read(final String login, final String email) {
        if (login == null || login.equals("") ||
                email == null || email.equals("")) {
            return null;
        }
        UserDaoModel result = new UserDaoModel();
        try (Connection connection = source.getConnect();
             PreparedStatement statement = getStatement(connection, login, email);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.setIdUser(resultSet.getLong(1));
                result.setLogin(resultSet.getString(2));
                result.setEmail(resultSet.getString(3));
                result.setHash(resultSet.getBytes(4));
                result.setSalt(resultSet.getBytes(5));
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

    /**
     * This method do update user in db, received UserDaoModel. After call to this
     * method do check on null, if data null do return zero else do update user in
     * data base. In this method do handlers on data base exception if catch it do
     * return zero.
     *
     * @param user object user model in date base.
     * @return id or zero if received wrong data or received exception.
     */
    @Override
    public long update(final UserDaoModel user) {
        if (user == null) {
            return 0;
        }
        final String query = "UPDATE Users SET LOGIN=?, HASH=?, ENABLE=? WHERE EMAIL=?;";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection
                     .prepareStatement(query)) {
            statement.setString(1, user.getLogin());
            statement.setBytes(2, user.getHash());
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

    /**
     * This method do delete user in db, received use email. After call to this
     * method do check on null or empty. If data don't valid do return zero else
     * do delete user in data base. In this method do handlers data base exception
     * if catch it do return zero.
     *
     * @param email this is user email.
     * @return id or zero if received wrong data or catch exception.
     */
    @Override
    public long delete(final String email) {
        if (email == null || email.equals("")) {
            return 0;
        }
        final String query = "DELETE FROM Users WHERE EMAIL=?";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
