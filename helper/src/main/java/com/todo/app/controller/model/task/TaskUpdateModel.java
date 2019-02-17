package com.todo.app.controller.model.task;

import java.util.Objects;

public class TaskUpdateModel {

    private static TaskUpdateModel TASK = new TaskUpdateModel();

    private String command;

    private String token;

    private String data;

    public TaskUpdateModel() {
    }

    public TaskUpdateModel(String command, String token, String data) {
        this.command = command;
        this.token = token;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public String getToken() {
        return token;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskUpdateModel that = (TaskUpdateModel) o;
        return Objects.equals(command, that.command) &&
                Objects.equals(token, that.token) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, token, data);
    }

    public boolean isTaskUpdateModel() {
        return this.equals(TASK);
    }
}
