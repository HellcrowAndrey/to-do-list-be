package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.controller.model.ResponseModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RegistrationDelegate delegate;

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private IdGenerator gen = IdGenerator.getInstance();

    @Async
    @RequestMapping(value = "/registration", method = {GET, POST})
    public ResponseEntity submitRegistration(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        ResponseEntity valid = RegistrationDelegate.isParams(login, email, password);
        if (valid != null) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS);
            return valid;
        }
        UserModel userModel = new UserModel(login, email, password);
        ResponseModel responseModel = new ResponseModel(gen.getCounter(),
                delegate.submitRegistration(userModel));
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
