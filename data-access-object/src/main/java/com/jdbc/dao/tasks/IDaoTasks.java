package com.jdbc.dao.tasks;

import com.helper.controller.model.task.Task;

import java.util.List;

public interface IDaoTasks {

    int create(Task data);

    List<Task> read(Task data);

    int update(Task data);

    int delete(int id);

}
