package com.todo.app.client.api.delegat;

import com.todo.app.cache.manager.CacheManager;
import com.todo.app.decorator.UserDecorator;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.service.users.impl.ServiceUsersImpl;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.client.api.controller.RegistrationController;
import com.todo.app.utils.ControllerUtils;
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

    public String userRegistration(UserModel user) {
        if (user == null) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS, RegistrationController.class);
            return ControllerUtils.IS_NOT_VALID_PARAMS;
        }
        CacheManager manager = CacheManager.getInstance();
        if (manager.fetchRegUser(user) == null) {
            logger.info("User doesn't exist in cache. Do check in data base.", RegistrationDelegate.class);
            IServiceUsers service = new ServiceUsersImpl();
            long id = service.read(user.getLogin(), user.getEmail());
            if (id > 0) {
                logger.info("This user exist in data base.", RegistrationDelegate.class);
                return ControllerUtils.USER_EXIT;
            }
            UserDecorator decorator = new UserDecorator();
            UserModel newUser = decorator.createHash(user);
            manager.addUser(newUser);
            return service.create(newUser);
        } else {
            logger.info("User with this params exist in cache.", RegistrationDelegate.class);
            return ControllerUtils.USER_EXIT;
        }
    }

}
