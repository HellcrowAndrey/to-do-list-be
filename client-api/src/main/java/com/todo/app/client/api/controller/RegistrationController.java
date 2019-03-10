package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.todo.app.controller.constant.ControllerUtils.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * The RegistrationController class start with app and wait
 * request with params (login, email and password).
 * This controller do registration user in this app.
 * This class has autowired fields registrationDelegate, LOGGER.
 * RequestMapping for this controller is 'registration'.
 */
@RestController
public class RegistrationController {

    /**
     * This field is logger this class.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RegistrationController.class);

    /**
     * This field is link on RegistrationDelegate.
     */
    @Autowired
    private RegistrationDelegate registrationDelegate;

    /**
     * This is registration controller. Has get and post methods.
     * After received request do call to submitRegistration and
     * generate response with status registration.
     *
     * @param login    this is user login
     * @param email    this is user email
     * @param password this is user password
     * @return response on user request
     */
    @RequestMapping(value = REGISTRATION, method = {GET, POST})
    public ResponseEntity submitRegistration(
            @RequestParam(value = LOGIN) String login,
            @RequestParam(value = EMAIL) String email,
            @RequestParam(value = PASSWORD) String password) {
        LOGGER.info(RECEIVED_MESSAGE + RegistrationController.class);
        final UserModel userModel = new UserModel(login, email, password);
        final ResponseModel<String> result = registrationDelegate
                .submitRegistration(userModel);
        LOGGER.info(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
