package com.todo.app.client.api.delegat;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.utils.ControllerUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IServiceUsers.class, RegistrationDelegate.class})
public class RegistrationDelegateTest {

    @Mock
    private IServiceUsers serviceUsersMock;

    @Test
    public void submitRegistrationTest() {
        UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        when(serviceUsersMock.create(anyObject())).thenReturn(1l);
        ResponseEntity<String> actual = delegate.submitRegistration(model);
        assertThat("This is actual test", actual, Matchers.notNullValue());
    }

    @Test
    public void submitRegistrationUserExistTest() {
        UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        when(serviceUsersMock.read("vasia", "john@somewhere.com"))
                .thenReturn(new UserDaoModel());
        ResponseEntity<String> expected = new ResponseEntity<String>(
                ControllerUtils.USER_EXIT, HttpStatus.OK);
        ResponseEntity<String> actual = delegate.submitRegistration(model);
        assertEquals(expected, actual);
    }

    @Test
    public void submitRegistrationFailureTest() {
        UserModel model = new UserModel(
                "vasia", "john@somewhere.com", "aaZZa44@");
        RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        when(serviceUsersMock.create(anyObject()))
                .thenReturn(0l);
        ResponseEntity<String> expected = new ResponseEntity<String >(
                ControllerUtils.USER_REGISTRATION_FAILURE,
                HttpStatus.OK);
        ResponseEntity<String> actual = delegate.submitRegistration(model);
        assertEquals(expected, actual);
    }

    @Test
    public void submitRegistrationNotValidTest() {
        UserModel model = new UserModel(
                "vasia", "@someserver", "aaZZa44@");
        RegistrationDelegate delegate = new RegistrationDelegate(serviceUsersMock);
        ResponseEntity<String> expected = new ResponseEntity<String>(
                ControllerUtils.IS_NOT_VALID_PARAMS, HttpStatus.OK);
        ResponseEntity<String> actual = delegate.submitRegistration(model);
        assertEquals(expected, actual);
    }

}