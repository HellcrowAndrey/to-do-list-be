package app.jdbc.dao.tasks.impl;

import com.todo.app.controller.model.task.Task;
import app.jdbc.dao.tasks.IDaoTasks;
import app.jdbc.utils.DaoUtils;
import app.jdbc.dao.data.source.IDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoTasksImpl implements IDaoTasks {

    private IDataSource source;

    public DaoTasksImpl(IDataSource source) {
        this.source = source;
    }

    @Override
    public int create(Task data) {
        if (data == null) {
            return 0;
        }
        String generatedColumns[] = {"ID"};
        String query = "INSERT INTO Tasks(NAME, TASK, STATUS, ID_USER) VALUES" +
                " (?, ?, ?, (SELECT ID FROM Users WHERE LOGIN = ?));";
        int taskId = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, generatedColumns)) {
            statement.setString(1, data.getTaskName());
            statement.setString(2, data.getTask());
            statement.setByte(3, data.getStatus());
            statement.setString(4, data.getLogin());
            int result = statement.executeUpdate();
            taskId = DaoUtils.returnId(result, statement);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskId;
    }

    @Override
    public List<Task> read(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT T.ID, T.NAME, T.TASK, T.STATUS, U.LOGIN FROM" +
                " Tasks AS T INNER JOIN Users AS U ON  U.ID = T.ID_USER" +
                " WHERE U.LOGIN = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String task = resultSet.getString(3);
                byte status = resultSet.getByte(4);
                String user = resultSet.getString(5);
                Task entity = new Task(id, name, task, status, user);
                tasks.add(entity);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public int update(Task task) {
        if (task == null) {
            return 0;
        }
        String generatedColumns[] = {"ID"};
        int taskId = 0;
        String query = "UPDATE Tasks SET NAME = ?, TASK = ?, STATUS = ? WHERE ID = ?;";
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, generatedColumns)) {
            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getTask());
            statement.setByte(3, task.getStatus());
            statement.setLong(4, task.getId());
            int result = statement.executeUpdate();
            taskId = DaoUtils.returnId(result, statement);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskId;
    }

    @Override
    public int delete(Task data) {
        if (data == null) {
            return 0;
        }
        String query = "DELETE FROM Tasks WHERE ID = ?;";
        String generatedColumns[] = {"ID"};
        int taskId = 0;
        try (Connection connection = source.getConnect();
             PreparedStatement statement = connection.prepareStatement(query, generatedColumns)) {
            statement.setLong(1, data.getId());
            int result = statement.executeUpdate();
            taskId = DaoUtils.returnId(result, statement);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskId;
    }

}
