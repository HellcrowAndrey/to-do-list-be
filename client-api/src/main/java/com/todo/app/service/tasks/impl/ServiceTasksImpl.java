package com.todo.app.service.tasks.impl;

import com.todo.app.controller.model.task.Task;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.tasks.IDaoTasks;
import com.todo.app.jdbc.dao.tasks.impl.DaoTasksImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ServiceTasksImpl implements IServiceTasks {

    private IDataSource source;

    public ServiceTasksImpl(IDataSource source) {
        this.source = source;
    }

    @Override
    public int create(Task data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.create(data);
    }

    @Override
    public List<Task> read(String  data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.read(data);
    }

    @Override
    public int update(Task data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.update(data);
    }

    @Override
    public int delete(Task data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.delete(data);
    }

}
