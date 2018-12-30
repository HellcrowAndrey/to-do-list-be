package app.jdbc.dao.tasks;

import com.todo.app.controller.model.task.Task;

import java.util.List;

public interface IDaoTasks {

    int create(Task data);

    List<Task> read(String data);

    int update(Task data);

    int delete(Task data);

}
