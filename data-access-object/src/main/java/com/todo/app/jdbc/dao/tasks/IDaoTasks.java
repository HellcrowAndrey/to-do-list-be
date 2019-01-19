package com.todo.app.jdbc.dao.tasks;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.dao.model.TaskDaoModel;

import java.util.List;

public interface IDaoTasks {

    long create(final TaskDaoModel data);

    List<TaskModel> read(final String data);

    long update(final TaskDaoModel data);

    long delete(final long idTask);

}
