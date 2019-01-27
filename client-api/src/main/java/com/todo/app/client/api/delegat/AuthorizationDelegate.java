package com.todo.app.client.api.delegat;

import com.todo.app.controller.model.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.decorator.UserDecorator;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.utils.ControllerUtils;
import com.todo.app.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class AuthorizationDelegate {

    private Logger logger = LoggerFactory.getLogger(AuthorizationDelegate.class);

    private IServiceUsers serviceUsers;

    private IServiceTasks serviceTasks;

    public AuthorizationDelegate(@Qualifier("serviceUsersImpl") IServiceUsers serviceUsers,
                                 @Qualifier("serviceTasksImpl") IServiceTasks serviceTasks) {
        this.serviceUsers = serviceUsers;
        this.serviceTasks = serviceTasks;
    }

    public static ResponseEntity isParams(final String login,
                                          final String email,
                                          final String password) {
        return isParams(login == null || login.equals("") ? email : login, password);
    }

    public static ResponseEntity isParams(final String value,
                                          final String password) {
        if (value == null || value.equals("") ||
                password == null || password.equals("")) {
            ResponseModel response =
                    new ResponseModel(IdGenerator.getInstance().getCounter(),
                            ControllerUtils.IS_NOT_VALID_PARAMS);
            return new ResponseEntity<>(response,
                    HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } else {
            return null;
        }
    }

//    public List<TaskModel> getData(UserModel authUser) {
//        if (authUser == null) {
//            logger.warn("User model is not valid.", AuthorizationDelegate.class);
//            return null;
//        }
//        CacheManager manager = CacheManager.getInstance();
//        UserModel userInCache = manager.fetchUser(authUser);
//        if (userInCache != null) {
//            logger.info("User find in cache.", AuthorizationDelegate.class);
//            return manager.fetchTasks(userInCache);
//        }
//        return findData(authUser);
//    }

    private List<TaskModel> findData(UserModel authUser) {
        UserDecorator decorator = new UserDecorator();
        //UserModel userInDb = serviceUsers.read(decorator.createHash(authUser));
//        if (userInDb == null) {
//            logger.warn("User doesn't find in db.", AuthorizationDelegate.class);
//            return null;
//        } else {
//            CacheManager manager = CacheManager.getInstance();
//            logger.info("User find in db and add to cache.", AuthorizationDelegate.class);
//            manager.addUser(userInDb);
//            //List<TaskModel> tasks = serviceTasks.read(userInDb.getLogin());
//            logger.info("Return Tasks by user.", AuthorizationDelegate.class);
//            return null; // manager.addTasks(tasks, userInDb.getLogin());
//        }
        return null;
    }

}
