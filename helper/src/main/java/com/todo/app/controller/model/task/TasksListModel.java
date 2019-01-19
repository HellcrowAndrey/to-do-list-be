package com.todo.app.controller.model.task;

import java.util.List;
import java.util.Objects;

public class TasksListModel {

    private String token;

    private List<TaskModel> tasks;

    /**
     * This is default constructor
     */
    public TasksListModel() {
        //This is constructor use if need create
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksListModel that = (TasksListModel) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, tasks);
    }

    @Override
    public String toString() {
        return "TasksListModel{" +
                "token='" + token + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
