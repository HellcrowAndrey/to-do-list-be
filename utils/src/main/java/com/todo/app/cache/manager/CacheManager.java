package com.todo.app.cache.manager;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.method.assistant.IsParams;

import java.util.*;

/**
 * The CacheManager class save information about users tasks.
 */
public class CacheManager {

    /**
     * This field is instance this class. Use for crete singleton
     * cache in client-api module.
     */
    private static CacheManager instance;

    /**
     * This constant is max cache size in map.
     */
    private static final int MAX_CACHE_SIZE = 100;

    /**
     * This is current cache size. Its field do start count field
     * in cache.
     */
    private int currentCacheSize = 0;

    /**
     * This map is cache app. Key is a user token value is a list with tasks.
     */
    private Map<String, List<TaskModel>> cache = new LinkedHashMap<>();

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

    public long addTask(String token, TaskModel model) {
        if (!IsParams.isParams(token, model)) {
            return 0;
        }
        long result = 0;
        if (currentCacheSize == 0) {
            addToCache(token, model);
            result = model.getIdTask();
            deleteFirstElement();
        } else {
            if (currentCacheSize >= MAX_CACHE_SIZE) {
                if (deleteFirstElement() != null) {
                    addToCache(token, model);
                    result = model.getIdTask();
                    currentCacheSize++;
                }
            }
            //todo add tasks
        }
        return result;
    }

    private List<TaskModel> deleteFirstElement() {
        String key = cache.entrySet().iterator().next().getKey();
        List<TaskModel> result = null;
        if (IsParams.isParams(key)) {
            result = cache.remove(key);
        }
        return result;
    }

    private void addToCache(String token, TaskModel model) {
        if (IsParams.isParams(token, model)) {
            List<TaskModel> array = cache.get(token);
            if (cache.get(token) == null) {
                array = new ArrayList<>();
                array.add(model);
                cache.put(token, array);
            } else {
                array.add(model);
                cache.put(token, array);
            }
        }
    }

    public void updateTask() {
    }

    public void updateTasks() {
    }

    public void removeTasks() {
    }

    public void removeTask() {
    }

    private class SeveData {

    }

}
