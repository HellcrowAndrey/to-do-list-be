package com.todo.app.client.api.delegat;

import com.todo.app.cache.manager.CacheManager;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.password.IPasswords;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.token.IToken;
import com.todo.app.token.impl.TokenGeneratorImpl;
import com.todo.app.user.CreateUser;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.validators.DataValidators;
import com.todo.app.validators.impl.UserValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RegistrationDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationDelegate.class);

    private IServiceUsers serviceUsers;

    public RegistrationDelegate(IServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    public ResponseEntity<String> submitRegistration(UserModel user) {
        DataValidators validators = new UserValidatorImpl();
        if (!validators.isUserDataValid(user)) {
            LOGGER.warn(ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<String>(
                    ControllerUtils.IS_NOT_VALID_PARAMS, HttpStatus.OK);
        }
        UserDaoModel userDaoModel = serviceUsers.read(user.getLogin(), user.getEmail());
        if (userDaoModel == null) {
            CreateUser createUser = new CreateUser.UserBuilder()
                    .init(new PasswordsImpl(), new TokenGeneratorImpl())
                    .createParams(user).createUser().build();
            return createUser(createUser.getDaoModel());
        } else {
         return new ResponseEntity<String >(
                    ControllerUtils.USER_EXIT, HttpStatus.OK);
        }
    }

    private ResponseEntity<String> createUser(UserDaoModel daoModel) {
        long result = serviceUsers.create(daoModel);
        if (result == 0) {
            return new ResponseEntity<String >(
                    ControllerUtils.USER_REGISTRATION_FAILURE,
                    HttpStatus.OK);
        } else {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTasks(daoModel.getToken(), new ArrayList<>());
            return new ResponseEntity<String >(daoModel.getToken(), HttpStatus.OK);
        }
    }

}
