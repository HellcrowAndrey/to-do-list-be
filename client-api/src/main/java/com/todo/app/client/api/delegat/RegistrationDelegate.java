package com.todo.app.client.api.delegat;

import com.todo.app.controller.model.ResponseModel;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RegistrationDelegate {

    private Logger logger = LoggerFactory.getLogger(RegistrationDelegate.class);

    private IServiceUsers serviceUsers;

    public RegistrationDelegate(@Qualifier("serviceUsersImpl") IServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    public static ResponseEntity isParams(String user, String email, String password) {
        if (user == null || user.equals("") ||
                email == null || email.equals("") ||
                password == null || password.equals("")) {
            ResponseModel responseModel = new ResponseModel(IdGenerator.getInstance()
                    .getCounter(), ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<>(responseModel, HttpStatus.NO_CONTENT);
        }
        return null;
    }

    public String submitRegistration(UserModel user) {
//        if (user == null) {
//            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS);
//            return ControllerUtils.IS_NOT_VALID_PARAMS;
//        }
//        CacheManager manager = CacheManager.getInstance();
//        if (manager.fetchRegUser(user) == null) {
//            logger.info("User doesn't exist in cache. Do check in data base.");
//            long id = serviceUsers.read(user.getLogin(), user.getEmail());
//            if (id > 0) {
//                logger.info("This user exist in data base.");
//                return ControllerUtils.USER_EXIT;
//            }
//            UserDecorator decorator = new UserDecorator();
//            UserModel newUser = decorator.createHash(user);
//            manager.addUser(newUser);
//            return serviceUsers.create(newUser);
//        } else {
//            logger.info("User with this params exist in cache.");
//            return ControllerUtils.USER_EXIT;
//        }
        return null;
    }

}
