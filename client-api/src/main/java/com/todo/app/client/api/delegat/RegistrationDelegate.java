package com.todo.app.client.api.delegat;

import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.validators.DataValidators;
import com.todo.app.validators.impl.UserValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Component
public class RegistrationDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationDelegate.class);

    private IServiceUsers serviceUsers;

    public RegistrationDelegate(
            @Qualifier("serviceUsersImpl") IServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    private ResponseEntity<String> doStaffRegistration(UserModel user) {
        DataValidators validators = new UserValidatorImpl();
        if (!validators.isUserDataValid(user)) {
            return new ResponseEntity(
                    ControllerUtils.IS_NOT_VALID_PARAMS, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @Async
    public CompletableFuture<ResponseEntity> submitRegistration(UserModel user) {

        return CompletableFuture.completedFuture(null);
    }

}
