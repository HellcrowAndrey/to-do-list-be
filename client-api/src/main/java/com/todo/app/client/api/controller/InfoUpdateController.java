package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.InfoUpdateDelegate;
import com.todo.app.generator.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoUpdateController {

    private Logger logger = LoggerFactory.getLogger(InfoUpdateController.class);

    private IdGenerator gen = IdGenerator.getInstance();

    @RequestMapping(value = "/change/password")
    public ResponseEntity changePassword(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        if (!InfoUpdateDelegate.isParams(email, password)) {
            return new ResponseEntity(gen.getCounter(), HttpStatus.OK);
        }
        //todo change dao
        return null;
    }

}
