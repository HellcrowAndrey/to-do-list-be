package com.todo.app.service.tasks.impl;

import com.todo.app.controller.model.task.Task;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlConnection;
import com.todo.app.jdbc.dao.tasks.IDaoTasks;
import com.todo.app.jdbc.dao.tasks.impl.DaoTasksImpl;

import java.util.List;

public class ServiceTasksImpl implements IServiceTasks {

    @Override
    public int create(Task data) {
        IDaoTasks tasks = init();
        return tasks.create(data);
    }

    @Override
    public List<Task> read(String  data) {
        IDaoTasks tasks = init();
        return tasks.read(data);
    }

    @Override
    public int update(Task data) {
        IDaoTasks tasks = init();
        return tasks.update(data);
    }

    @Override
    public int delete(Task data) {
        IDaoTasks tasks = init();
        return tasks.delete(data);
    }

    private IDaoTasks init() {
        IDataSource source = new MySqlConnection();
        return new DaoTasksImpl(source);
    }

}
