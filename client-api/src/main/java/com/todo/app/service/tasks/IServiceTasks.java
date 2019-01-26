package com.todo.app.service.tasks;

import com.todo.app.controller.model.task.TasksListModel;
import com.todo.app.dao.model.TaskDaoModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface IServiceTasks {

    long create(TaskDaoModel task);

    TasksListModel read(String data);

    long update(TaskDaoModel data);

    long delete(long idTask);

}
