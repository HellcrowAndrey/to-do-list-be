package com.helper.controller.model.task;

import java.util.Objects;

public class Task {

    private long id;

    private String name;

    private String task;

    private byte status;

    private String user;

    public Task(long id, String name, String task, byte status, String user) {
        this.id = id;
        this.name = name;
        this.task = task;
        this.status = status;
        this.user = user;
    }

    public Task(String name, String task, byte status, String user) {
        this.name = name;
        this.task = task;
        this.status = status;
        this.user = user;
    }

    public Task(String user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public byte getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id &&
                status == task1.status &&
                Objects.equals(name, task1.name) &&
                Objects.equals(task, task1.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, task, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", task='" + task + '\'' +
                ", status=" + status +
                ", user='" + user + '\'' +
                '}';
    }
}
