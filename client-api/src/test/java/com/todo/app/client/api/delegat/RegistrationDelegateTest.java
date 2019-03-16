package com.todo.app.client.api.delegat;

import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.controller.constant.ControllerUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IServiceUsers.class, RegistrationDelegate.class})
public class RegistrationDelegateTest {

    @Mock
    private IServiceUsers serviceUsersMock;

    /*
        This test case testing generate user token and push user data in db.
     */
    @Test
    public void submitRegistrationTest() {
        final UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        final RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        when(serviceUsersMock.create(anyObject())).thenReturn(1l);
        final ResponseModel<String> actual = delegate.submitRegistration(model);
        assertThat("This is actual test", actual, Matchers.notNullValue());
    }

    /*
        This test case testing form user exist status.
     */
    @Test
    public void submitRegistrationUserExistTest() {
        final UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        final RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        final UserDaoModel userDaoModel = new UserDaoModel();
        userDaoModel.setLogin("vasia");
        userDaoModel.setEmail("vasia@gmail.ru");
        userDaoModel.setHash(new byte[]{1, 2, 3});
        userDaoModel.setSalt(new byte[]{4, 5, 6});
        userDaoModel.setToken("token_for_test");
        userDaoModel.setEnable(true);
        when(serviceUsersMock.read("vasia", "john@somewhere.com"))
                .thenReturn(userDaoModel);
        final ResponseModel<String> expected = new ResponseModel<>(1, ControllerUtils.USER_EXIT);
        final ResponseModel<String> actual = delegate.submitRegistration(model);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
        This test case testing form user registration failure.
     */
    @Test
    public void submitRegistrationFailureTest() {
        final UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        final RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        when(serviceUsersMock.create(anyObject()))
                .thenReturn(0l);
        final ResponseModel<String> expected = new ResponseModel<>(
                1, ControllerUtils.USER_REGISTRATION_FAILURE);
        final ResponseModel<String> actual = delegate.submitRegistration(model);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
        This test case testing form not valid user params.
     */
    @Test
    public void submitRegistrationNotValidTest() {
        final UserModel model = new UserModel(
                "vasia", "@someserver", "aaZZa44@");
        final RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        final ResponseModel<String> expected = new ResponseModel<>(
                1, ControllerUtils.IS_NOT_VALID_PARAMS);
        final ResponseModel<String> actual = delegate.submitRegistration(model);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

}