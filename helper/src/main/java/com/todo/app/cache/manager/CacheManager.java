package com.todo.app.cache.manager;

import com.todo.app.controller.model.task.TaskModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheManager {

    private static CacheManager instance;

    private static final int MAX_CACHE_SIZE = 80;

    private int currentCacheSize = 0;

    /**
     * This map is cache app. Key is a user token value is a list with tasks.
     */
    private Map<String, List<TaskModel>> cache = new HashMap<>();

    /**
     * This method do create instance CacheManager class.
     *
     * @return object CacheManager
     */
    public static synchronized CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void fetchTasks() {

    }

    public void fetchTask() {
    }

    public void addTasks() {
    }

    public void addTask() {
    }

    public void updateTask() {
    }

    public void updateTasks() {
    }

    public void removeTasks() {
    }

    public void removeTask() {
    }

}
