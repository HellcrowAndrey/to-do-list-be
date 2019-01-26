package com.todo.app.dao.model;

import com.todo.app.controller.model.task.TaskModel;

import java.util.Objects;

public class TaskDaoModel {

    private String token;

    private TaskModel taskModel;

    /**
     * This is default constructor.
     */
    public TaskDaoModel() {
        //This constructor use if need instance new object inside loop
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(final TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final TaskDaoModel that = (TaskDaoModel) object;
        return Objects.equals(token, that.token) &&
                Objects.equals(taskModel, that.taskModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, taskModel);
    }

    @Override
    public String toString() {
        return "TaskDaoModel{" +
                "token='" + token + '\'' +
                ", taskModel=" + taskModel +
                '}';
    }
}
