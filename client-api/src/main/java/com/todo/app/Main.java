package com.todo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static Logger logger = LoggerFactory.getLogger(RunApp.class);

    public static void main(String[] args) {
        logger.info("Start client api.");
        SpringApplication.run(RunApp.class, args);
    }
}
