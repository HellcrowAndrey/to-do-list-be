package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.controller.model.ResponseModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private IdGenerator gen = IdGenerator.getInstance();

    @Async
    @RequestMapping(value = "/registration", method = {GET, POST})
    public ResponseEntity registration(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        ResponseModel responseModel;
        if (!RegistrationDelegate.isParams(login, email, password)) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS, RegistrationController.class);
            responseModel = new ResponseModel(
                    gen.getCounter(), ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<>(responseModel, HttpStatus.NO_CONTENT);
        }
        UserModel userModel = new UserModel(login, email, password);
        RegistrationDelegate delegate = new RegistrationDelegate();
        responseModel = new ResponseModel(gen.getCounter(), delegate.userRegistration(userModel));
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}