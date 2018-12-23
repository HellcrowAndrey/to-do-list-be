package com.client.api.controller;

import com.client.api.delegat.AuthorizationDelegate;
import com.helper.controller.model.user.UserModel;
import com.helper.controller.model.auth.AuthorizationModel;
import com.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthorizationController {

    private Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/authorization", method = {GET, POST})
    @Async
    public ResponseEntity<AuthorizationModel> isAuthorization(
            @RequestParam(value = "user", defaultValue = "") String user,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "password") String password) {
        AuthorizationModel outModel;
        if (!AuthorizationDelegate.isParams(user, email, password)) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS, AuthorizationController.class);
            outModel = new AuthorizationModel(counter.incrementAndGet(), false);
            return new ResponseEntity<>(outModel, HttpStatus.NO_CONTENT);
        }
        UserModel model = new UserModel(user, email, password);
        AuthorizationDelegate delegate = new AuthorizationDelegate();
        if (!delegate.getData(model)) {
            logger.warn(ControllerUtils.USER_NOT_FOUNT, AuthorizationController.class);
            outModel = new AuthorizationModel(counter.incrementAndGet(), false);
            return new ResponseEntity<>(outModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        logger.info(ControllerUtils.USER_AUTHORIZATION_SUCCESS, AuthorizationController.class);
        outModel = new AuthorizationModel(counter.incrementAndGet(), true);
        return new ResponseEntity<>(outModel, HttpStatus.OK);
    }

}
