package com.todo.app.service.tasks;

import com.todo.app.controller.model.task.TaskModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface IServiceTasks {

    int create(TaskModel task);

    List<TaskModel> read(String data);

    int update(TaskModel data);

    int delete(TaskModel data);

}
