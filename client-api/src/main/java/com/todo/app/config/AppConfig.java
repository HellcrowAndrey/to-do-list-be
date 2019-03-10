package com.todo.app.config;

import com.todo.app.client.api.delegat.AuthorizationDelegate;
import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.client.api.delegat.TasksDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoServiceConfig.class)
public class AppConfig {

    @Autowired
    private DaoServiceConfig daoServiceConfig;

    @Bean
    public AuthorizationDelegate authService() {
        return new AuthorizationDelegate(
                daoServiceConfig.usersService(),
                daoServiceConfig.tasksService());
    }

    @Bean
    public RegistrationDelegate registrationService() {
        return new RegistrationDelegate(daoServiceConfig.usersService());
    }

    @Bean
    public TasksDelegate tasksService() {
        return new TasksDelegate(daoServiceConfig.tasksService());
    }

}
