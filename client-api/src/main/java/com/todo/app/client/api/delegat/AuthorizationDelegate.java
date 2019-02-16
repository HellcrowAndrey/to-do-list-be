package com.todo.app.client.api.delegat;

import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.generator.id.IdGenerator;
import com.todo.app.password.IPasswords;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.validators.DataValidators;
import com.todo.app.validators.impl.UserValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.todo.app.constant.ToDoConstant.MOCK_CONSTANT;
import static com.todo.app.utils.ControllerUtils.*;

/**
 * The AuthorizationDelegate class user for authorization.
 * Has for method submitAuth, userAuth, matchesPassword,
 * doStuffCache. Method submitAuth received UserModel and
 * do call to private method userAuth. Method userAuth do
 * validate user data and call to method matchesPassword
 * this method do check password if password invalid do
 * form message to user about it else add user token and
 * tasks to cache.
 */
@Component
public class AuthorizationDelegate {

    /**
     * This field is logger this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationDelegate.class);

    /**
     * This field is singleton id generator.
     */
    private static final IdGenerator GENERATOR = IdGenerator.getInstance();

    /**
     * This field keeps link on IServiceUser.
     */
    private IServiceUsers serviceUsers;

    /**
     * This field keeps link on IServiceTasks.
     */
    private IServiceTasks serviceTasks;

    /**
     * This is constructor class AuthorizationDelegate with link on
     * IServiceUsers, IServiceTasks.
     *
     * @param serviceUsers exemplar interface IServiceUsers
     * @param serviceTasks exemplar interface IServiceTasks
     */
    public AuthorizationDelegate(IServiceUsers serviceUsers,
                                 IServiceTasks serviceTasks) {
        this.serviceUsers = serviceUsers;
        this.serviceTasks = serviceTasks;
    }

    /**
     * Method submitAuth received UserModel and do call
     * to userAuth method.
     *
     * @param user user data received in controller
     * @return response message to user
     */
    public ResponseModel<String> submitAuth(UserModel user) {
        return userAuth(user);
    }

    /**
     * Method userAuth received UserModel and do create exemplar
     * interface DataValidator after that do validate user data.
     * Has two validation case:
     * - first case if user send email and password;
     * - second case if user send login and password.
     * In this case form response on user request. If these cases
     * didn't work do create response about incorrect data.
     *
     * @param user obj with user data
     * @return response message to user
     */
    private ResponseModel<String> userAuth(UserModel user) {
        final DataValidators validators = new UserValidatorImpl();
        if (validators.isValidEmailAddress(user.getEmail())
                && validators.isValidPassword(user.getPassword())) {
            final UserDaoModel result = serviceUsers.read(MOCK_CONSTANT,
                    user.getEmail());
            return result.isEmpty() ? new ResponseModel<>(
                    GENERATOR.getCounter(), USER_NOT_FOUNT) :
                    matchesPassword(user.getPassword(), result);
        }
        if (validators.isValidLogin(user.getLogin())
                && validators.isValidPassword(user.getPassword())) {
            UserDaoModel result = serviceUsers.read(user.getLogin(),
                    MOCK_CONSTANT);
            return result.isEmpty() ? new ResponseModel<>(
                    GENERATOR.getCounter(), USER_NOT_FOUNT) :
                    matchesPassword(user.getPassword(), result);
        }
        LOGGER.info("User data is doesn't valid!");
        return new ResponseModel<>(
                GENERATOR.getCounter(), INCORRECT_DATA);
    }

    /**
     * Method matchesPassword received user password and UserDaoModel.
     * After that do create exemplar interface IPasswords and do check
     * is user has this password or not. If user has this password call
     * to doStuffCache method else form message about 'User registration
     * failure'.
     *
     * @param password user password
     * @param result   user data model in db
     * @return response message to user
     */
    private ResponseModel<String> matchesPassword(final String password, final UserDaoModel result) {
        final IPasswords passwords = new PasswordsImpl();
        final boolean tmp = passwords.isExpectedPassword(password,
                result.getSalt(), result.getHash());
        if (tmp) {
            return doStuffCache(result.getToken());
        } else {
            LOGGER.info("User auth failure, password doesn't valid.");
            return new ResponseModel<>(
                    GENERATOR.getCounter(), USER_AUTHORIZATION_FAILURE);
        }
    }

    /**
     * Method doStuffCache received user token after that find user tasks in
     * cache if find do form message about 'user auth success' else read user
     * task in db and add to cache after that form message about
     * 'user auth success'.
     *
     * @param token user token
     * @return response message to use
     */
    private ResponseModel<String> doStuffCache(final String token) {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasksInCache = cacheManager.fetchTasks(token);
        if (tasksInCache == null || tasksInCache.isEmpty()) {
            final List<TaskModel> tasksInDb = serviceTasks.read(token);
            if (tasksInDb != null && !tasksInDb.isEmpty()) {
                cacheManager.addTasks(token, tasksInDb);
                LOGGER.info("User auth success, and get cache.");
                return new ResponseModel<>(GENERATOR.getCounter(),
                        USER_AUTHORIZATION_SUCCESS);
            }
        }
        LOGGER.info("User auth success. Cache exist.");
        return new ResponseModel<>(GENERATOR.getCounter(),
                USER_AUTHORIZATION_SUCCESS);
    }

}
