package com.todo.app.controller.model.task;

import java.util.Objects;

public class TaskModel {

    protected long idTask;

    protected String taskName;

    protected String task;

    protected byte status;

    /**
     * This is default constructor class TaskModel.
     */
    public TaskModel() {
        // Use for avoid instance new object inside loops
    }

    public TaskModel(long idTask, String taskName, String task, byte status) {
        this.idTask = idTask;
        this.taskName = taskName;
        this.task = task;
        this.status = status;
    }

    public TaskModel(String taskName, String task, byte status) {
        this.taskName = taskName;
        this.task = task;
        this.status = status;
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
                "idTask=" + idTask +
                ", taskName='" + taskName + '\'' +
                ", task='" + task + '\'' +
                ", status=" + status +
                '}';
    }
}
