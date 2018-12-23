package com.client.api.controller;

import com.client.api.delegat.RegistrationDelegate;
import com.helper.controller.model.registration.RegistrationModel;
import com.helper.controller.model.user.UserModel;
import com.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/registration", method = {GET, POST})
    public ResponseEntity<RegistrationModel> registration(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        RegistrationModel outModel;
        if (!RegistrationDelegate.isParams(user, email, password)) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS, RegistrationController.class);
            outModel = new RegistrationModel(counter.incrementAndGet(),
                    ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<>(outModel, HttpStatus.NO_CONTENT);
        }
        UserModel userModel = new UserModel(user, email, password);
        RegistrationDelegate delegate = new RegistrationDelegate();
        if (!delegate.setUser(userModel)) {
            logger.warn(ControllerUtils.USER_EXIT, RegistrationController.class);
            outModel = new RegistrationModel(counter.incrementAndGet(),
                    ControllerUtils.USER_EXIT);
            return new ResponseEntity<>(outModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        logger.info(ControllerUtils.USER_REGISTRATION_SUCCESS, RegistrationController.class);
        outModel = new RegistrationModel(counter.incrementAndGet(),
                ControllerUtils.USER_REGISTRATION_SUCCESS);
        return new ResponseEntity<>(outModel, HttpStatus.OK);
    }

}
