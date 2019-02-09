package com.todo.app.service.tasks;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.controller.model.task.TasksListModel;
import com.todo.app.dao.model.TaskDaoModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The IServiceTasks interface use for encapsulation
 * call to doo class and methods.
 */
public interface IServiceTasks {

    /**
     * This methods call to method create task
     * from IDaoTasks interface
     *
     * @param task obj with task data
     * @return id row in db
     */
    long create(TaskDaoModel task);

    /**
     * This method call to method read
     * from IDaoTasks interface.
     *
     * @param data this user token
     * @return array with tasks
     */
    List<TaskModel> read(String data);

    /**
     * This method call to method update
     * from IDaoTasks interface.
     *
     * @param data obj with task
     * @return id row in db
     */
    long update(TaskDaoModel data);

    /**
     * This method call to method delete
     * from IDaoTasks interface.
     *
     * @param idTask id user task
     * @return id row in db
     */
    long delete(long idTask);

}
