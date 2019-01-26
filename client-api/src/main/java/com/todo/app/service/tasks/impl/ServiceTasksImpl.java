package com.todo.app.service.tasks.impl;

import com.todo.app.controller.model.task.TasksListModel;
import com.todo.app.dao.model.TaskDaoModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.tasks.IDaoTasks;
import com.todo.app.jdbc.dao.tasks.impl.DaoTasksImpl;
import com.todo.app.service.tasks.IServiceTasks;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ServiceTasksImpl implements IServiceTasks {

    private IDataSource source;

    public ServiceTasksImpl(IDataSource source) {
        this.source = source;
    }

    @Override
    public long create(TaskDaoModel data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.create(data);
    }

    @Override
    public TasksListModel read(String data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.read(data);
    }

    @Override
    public long update(TaskDaoModel data) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.update(data);
    }

    @Override
    public long delete(long idTask) {
        IDaoTasks tasks = new DaoTasksImpl(source);
        return tasks.delete(idTask);
    }

}
