package com.client.api.controller;

import com.client.api.delegat.AuthorizationDelegate;
import com.helper.controller.model.ResponseModel;
import com.helper.controller.model.task.Task;
import com.helper.controller.model.user.UserModel;
import com.utils.ControllerUtils;
import com.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthorizationController {

    private IdGenerator gen = IdGenerator.getInstance();

    private Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Async
    @RequestMapping(value = "/authorization", method = {GET, POST})
    public ResponseEntity<ResponseModel> isAuthorization(
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "password") String password) {
        ResponseModel responseModel;
        if (!AuthorizationDelegate.isParams(login, email, password)) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS, AuthorizationController.class);
            responseModel = new ResponseModel(gen.getCounter(), ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<>(responseModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        UserModel model = new UserModel(login, email, password);
        AuthorizationDelegate delegate = new AuthorizationDelegate();
        List<Task> response = delegate.getData(model);
        if (response == null) {
            logger.warn(ControllerUtils.USER_NOT_FOUNT, AuthorizationController.class);
            responseModel = new ResponseModel(gen.getCounter(), ControllerUtils.USER_NOT_FOUNT);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
        logger.info(ControllerUtils.USER_AUTHORIZATION_SUCCESS, AuthorizationController.class);
        responseModel = new ResponseModel(gen.getCounter(), response);
        return new ResponseEntity<>(responseModel, HttpStatus.ACCEPTED);
    }

}
