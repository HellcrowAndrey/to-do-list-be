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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthorizationController {

//    @Autowired
//    private AuthorizationDelegate delegate;

    private IdGenerator gen = IdGenerator.getInstance();

    private Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @RequestMapping(value = "/authorization", method = {GET, POST})
    public ResponseEntity<ResponseModel> isAuthorization(
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "password") String password) {
        ResponseEntity valid = AuthorizationDelegate.isParams(login, email, password);
        if (valid != null) {
            logger.warn(ControllerUtils.IS_NOT_VALID_PARAMS);
            return valid;
        }
        ResponseModel responseModel;
        UserModel model = new UserModel(login, email, password);
        List<TaskModel> response = null; //delegate.getData(model);
        if (response == null) {
            logger.warn(ControllerUtils.USER_NOT_FOUNT);
            responseModel = new ResponseModel(gen.getCounter(), ControllerUtils.USER_NOT_FOUNT);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
        logger.info(ControllerUtils.USER_AUTHORIZATION_SUCCESS);
        responseModel = new ResponseModel(gen.getCounter(), response);
        return new ResponseEntity<>(responseModel, HttpStatus.ACCEPTED);
    }

}
