package com.client.api.controller;

import com.client.api.delegat.TasksDelegate;
import com.google.gson.Gson;
import com.helper.controller.model.ResponseModel;
import com.helper.controller.model.task.Task;
import com.utils.IdGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TasksController {

    @Async
    @RequestMapping(value = "/tasks/{command}", method = {GET, POST})
    public ResponseEntity tasks(@PathVariable String command, @RequestParam(value = "data") String data) {
        if (command == null || command.equals("") ||
                data == null || data.equals("")) {
            return new ResponseEntity(
                    new ResponseModel(IdGenerator.getInstance().getCounter(),
                            "Incorrect data"), HttpStatus.OK);
        }
        Gson gson = new Gson();
        Task task = gson.fromJson(data, Task.class);
        TasksDelegate delegate = new TasksDelegate();
        return delegate.dispatcher(command, task);
    }

}
