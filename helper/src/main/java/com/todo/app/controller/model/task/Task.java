package com.todo.app.controller.model.task;

import java.util.Objects;

public class Task {

    private long id;

    private String taskName;

    private String task;

    private byte status;

    private String login;

    public Task(long id, String taskName, String task, byte status, String login) {
        this.id = id;
        this.taskName = taskName;
        this.task = task;
        this.status = status;
        this.login = login;
    }

    public Task(String taskName, String task, byte status, String login) {
        this.taskName = taskName;
        this.task = task;
        this.status = status;
        this.login = login;
    }

    public Task(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id &&
                status == task1.status &&
                Objects.equals(taskName, task1.taskName) &&
                Objects.equals(task, task1.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, task, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", task='" + task + '\'' +
                ", status=" + status +
                ", login='" + login + '\'' +
                '}';
    }
}
