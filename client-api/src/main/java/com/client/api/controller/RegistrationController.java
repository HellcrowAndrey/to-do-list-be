package com.client.api.controller;

import com.client.api.delegat.RegistrationDelegate;
import com.helper.controller.model.ResponseModel;
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
        if (!delegate.setUser(userModel)) {
            logger.warn(ControllerUtils.USER_EXIT, RegistrationController.class);
            responseModel = new ResponseModel(gen.getCounter(), ControllerUtils.USER_EXIT);
            return new ResponseEntity<>(responseModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        logger.info(ControllerUtils.USER_REGISTRATION_SUCCESS, RegistrationController.class);
        responseModel = new ResponseModel(gen.getCounter(), ControllerUtils.USER_REGISTRATION_SUCCESS);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
