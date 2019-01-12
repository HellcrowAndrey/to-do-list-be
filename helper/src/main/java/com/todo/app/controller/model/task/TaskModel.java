package com.todo.app.controller.model.task;

import java.util.Objects;

public class TaskModel {

    private long idTask;

    private String taskName;

    private String task;

    private byte status;

    private String login;

    /**
     * This is default constructor class TaskModel.
     */
    public TaskModel() {
        // Use for avoid instance new object inside loops
    }

    public TaskModel(final long idTask, final String taskName,
                     final String task, final byte status, final String login) {
        this.idTask = idTask;
        this.taskName = taskName;
        this.task = task;
        this.status = status;
        this.login = login;
    }

    public TaskModel(final String taskName, final String task,
                     final byte status, final String login) {
        this.taskName = taskName;
        this.task = task;
        this.status = status;
        this.login = login;
    }

    public void setIdTask(final long idTask) {
        this.idTask = idTask;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public void setTask(final String task) {
        this.task = task;
    }

    public void setStatus(final byte status) {
        this.status = status;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public TaskModel(final String login) {
        this.login = login;
    }

    public long getIdTask() {
        return idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTask() {
        return task;
    }

    public byte getStatus() {
        return status;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final TaskModel task1 = (TaskModel) object;
        return idTask == task1.idTask &&
                status == task1.status &&
                Objects.equals(taskName, task1.taskName) &&
                Objects.equals(task, task1.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, taskName, task, status);
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + idTask +
                ", taskName='" + taskName + '\'' +
                ", task='" + task + '\'' +
                ", status=" + status +
                ", login='" + login + '\'' +
                '}';
    }
}
