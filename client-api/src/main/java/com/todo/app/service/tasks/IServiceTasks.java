package com.todo.app.service.tasks;

import com.todo.app.controller.model.task.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface IServiceTasks {

    int create(Task task);

    List<Task> read(String data);

    int update(Task data);

    int delete(Task data);

}
