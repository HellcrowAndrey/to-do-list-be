package com.todo.app.jdbc.dao.tasks;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.dao.model.TaskDaoModel;

import java.util.List;

/**
 * The IDaoTasks interface use if need create class for work with
 * sql data base(db) and table tasks. Has next methods create, read
 * update, delete. Method create received TaskDaoModel obj and do
 * create task in db, after that do return id (number row in db).
 * Method read received token user in db and do read user task in db.
 * Method update received TaskDaoModel obj and do update task in db.
 * Method delete received task id and do delete task in db after that
 * do return id.
 */
public interface IDaoTasks {

    /**
     * This is method create task in db. After call to this method
     * do check on null if received obj is null do return zero else
     * do create task user in db. In this method do handlers on db
     * exception if catch it do return zero.
     *
     * @param data obj with task data
     * @return id row in db
     */
    long create(final TaskDaoModel data);

    /**
     * This is method read user tasks in db. After call to this method
     * do check on null and empty data if data matches null or empty do
     * return null.
     *
     * @param data user token
     * @return array with the user tasks
     */
    List<TaskModel> read(final String data);

    /**
     * This is method do update user task in db. After call to this method
     * do check on null if data matches null do return zero.
     *
     * @param data obj with task data
     * @return id row in db
     */
    long update(final TaskDaoModel data);

    /**
     * This is method do delete user task in db. After call to this method
     * do check on zero if data matches do return zero else id row in db.
     *
     * @param idTask this is id  task in db
     * @return id or zero
     */
    long delete(final long idTask);

}
