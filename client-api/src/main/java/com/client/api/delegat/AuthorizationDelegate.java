package com.client.api.delegat;

import com.cache.manager.UsersCacheManager;
import com.dao.service.users.IServiceUsers;
import com.dao.service.users.impl.ServiceUsersImpl;
import com.helper.controller.model.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public boolean getData(final UserModel authUser) {
        if (authUser == null) {
            logger.warn("User model is not valid.", AuthorizationDelegate.class);
            return false;
        }
        UsersCacheManager manager = UsersCacheManager.getInstance();
        UserModel userInCache = manager.fetchUser(authUser);
        if (userInCache != null) {
            logger.info("User find in cache.", AuthorizationDelegate.class);
            return true;
        }
        IServiceUsers serviceUsers = new ServiceUsersImpl();
        UserModel userInDb = serviceUsers.read(authUser);
        if (userInDb == null) {
            logger.warn("User doesn't find un cache.", AuthorizationDelegate.class);
            return false;
        }
        logger.info("User find in db and add to cache.", AuthorizationDelegate.class);
        return manager.addUser(userInDb);
    }

}