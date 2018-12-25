package com.cache.manager;

import com.helper.controller.model.task.Task;
import com.helper.controller.model.user.UserModel;

import java.util.*;

public class CacheManager {

    private static CacheManager instance;

    private static final int MAX_CACHE_SIZE = 80;

    private int currentCacheSize = 0;

    private List<UserModel> users = new ArrayList<>();

    private Map<String, List<Task>> tasks = new LinkedHashMap<>();

    public static synchronized CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public UserModel fetchUser(UserModel user) {
        if (user == null) {
            return null;
        }
        return users.stream().filter(currentUser ->
                fetchUser(user, currentUser)).findFirst().orElse(null);
    }

    private boolean fetchUser(final UserModel user, final UserModel currentUser) {
        if (user.getLogin().equals(currentUser.getLogin()) &&
                user.getPassword().equals(currentUser.getPassword())) {
            return true;
        } else if (user.getEmail().equals(currentUser.getEmail()) &&
                user.getPassword().equals(currentUser.getPassword())) {
            return true;
        }
        return false;
    }

    public UserModel fetchRegUser(final UserModel user) {
        if (user == null) {
            return null;
        }
        return users.stream().filter(currentUser ->
                fetchRegUser(user, currentUser)).findFirst().orElse(null);
    }

    private boolean fetchRegUser(final UserModel user, final UserModel currentUser) {
        if (user.getLogin().equals(currentUser.getLogin()) ||
                user.getEmail().equals(currentUser.getEmail())) {
            return false;
        }
        return true;
    }

    public UserModel fetchToken(String login) {
        return users.stream().filter(current ->
                current.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<Task> fetchTasks(UserModel user) {
        if (user == null) {
            return null;
        }
        return tasks.get(user.getLogin());
    }

    public boolean addUser(final UserModel user) {
        if (user == null) {
            return false;
        }
        if (currentCacheSize >= MAX_CACHE_SIZE) {
            UserModel firstUser = users.get(0);
            tasks.remove(firstUser.getLogin());
            users.remove(0);
            return users.add(user);
        }
        currentCacheSize++;
        return users.add(user);
    }

    public List<Task> addTasks(List<Task> data, String key) {
        if (data == null || key == null || key.equals("")) {
            return null;
        }
        tasks.put(key, data);
        return tasks.get(key);
    }

    public boolean addTask(Task data) {
        if (data != null) {
            List<Task> list = tasks.get(data.getLogin());
            return list.add(data);
        }
        return false;
    }

    public boolean deleteTask(Task data) {
        if (data != null) {
            List<Task> list = tasks.get(data.getLogin());
            return list.remove(data);
        }
        return false;
    }

    public Task updateTask(Task data) {
        if (data != null) {
            List<Task> list = tasks.get(data.getLogin());
            long index = list.stream().filter(current ->
                    data.getId() == current.getId()).count();
            return list.set((int) index, data);
        }
        return null;
    }

}