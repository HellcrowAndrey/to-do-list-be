package com.client.api.delegat;

import com.cache.manager.CacheManager;
import com.dao.service.tasks.IServiceTasks;
import com.dao.service.tasks.impl.ServiceTasksImpl;
import com.dao.service.users.IServiceUsers;
import com.dao.service.users.impl.ServiceUsersImpl;
import com.helper.controller.model.task.Task;
import com.helper.controller.model.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthorizationDelegate {

    private Logger logger = LoggerFactory.getLogger(AuthorizationDelegate.class);

    public static boolean isParams(final String user, final String email, final String password) {
        if (user == null || user.equals("")) {
            if (email == null || email.equals("") ||
                    password == null || password.equals("")) {
                return false;
            }
        } else if (email == null || email.equals("")) {
            if (user == null || user.equals("") ||
                    password == null || password.equals("")) {
                return false;
            }
        }
        return true;
    }

    public List<Task> getData(UserModel authUser) {
        if (authUser == null) {
            logger.warn("User model is not valid.", AuthorizationDelegate.class);
            return null;
        }
        CacheManager manager = CacheManager.getInstance();
        UserModel userInCache = manager.fetchUser(authUser);
        if (userInCache != null) {
            logger.info("User find in cache.", AuthorizationDelegate.class);
            return manager.fetchTasks(userInCache);
        }
        return findData(authUser);
    }

    private List<Task> findData(UserModel authUser) {
        IServiceUsers serviceUsers = new ServiceUsersImpl();
        UserModel userInDb = serviceUsers.read(authUser);
        if (userInDb == null) {
            logger.warn("User doesn't find un cache.", AuthorizationDelegate.class);
            return null;
        } else {
            CacheManager manager = CacheManager.getInstance();
            logger.info("User find in db and add to cache.", AuthorizationDelegate.class);
            manager.addUser(userInDb);
            IServiceTasks serviceTasks = new ServiceTasksImpl();
            List<Task> tasks = serviceTasks.read(userInDb.getUser());
            logger.info("Return Tasks by user.", AuthorizationDelegate.class);
            return manager.addTasks(tasks, authUser.getUser());
        }
    }

}