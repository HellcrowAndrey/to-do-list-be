package com.todo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public abstract class MainClientApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainClientApi.class);

    public static void main(final String[] args) {
        //This method do start spring app
        LOGGER.info("Start client api.");
        SpringApplication.run(MainClientApi.class, args);
    }
}
