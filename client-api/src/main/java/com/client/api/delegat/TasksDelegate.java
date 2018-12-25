package com.client.api.delegat;

import com.cache.manager.CacheManager;
import com.dao.service.tasks.IServiceTasks;
import com.dao.service.tasks.impl.ServiceTasksImpl;
import com.helper.controller.model.ResponseModel;
import com.helper.controller.model.task.Task;
import com.utils.ControllerUtils;
import com.utils.IdGenerator;
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
        IServiceTasks serviceTasks = new ServiceTasksImpl();
        int id = serviceTasks.create(data);
        if (id > 0) {
            CacheManager cacheManager = CacheManager.getInstance();
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

    private ResponseEntity update(Task data) {
        if (data == null) {
            logger.error("Incorrect data in method update.",
                    TasksDelegate.class);
            return new ResponseEntity(
                    new ResponseModel(gen.getCounter(), "Incorrect data"),
                    HttpStatus.OK);
        }
        IServiceTasks serviceTasks = new ServiceTasksImpl();
        int id = serviceTasks.update(data);
        if (id > 0) {
            CacheManager cacheManager = CacheManager.getInstance();
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

    private ResponseEntity delete(Task data) {
        if (data == null) {
            logger.error("Incorrect data in method delete.",
                    TasksDelegate.class);
            return new ResponseEntity(
                    new ResponseModel(gen.getCounter(),
                            "Incorrect data"), HttpStatus.OK);
        }
        IServiceTasks serviceTasks = new ServiceTasksImpl();
        int id = serviceTasks.delete(data);
        if (id > 0) {
            CacheManager cacheManager = CacheManager.getInstance();
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

}
