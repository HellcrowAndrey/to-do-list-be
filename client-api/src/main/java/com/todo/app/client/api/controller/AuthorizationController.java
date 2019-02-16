package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.AuthorizationDelegate;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
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

import java.util.List;

import static com.todo.app.utils.ControllerUtils.RECEIVED_MESSAGE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * The AuthorizationController class start with app and wait
 * request with params (login, password or email, password).
 * This controller do authorization user in this app.
 * This class has autowired fields authorizationDelegate and
 * field LOGGER. Request mapping for this controller is
 * 'authorization'.
 */
@RestController
public class AuthorizationController {

    /**
     * This field is logger this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    /**
     * This field is link on AuthorizationDelegate.
     */
    @Autowired
    private AuthorizationDelegate authorizationDelegate;

    /**
     * This is authorization controller. Has get and post methods.
     * After received request do call to submitAuth and generate
     * response with status authorization.
     *
     * @param login    this is user login
     * @param email    this is user email
     * @param password this is user password
     * @return response on user request.
     */
    @RequestMapping(value = "/authorization", method = {GET, POST})
    public ResponseEntity<ResponseModel> isAuthorization(
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "password") String password) {
        LOGGER.info(RECEIVED_MESSAGE + AuthorizationController.class);
        final UserModel user = new UserModel(login, email, password);
        final ResponseModel<String> result = authorizationDelegate.submitAuth(user);
        LOGGER.info(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
