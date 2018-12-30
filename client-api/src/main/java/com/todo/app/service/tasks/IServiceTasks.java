package com.todo.app.service.tasks;

import com.todo.app.controller.model.task.Task;

import java.util.List;

public interface IServiceTasks {

    int create(Task task);

    List<Task> read(String data);

    int update(Task data);

    int delete(Task data);

}
