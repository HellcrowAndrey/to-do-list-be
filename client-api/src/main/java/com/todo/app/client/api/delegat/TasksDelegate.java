package com.todo.app.client.api.delegat;

import com.google.gson.Gson;
import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.task.TaskUpdateModel;
import com.todo.app.dao.model.TaskDaoModel;
import com.todo.app.generator.id.IdGenerator;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.todo.app.controller.constant.ControllerUtils.*;

@Component
public class TasksDelegate {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TasksDelegate.class);

    private static final IdGenerator GENERATOR = IdGenerator
            .getInstance();

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

    public ResponseModel<String> dispatcher(final TaskUpdateModel task) {
        if (task == null || task.isTaskUpdateModel()) {
            return new ResponseModel<>(
                    GENERATOR.getCounter(), IS_NOT_VALID_PARAMS);
        }
        ResponseModel<String> result = restartAuth(task.getToken());
        if (result == null) {
            switch (task.getCommand()) {
                case CREATE:
                    return submitCreate(task);
                case UPDATE:
                    return submitUpdate(task);
                case DELETE:
                    return submitDelete(task);
                default:
                    return new ResponseModel<>(
                            GENERATOR.getCounter(), IS_NOT_VALID_PARAMS);
            }
        } else {
            return result;
        }
    }

    private final ResponseModel<String> restartAuth(final String token) {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> result = cacheManager.fetchTasks(token);
        if (result == null) {
            return new ResponseModel<>(GENERATOR.getCounter(), RESTART_ACCOUNT);
        } else {
            return null;
        }
    }

    private final ResponseModel<String> submitCreate(final TaskUpdateModel task) {
        final TaskModel taskModel = jsonParser(task.getData());
        if (taskModel != null) {
            final CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTask(task.getToken(), taskModel);
            final TaskDaoModel taskDaoModel = new TaskDaoModel();
            taskDaoModel.setToken(task.getToken());
            taskDaoModel.setTaskModel(taskModel);
            final long taskId = serviceTasks.create(taskDaoModel);
            return new ResponseModel<>(GENERATOR.getCounter(), String.valueOf(taskId));
        } else {
            return new ResponseModel<>(GENERATOR.getCounter(), IS_NOT_VALID_PARAMS);
        }
    }

    private final ResponseModel<String> submitUpdate(final TaskUpdateModel task) {
        final TaskModel taskModel = jsonParser(task.getData());
        if (taskModel != null) {
            final CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.updateTask(task.getToken(), taskModel);
            final TaskDaoModel taskDaoModel = new TaskDaoModel();
            taskDaoModel.setToken(task.getToken());
            taskDaoModel.setTaskModel(taskModel);
            final long taskId = serviceTasks.update(taskDaoModel);
            return new ResponseModel<>(GENERATOR.getCounter(), String.valueOf(taskId));
        } else {
            return new ResponseModel<>(GENERATOR.getCounter(), IS_NOT_VALID_PARAMS);
        }
    }

    private final ResponseModel<String> submitDelete(final TaskUpdateModel task) {
        final Gson gson = new Gson();
        long id = 0;
        try {
            id = gson.fromJson(task.getData(), Long.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (id > 0) {
            final CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.removeTask(task.getToken(), id);
            final long taskId = serviceTasks.delete(id);
            return new ResponseModel<>(GENERATOR.getCounter(), String.valueOf(taskId));
        } else {
            return new ResponseModel<>(GENERATOR.getCounter(), IS_NOT_VALID_PARAMS);
        }
    }

    private final TaskModel jsonParser(final String data) {
        final Gson gson = new Gson();
        TaskModel taskModel = null;
        try {
            taskModel = gson.fromJson(data, TaskModel.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return taskModel;
    }

}
