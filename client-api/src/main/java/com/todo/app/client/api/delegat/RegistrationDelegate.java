package com.todo.app.client.api.delegat;

import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.generator.id.IdGenerator;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.token.impl.TokenGeneratorImpl;
import com.todo.app.user.CreateUser;
import com.todo.app.controller.constant.ControllerUtils;
import com.todo.app.validators.DataValidators;
import com.todo.app.validators.impl.UserValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.todo.app.controller.constant.ControllerUtils.*;

/**
 * The RegistrationDelegate class use for user registration. Has two
 * methods submitRegistration and createUser. Has next fields LOGGER,
 * id gen and serviceUser.
 * Method submitRegistration received UserModel and do validation user
 * data if user data doesn't valid do return message with info about
 * 'not valid params' else do read user in db if user exist in db create
 * message with info about user exist else crate user.
 * Method createUser use to assistant for method submitRegistration and
 * depend on result in method create, can generate two message about
 * registration failure or success.
 */
@Component
public class RegistrationDelegate {

    /**
     * This field is logger this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationDelegate.class);

    /**
     * This field is singleton id generator.
     */
    private static final IdGenerator GENERATOR = IdGenerator.getInstance();

    /**
     * This field keeps link on IServiceUser.
     */
    private IServiceUsers serviceUsers;

    /**
     * This is constructor class RegistrationDelegate with link on
     * IServiceUsers interface.
     *
     * @param serviceUsers exemplar interface IServiceUsers
     */
    public RegistrationDelegate(IServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    /**
     * Method submitRegistration received UserModel and do validation user
     * data if user data doesn't valid do return message with info about
     * 'not valid params' else do read user in db if user exist in db create
     * message with info about user exist else crate user.
     *
     * @param user obj with user info
     * @return message with info about registration
     */
    public ResponseModel<String> submitRegistration(UserModel user) {
        final DataValidators validators = new UserValidatorImpl();
        if (!validators.isUserDataValid(user)) {
            LOGGER.warn(IS_NOT_VALID_PARAMS);
            return new ResponseModel<>(GENERATOR.getCounter(),
                    IS_NOT_VALID_PARAMS);
        }
        final UserDaoModel userDaoModel = serviceUsers.read(user.getLogin(), user.getEmail());
        if (userDaoModel == null || userDaoModel.isEmpty()) {
            LOGGER.info(CRETE_USER);
            final CreateUser createUser = new CreateUser.UserBuilder()
                    .init(new PasswordsImpl(), new TokenGeneratorImpl())
                    .createParams(user).createUser().build();
            return createUser(createUser.getDaoModel());
        } else {
            LOGGER.info(USER_EXIT);
            return new ResponseModel<>(GENERATOR.getCounter(),
                    USER_EXIT);
        }
    }

    /**
     * Method createUser use to assistant for method submitRegistration and
     * depend on result in method create, can generate two message about
     * registration failure or success.
     *
     * @param daoModel obj with user info
     * @return message with info about registration
     */
    private ResponseModel<String> createUser(UserDaoModel daoModel) {
        final long result = serviceUsers.create(daoModel);
        if (result == 0) {
            LOGGER.warn(ControllerUtils.USER_REGISTRATION_FAILURE);
            return new ResponseModel<>(GENERATOR.getCounter(),
                    USER_REGISTRATION_FAILURE);
        } else {
            final CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTasks(daoModel.getToken(), new ArrayList<>());
            LOGGER.info(USER_REGISTRATION_SUCCESS);
            return new ResponseModel<>(GENERATOR.getCounter(), daoModel.getToken());
        }
    }

}
