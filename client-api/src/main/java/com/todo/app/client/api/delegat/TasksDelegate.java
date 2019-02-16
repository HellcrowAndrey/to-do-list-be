package com.todo.app.client.api.delegat;

import com.google.gson.Gson;
import com.todo.app.cache.manager.CacheManager;
import com.todo.app.generator.id.IdGenerator;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.todo.app.utils.ControllerUtils.INCORRECT_TOKEN;

@Component
public class TasksDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksDelegate.class);

    private static final IdGenerator GENERATOR = IdGenerator.getInstance();

    private IServiceTasks serviceTasks;

    public TasksDelegate(IServiceTasks serviceTasks) {
        this.serviceTasks = serviceTasks;
    }

    public ResponseModel<String> submitTasks(final String token) {
        if (token == null || token.equals("")) {
            LOGGER.error("Invalid user token!!!");
            return new ResponseModel<>(GENERATOR.getCounter(), INCORRECT_TOKEN);
        }
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> result = cacheManager.fetchTasks(token);
        final Gson gson = new Gson();
        if (result == null || result.isEmpty()) {
            final List<TaskModel> tmp = serviceTasks.read(token);
            cacheManager.addTasks(token, tmp);
            LOGGER.info("Get tasks in db and send to user!");
            return new ResponseModel<>(GENERATOR.getCounter(),
                    gson.toJson(tmp));
        }
        LOGGER.info("Get tasks in cache and send to user!");
        return new ResponseModel<>(GENERATOR.getCounter(),
                gson.toJson(result));
    }

}
