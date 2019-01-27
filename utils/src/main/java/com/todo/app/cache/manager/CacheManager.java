package com.todo.app.cache.manager;

import com.todo.app.cache.CacheTasks;
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
    private Map<String, CacheTasks> cache = new LinkedHashMap<>();

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

    private CacheManager() {
    }

    public List<TaskModel> fetchTasks(String token) {
        if (!IsParams.isParams(token)) {
            return null;
        } else {
            CacheTasks inCache = cache.get(token);
            if (inCache != null) {
                CacheTasks newTimeUseThis = new CacheTasks
                        .TaskBuilder(inCache.getTasks()).build();
                cache.put(token, newTimeUseThis);
                return inCache.getTasks();
            } else {
                return null;
            }
        }
    }

    public TaskModel fetchTask(String token, long id) {
        if (!IsParams.isParams(token) || id <= 0) {
            return null;
        }
        CacheTasks inCache = cache.get(token);
        if (inCache == null) {
            return null;
        } else {
            CacheTasks newTimeUseThis = new CacheTasks
                    .TaskBuilder(inCache.getTasks()).build();
            cache.put(token, newTimeUseThis);
            return inCache.getTasks()
                    .stream()
                    .filter(task -> task.getIdTask() == id)
                    .findFirst().orElse(null);
        }
    }

    public void addTasks(String token, List<TaskModel> tasks) {
        if (IsParams.isParams(token, tasks)) {
            if (currentCacheSize == 0) {
                CacheTasks newTasks = new CacheTasks.TaskBuilder(tasks).build();
                cache.put(token, newTasks);
            } else {
                if (currentCacheSize >= MAX_CACHE_SIZE) {
                    maxCacheSize(token, tasks);
                } else {
                    CacheTasks newTasks = new CacheTasks.TaskBuilder(tasks).build();
                    cache.put(token, newTasks);
                }
            }
            currentCacheSize++;
        }
    }

    private void maxCacheSize(String token, List<TaskModel> tasks) {
        if (IsParams.isParams(token, tasks)) {
            if (cache.get(token) == null) {
                String minKey = minKeyInCache();
                cache.remove(minKey);
                CacheTasks newTaskToCache = new CacheTasks
                        .TaskBuilder(tasks).build();
                cache.put(token, newTaskToCache);
            } else {
                CacheTasks existTaskByToken = new CacheTasks
                        .TaskBuilder(tasks).build();
                cache.put(token, existTaskByToken);
            }
        }
    }

    private String minKeyInCache() {
        CacheTasks minValue = cache.entrySet()
                .stream().iterator().next().getValue();
        String minKey = cache.keySet().iterator().next();
        for (Map.Entry<String, CacheTasks> currentValue : cache.entrySet()) {
            if (findMinTimePredicate(minValue, currentValue)) {
                minKey = currentValue.getKey();
                minValue = currentValue.getValue();
            }
        }
        return minKey;
    }

    private boolean findMinTimePredicate(CacheTasks minValue,
                                         Map.Entry<String, CacheTasks> currentValue) {
        return minValue.getTimeCreation() >
                currentValue.getValue().getTimeCreation();
    }

    public void addTask(String token, TaskModel task) {
        if (IsParams.isParams(token, task)) {
            CacheTasks result = cache.get(token);
            if (result != null) {
                CacheTasks cacheTasks = new CacheTasks
                        .TaskBuilder(result.getTasks()).add(task).build();
                cache.put(token, cacheTasks);
            } else {
                CacheTasks cacheTasks = new CacheTasks
                        .TaskBuilder(new ArrayList<>()).add(task).build();
                cache.put(token, cacheTasks);
            }
        }
    }

    public long updateTask(String token, TaskModel task) {
        if (!IsParams.isParams(token, task)) {
            return 0;
        }
        CacheTasks inCache = cache.get(token);
        if (inCache == null) {
            CacheTasks newTaskToCache = new CacheTasks
                    .TaskBuilder(new ArrayList<>()).update(task).build();
            cache.put(token, newTaskToCache);
            return task.getIdTask();
        }
        CacheTasks newTaskToCache = new CacheTasks
                .TaskBuilder(inCache.getTasks()).update(task).build();
        cache.put(token, newTaskToCache);
        return task.getIdTask();
    }

    public void updateTasks() {
    }

    public void removeTasks() {
    }

    public void removeTask() {
    }

}
