package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.generator.id.IdGenerator;
import com.todo.app.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationDelegate registrationDelegate;

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private IdGenerator gen = IdGenerator.getInstance();

    @RequestMapping(value = "/registration", method = {GET, POST})
    public ResponseEntity submitRegistration(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        LOGGER.info(ControllerUtils.RECEIVED_MESSAGE + RegistrationController.class);
        UserModel userModel = new UserModel(login, email, password);
        ResponseEntity result = registrationDelegate.submitRegistration(userModel);
        ResponseModel<ResponseEntity> responseModel =
                new ResponseModel<>(gen.getCounter(), result);
        LOGGER.info(responseModel.toString());
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
