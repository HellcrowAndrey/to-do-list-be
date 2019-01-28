package com.todo.app.cache.manager;

import com.todo.app.cache.CacheTasks;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.method.assistant.IsParams;

import java.util.*;

/**
 * The CacheManager class save information about users tasks.
 * This class use LinkedHashMap() as cache for token and tasks,
 * key in this map is user token value is List with user tasks.
 * This class keeps next methods: fetchTasks, fetchTask, addTasks,
 * maxCacheSize, minKeyInCache, updateTask, removeTask.
 * <p>
 * - fetchTasks use if need received array with tasks;
 * - fetchTask use if need received one task;
 * - addTasks use if need do add tasks to cache;
 * - maxCacheSize do work if cache has max size;
 * - minKeyInCache this method do find min time in cache;
 * - updateTask this method do update task in cache by token;
 * - removeTask this method do delete task in cache.
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
     * This map is cache this app. Key is a user token value
     * is a list with tasks.
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

    /**
     * This is private constructor. Use for create
     * singleton of this class.
     */
    private CacheManager() {
        //This is private constructor of class.
    }

    /**
     * This method do find tasks in cache after that do return
     * array with tasks if tasks exist in cache do return null.
     * After call to this method first to fall do check on valid
     * param. If param doesn't valid do return null else do get
     * object CacheTasks of cache and if obj null do return null
     * else return tasks.
     *
     * @param token this is user token.
     * @return array with user task.
     */
    public List<TaskModel> fetchTasks(final String token) {
        if (!IsParams.isParams(token)) {
            return null;
        } else {
            final CacheTasks inCache = cache.get(token);
            if (inCache != null) {
                final CacheTasks newTimeUseThis = new CacheTasks
                        .TaskBuilder(inCache.getTasks()).build();
                cache.put(token, newTimeUseThis);
                return inCache.getTasks();
            } else {
                return null;
            }
        }
    }

    /**
     * This method do find task in cache after that do return
     * object TaskModel with data about user task. After call
     * to this method first to fall do check on valid params,
     * if params doesn't valid return null else get object of
     * cache, and if obj null do return null else return obj
     * TaskModel.
     *
     * @param token this is user token.
     * @param id    this is id task in cache.
     * @return object TaskModel.
     */
    public TaskModel fetchTask(final String token, final long id) {
        if (!IsParams.isParams(token) || id <= 0) {
            return null;
        }
        final CacheTasks inCache = cache.get(token);
        if (inCache == null) {
            return null;
        } else {
            final CacheTasks newTimeUseThis = new CacheTasks
                    .TaskBuilder(inCache.getTasks()).build();
            cache.put(token, newTimeUseThis);
            return inCache.getTasks()
                    .stream()
                    .filter(task -> task.getIdTask() == id)
                    .findFirst().orElse(null);
        }
    }

    /**
     * This method do add tasks to cache. After call to this
     * method first to fall do check on valid params if params
     * doesn't valid do nothing else do check on currentCacheSize
     * if it equals zero do create CacheTasks object and put to
     * map (cache) else do check on max size cache if its true
     * do call method maxCacheSize else do create CacheTasks
     * obj and put to map.
     *
     * @param token this is user token.
     * @param tasks array with user task.
     */
    public void addTasks(final String token,
                         final List<TaskModel> tasks) {
        if (IsParams.isParams(token, tasks)) {
            if (currentCacheSize == 0) {
                CacheTasks newTasks = new CacheTasks
                        .TaskBuilder(tasks).build();
                cache.put(token, newTasks);
            } else {
                if (currentCacheSize >= MAX_CACHE_SIZE) {
                    maxCacheSize(token, tasks);
                } else {
                    CacheTasks newTasks = new CacheTasks
                            .TaskBuilder(tasks).build();
                    cache.put(token, newTasks);
                }
            }
            currentCacheSize++;
        }
    }

    /**
     * This method do work if cache has max size. After call to
     * this method do check on valid params if params doesn't
     * valid do nothing else do find obj (CacheTasks) in cache
     * if obj equals null do call to method minKeyInCache() and
     * do find last use obj in cache and remove this obj after that
     * do create new obj and put to map (cache) else do create new
     * obj and put to map.
     *
     * @param token this is user token.
     * @param tasks array with user task.
     */
    private void maxCacheSize(final String token,
                              final List<TaskModel> tasks) {
        if (IsParams.isParams(token, tasks)) {
            if (cache.get(token) == null) {
                final String minKey = minKeyInCache();
                cache.remove(minKey);
                final CacheTasks newTaskToCache = new CacheTasks
                        .TaskBuilder(tasks).build();
                cache.put(token, newTaskToCache);
            } else {
                final CacheTasks existTaskByToken = new CacheTasks
                        .TaskBuilder(tasks).build();
                cache.put(token, existTaskByToken);
            }
        }
    }

    /**
     * This method do find seldom used obj in map (cache).
     * Firs step find first element in map (cache) second
     * step get its key after that do find min value and
     * return its key.
     *
     * @return key min element.
     */
    private String minKeyInCache() {
        CacheTasks minValue = cache.entrySet()
                .stream().iterator().next().getValue();
        String minKey = cache.keySet().iterator().next();
        for (Map.Entry<String, CacheTasks> currentValue :
                cache.entrySet()) {
            if (findMinTimePredicate(minValue, currentValue)) {
                minKey = currentValue.getKey();
                minValue = currentValue.getValue();
            }
        }
        return minKey;
    }

    /**
     * This method compere two which received in params.
     *
     * @param minValue
     * @param currentValue this is current value in map
     * @return true or false
     */
    private boolean findMinTimePredicate(final CacheTasks minValue,
                                         final Map.Entry<String, CacheTasks>
                                                 currentValue) {
        return minValue.getTimeCreation() >
                currentValue.getValue().getTimeCreation();
    }

    /**
     * This method do add task to cache. After call to this
     * method first to fall do check on valid params if params
     * doesn't valid do nothing else do check CacheTasks in
     * cache by token. If this object exist do update in array
     * else do crete new CacheTasks object and new array and add
     * task to array next step do put to map.
     *
     * @param token this is user token.
     * @param task  obj model user task.
     */
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

    /**
     * This method do update task inside CacheTasks obj.
     * After call to this method do check on valid params
     * if params doesn't valid do return zero, else do find
     * obj in cache if obj equals null do create new CacheTasks
     * and put to map and return id else do update cache return id.
     *
     * @param token this is user token.
     * @param task  obj model user task.
     * @return
     */
    public long updateTask(final String token, final TaskModel task) {
        if (!IsParams.isParams(token, task)) {
            return 0;
        }
        final CacheTasks inCache = cache.get(token);
        if (inCache == null) {
            final CacheTasks newTaskToCache = new CacheTasks
                    .TaskBuilder(new ArrayList<>()).update(task).build();
            cache.put(token, newTaskToCache);
            return task.getIdTask();
        }
        final CacheTasks newTaskToCache = new CacheTasks
                .TaskBuilder(inCache.getTasks()).update(task).build();
        cache.put(token, newTaskToCache);
        return task.getIdTask();
    }

    /**
     * This method do remove task inside CacheTasks obj.
     * After call to this method do check on valid params
     * if params doesn't valid do return zero, else do find
     * obj in cache if obj equals null do return zero else
     * create new obj update it and put in cache.
     *
     * @param token this is user token.
     * @param id    this is id user task.
     * @return id removed tasks
     */
    public long removeTask(final String token, final long id) {
        if (!IsParams.isParams(token) || id <= 0) {
            return 0;
        }
        final CacheTasks inCache = cache.get(token);
        if (inCache == null) {
            return 0;
        }
        final CacheTasks newTaskToCache = new CacheTasks
                .TaskBuilder(inCache.getTasks()).remove(id).build();
        cache.put(token, newTaskToCache);
        return id;
    }

}
