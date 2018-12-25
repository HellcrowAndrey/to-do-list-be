package com.dao.service.tasks;

import com.helper.controller.model.task.Task;

import java.util.List;

public interface IServiceTasks {

    int create(Task task);

    List<Task> read(String data);

    int update(Task data);

    int delete(Task data);

}
