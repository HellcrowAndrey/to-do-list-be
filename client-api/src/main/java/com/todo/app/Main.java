package com.todo.app;

import com.todo.app.service.users.IServiceUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {

//    @Autowired
//    IServiceUsers users;

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start client api.");
        SpringApplication.run(Main.class, args);
    }

//    @PostConstruct
//    public void res() {
//        users.read("", "");
//    }

}
