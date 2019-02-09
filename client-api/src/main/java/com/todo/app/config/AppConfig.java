package com.todo.app.config;

import com.todo.app.client.api.delegat.AuthorizationDelegate;
import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.client.api.delegat.TasksDelegate;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.service.users.IServiceUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class AppConfig {

    @Autowired
    @Qualifier(value = "usersService")
    private IServiceUsers serviceUsers;

    @Autowired
    @Qualifier(value = "tasksService")
    private IServiceTasks serviceTasks;

    @Bean
    public AuthorizationDelegate authService() {
        return new AuthorizationDelegate(serviceUsers, serviceTasks);
    }

    @Bean
    public RegistrationDelegate registrationService() {
        return new RegistrationDelegate(serviceUsers);
    }

    @Bean
    public TasksDelegate tasksService() {
        return new TasksDelegate(serviceTasks);
    }

}
