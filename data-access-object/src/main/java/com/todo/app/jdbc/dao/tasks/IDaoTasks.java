package com.todo.app.jdbc.dao.tasks;

import com.todo.app.controller.model.task.TaskModel;

import java.util.List;

public interface IDaoTasks {

    int create(TaskModel data);

    List<TaskModel> read(String data);

    int update(TaskModel data);

    int delete(TaskModel data);

}
