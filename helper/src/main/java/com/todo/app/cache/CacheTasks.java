package com.todo.app.cache;

import com.todo.app.controller.model.task.TaskModel;

import java.util.List;

public class CacheTasks {

    private final long timeCreation;

    private List<TaskModel> tasks;

    public static class TaskBuilder {

        private long timeCreation;

        private List<TaskModel> tasks;

        public TaskBuilder(List<TaskModel> tasks) {
            this.timeCreation = System.currentTimeMillis();
            this.tasks = tasks;
        }

        public TaskBuilder add(TaskModel task) {
            tasks.add(task);
            return this;
        }

        public TaskBuilder update(TaskModel task) {
            //todo add check
            TaskModel result = tasks.stream().filter(t -> t.getIdTask() == task.getIdTask())
                    .findFirst().orElse(null);
            tasks.remove(result);
            tasks.add(task);
            return this;
        }

        public CacheTasks build() {
            return new CacheTasks(this);
        }
    }

    private CacheTasks(TaskBuilder builder) {
        this.timeCreation = builder.timeCreation;
        this.tasks = builder.tasks;
    }

    public long getTimeCreation() {
        return timeCreation;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

}
