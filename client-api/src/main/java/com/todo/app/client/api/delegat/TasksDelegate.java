package com.todo.app.client.api.delegat;

import com.todo.app.utils.IdGenerator;
import com.todo.app.cache.manager.CacheManager;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.service.tasks.impl.ServiceTasksImpl;
import com.todo.app.controller.model.ResponseModel;
import com.todo.app.controller.model.task.Task;
import com.todo.app.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TasksDelegate {

    private IdGenerator gen = IdGenerator.getInstance();

    private Logger logger = LoggerFactory.getLogger(TasksDelegate.class);

    public ResponseEntity dispatcher(String command, Task data) {
        ResponseEntity entity;
        switch (command) {
            case ControllerUtils.CREATE:
                entity = create(data);
                logger.info(ControllerUtils.CREATE, TasksDelegate.class);
                break;
            case ControllerUtils.UPDATE:
                entity = update(data);
                logger.info(ControllerUtils.UPDATE, TasksDelegate.class);
                break;
            case ControllerUtils.DELETE:
                entity = delete(data);
                logger.info(ControllerUtils.DELETE, TasksDelegate.class);
                break;
            default:
                entity = new ResponseEntity(new ResponseModel(gen.getCounter(),
                        "Command doesn't exist"), HttpStatus.OK);
                logger.info("Default case.", TasksDelegate.class);
                break;
        }
        return entity;
    }

    private ResponseEntity create(Task data) {
        if (data == null) {
            logger.error("Incorrect data in method create.",
                    TasksDelegate.class);
            return new ResponseEntity(
                    new ResponseModel(gen.getCounter(), "Incorrect data"),
                    HttpStatus.OK);
        }
        CacheManager cacheManager = CacheManager.getInstance();
        if (!repeatAuth(data.getLogin(), cacheManager)) {
            IServiceTasks serviceTasks = new ServiceTasksImpl();
            int id = serviceTasks.create(data);
            if (id > 0) {
                cacheManager.addTask(data);
                logger.info("Create task " + id, TasksDelegate.class);
                return new ResponseEntity(
                        new ResponseModel(gen.getCounter(), id), HttpStatus.OK);
            } else {
                logger.warn("Doesn't create task.", TasksDelegate.class);
                return new ResponseEntity(
                        new ResponseModel(gen.getCounter(), "Task doesn't create"),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity(new ResponseModel(gen.getCounter(),
                "You mast repeat authorization"), HttpStatus.OK);
    }

    private ResponseEntity update(Task data) {
        if (data == null) {
            logger.error("Incorrect data in method update.",
                    TasksDelegate.class);
            return new ResponseEntity(
                    new ResponseModel(gen.getCounter(), "Incorrect data"),
                    HttpStatus.OK);
        }
        CacheManager cacheManager = CacheManager.getInstance();
        if (!repeatAuth(data.getLogin(), cacheManager)) {
            IServiceTasks serviceTasks = new ServiceTasksImpl();
            int id = serviceTasks.update(data);
            if (id > 0) {
                cacheManager.updateTask(data);
                logger.info("Update task " + id, TasksDelegate.class);
                return new ResponseEntity(
                        new ResponseModel(gen.getCounter(), id),
                        HttpStatus.OK);
            } else {
                logger.warn("Doesn't update task.", TasksDelegate.class);
                return new ResponseEntity(new ResponseModel(gen.getCounter(),
                        "Task doesn't delete"), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new ResponseModel(gen.getCounter(),
                "You mast repeat authorization"), HttpStatus.OK);
    }

    private ResponseEntity delete(Task data) {
        if (data == null) {
            logger.error("Incorrect data in method delete.",
                    TasksDelegate.class);
            return new ResponseEntity(
                    new ResponseModel(gen.getCounter(),
                            "Incorrect data"), HttpStatus.OK);
        }
        CacheManager cacheManager = CacheManager.getInstance();
        if (!repeatAuth(data.getLogin(), cacheManager)) {
            IServiceTasks serviceTasks = new ServiceTasksImpl();
            int id = serviceTasks.delete(data);
            if (id > 0) {
                cacheManager.deleteTask(data);
                logger.info("Delete task " + id, TasksDelegate.class);
                return new ResponseEntity(
                        new ResponseModel(gen.getCounter(),
                                id), HttpStatus.OK);
            } else {
                logger.warn("Doesn't delete task.", TasksDelegate.class);
                return new ResponseEntity(
                        new ResponseModel(gen.getCounter(),
                                "Task doesn't create"), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new ResponseModel(gen.getCounter(),
                "You mast repeat authorization"), HttpStatus.OK);
    }

    private boolean repeatAuth(String login, CacheManager cacheManager) {
        if (cacheManager.fetchToken(login) == null) {
            return true;
        }
        return false;
    }

}
