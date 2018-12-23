package com.cache.manager;

import com.helper.controller.model.task.Task;
import com.helper.controller.model.user.UserModel;

import java.util.*;

public class UsersCacheManager {

    private static UsersCacheManager instance;

    private static final int MAX_CACHE_SIZE = 80;

    private int currentCacheSize = 0;

    private List<UserModel> users = new ArrayList<>();

    //todo add logic to this map
    private Map<String, List<Task>> tasks = new LinkedHashMap<>();

    public static synchronized UsersCacheManager getInstance() {
        if (instance == null) {
            instance = new UsersCacheManager();
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

    public UserModel fetchRegUser(final UserModel user) {
        if (user == null) {
            return null;
        }
        return users.stream().filter(currentUser ->
                fetchRegUser(user, currentUser)).findFirst().orElse(null);
    }

    private boolean fetchRegUser(final UserModel user, final UserModel currentUser) {
        if (user.getUser().equals(currentUser.getUser()) ||
                user.getEmail().equals(currentUser.getEmail())) {
            return false;
        }
        return true;
    }

    private boolean fetchUser(final UserModel user, final UserModel currentUser) {
        if (user.getUser().equals(currentUser.getUser()) &&
                user.getPassword().equals(currentUser.getPassword())) {
            return true;
        } else if (user.getEmail().equals(currentUser.getEmail()) &&
                user.getPassword().equals(currentUser.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean addUser(final UserModel user) {
        if (user == null) {
            return false;
        }
        if (currentCacheSize >= MAX_CACHE_SIZE) {
            users.remove(0);
            return users.add(user);
        }
        currentCacheSize++;
        return users.add(user);
    }

}
