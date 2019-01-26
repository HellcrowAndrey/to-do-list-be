package com.todo.app.jdbc.dao.tasks.impl;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.controller.model.task.TasksListModel;
import com.todo.app.dao.model.TaskDaoModel;
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
    public long create(final TaskDaoModel data) {
        if (data == null) {
            return 0;
        }
        final String query = "INSERT INTO Tasks(NAME, TASK, STATUS, ID_USER) VALUES (?, ?, ?, (SELECT ID FROM Users WHERE TOKEN = ?));";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, data.getTaskModel().getTaskName());
            statement.setString(2, data.getTaskModel().getTask());
            statement.setByte(3, data.getTaskModel().getStatus());
            statement.setString(4, data.getToken());
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

    private PreparedStatement getStatement(final Connection connection,
                                           final String data) throws SQLException {
        final String query = "SELECT T.ID, T.NAME, T.TASK, T.STATUS FROM" +
                " Tasks AS T INNER JOIN Users AS U ON  U.ID = T.ID_USER" +
                " WHERE U.TOKEN = ?;";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, data);
        return statement;
    }

    @Override
    public List<TaskModel> read(String token) {
        if (token == null || token.equals("")) {
            return null;
        }
        final List<TaskModel> tasks = new ArrayList<>();
        try (Connection connection = source.getConnect();
             PreparedStatement statement = getStatement(connection, token);
             ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, token);
            while (resultSet.next()) {
                TaskModel task = new TaskModel();
                task.setIdTask(resultSet.getLong(1));
                task.setTaskName(resultSet.getString(2));
                task.setTask(resultSet.getString(3));
                task.setStatus(resultSet.getByte(4));
                tasks.add(task);
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
    public long update(TaskDaoModel data) {
        if (data == null) {
            return 0;
        }
        long result = 0;
        final String query = "UPDATE Tasks SET NAME = ?, TASK = ?, STATUS = ? WHERE ID = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, data.getTaskModel().getTaskName());
            statement.setString(2, data.getTaskModel().getTask());
            statement.setByte(3, data.getTaskModel().getStatus());
            statement.setLong(4, data.getTaskModel().getIdTask());
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
    public long delete(final long idTask) {
        if (idTask == 0) {
            return 0;
        }
        final String query = "DELETE FROM Tasks WHERE ID = ?;";
        long result = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, idTask);
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
