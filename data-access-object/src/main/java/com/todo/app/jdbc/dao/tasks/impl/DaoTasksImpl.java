package com.todo.app.jdbc.dao.tasks.impl;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.jdbc.dao.tasks.IDaoTasks;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoTasksImpl implements IDaoTasks {

    private final IDataSource source;

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoTasksImpl.class);

    public DaoTasksImpl(final IDataSource source) {
        this.source = source;
    }

    @Override
    public int create(final TaskModel data) {
        if (data == null) {
            return 0;
        }
        final String query = "INSERT INTO Tasks(NAME, TASK, STATUS, ID_USER) VALUES" +
                " (?, ?, ?, (SELECT ID FROM Users WHERE LOGIN = ?));";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, data.getTaskName());
            statement.setString(2, data.getTask());
            statement.setByte(3, data.getStatus());
            statement.setString(4, data.getLogin());
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

    private PreparedStatement getStatement(Connection connection,
                                          String query,
                                          String data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, data);
        return statement;
    }

    @Override
    public List<TaskModel> read(final String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        final List<TaskModel> tasks = new ArrayList<>();
        final String query = "SELECT T.ID, T.NAME, T.TASK, T.STATUS, U.LOGIN FROM" +
                " Tasks AS T INNER JOIN Users AS U ON  U.ID = T.ID_USER" +
                " WHERE U.LOGIN = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = getStatement(connection, query, data);
             ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, data);
            final TaskModel entity = new TaskModel();
            while (resultSet.next()) {
                entity.setIdTask(resultSet.getLong(1));
                entity.setTaskName(resultSet.getString(2));
                entity.setTask(resultSet.getString(3));
                entity.setStatus(resultSet.getByte(4));
                entity.setLogin(resultSet.getString(5));
                tasks.add(entity);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return tasks;
    }

    @Override
    public int update(final TaskModel task) {
        if (task == null) {
            return 0;
        }
        int result = 0;
        final String query = "UPDATE Tasks SET NAME = ?, TASK = ?, STATUS = ? WHERE ID = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getTask());
            statement.setByte(3, task.getStatus());
            statement.setLong(4, task.getIdTask());
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
    public int delete(final TaskModel data) {
        if (data == null) {
            return 0;
        }
        final String query = "DELETE FROM Tasks WHERE ID = ?;";
        int result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, data.getIdTask());
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

}
