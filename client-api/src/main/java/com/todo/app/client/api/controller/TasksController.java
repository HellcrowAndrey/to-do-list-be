package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.TasksDelegate;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskUpdateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.todo.app.controller.constant.ControllerUtils.RECEIVED_MESSAGE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TasksController {

    /**
     * This field is logger this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    /**
     * This field is link on TasksDelegate.
     */
    @Autowired
    private TasksDelegate tasksDelegate;

    @RequestMapping(value = "/tasks", method = {GET, POST})
    public ResponseEntity<ResponseModel> tasks(
            @RequestParam(value = "token") String token) {
        LOGGER.info(RECEIVED_MESSAGE + TasksController.class);
        final ResponseModel<String> result = tasksDelegate.submitTasks(token);
        LOGGER.info(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{command}/task")
    public ResponseEntity<ResponseModel> task(
            @PathVariable String command,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "data") String data) {
        TaskUpdateModel task = new TaskUpdateModel(command, token, data);
        ResponseModel<String> result = tasksDelegate.dispatcher(task);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
