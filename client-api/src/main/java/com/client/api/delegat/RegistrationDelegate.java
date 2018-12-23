package com.client.api.delegat;

import com.cache.manager.UsersCacheManager;
import com.dao.service.users.IServiceUsers;
import com.dao.service.users.impl.ServiceUsersImpl;
import com.helper.controller.model.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationDelegate {

    private Logger logger = LoggerFactory.getLogger(RegistrationDelegate.class);

    public static boolean isParams(String user, String email, String password) {
        if (user == null || user.equals("") ||
                email == null || email.equals("") ||
                password == null || password.equals("")) {
            return false;
        }
        return true;
    }

    public boolean setUser(final UserModel user) {
        UsersCacheManager cacheManager = UsersCacheManager.getInstance();
        UserModel userInCache = cacheManager.fetchRegUser(user);
        if (userInCache == null) {
            logger.info("User doesn't exist in cache. Do check in data base.", RegistrationDelegate.class);
            IServiceUsers service = new ServiceUsersImpl();
            UserModel userInDb = service.read(user);
            if (userInDb == null) {
                logger.info("User add to cache and data base.", RegistrationDelegate.class);
                return cacheManager.addUser(user) && service.create(user);
            }
            logger.info("This user exist in data base.", RegistrationDelegate.class);
            return false;
        }
        logger.info("User with this params exist in cache.", RegistrationDelegate.class);
        return false;
    }

}
