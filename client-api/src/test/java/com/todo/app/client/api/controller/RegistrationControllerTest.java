package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.constant.ControllerUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationDelegate registrationDelegate;

    /*
    This is registration controller.
     */
    @InjectMocks
    private RegistrationController registrationController;

    /*
    This method do init mocks and mockMvc.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(registrationController)
                .build();
    }

    /*
    This test case create testing response with user token.
     */
    @Test
    public void submitRegistrationTokenTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseModel<>(1, "321312DSADwqqwdqw"));
        ResultActions result = mockMvc.perform(post("/registration")
                .param("login", "vasia")
                .param("email", "vasia@ya.ru")
                .param("password", "32143214"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(registrationDelegate, times(1)).submitRegistration(anyObject());
        verifyNoMoreInteractions(registrationDelegate);
    }

    /*
    This test case testing create response with user exit message.
     */
    @Test
    public void submitRegistrationExistTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseModel<>(1,
                        ControllerUtils.USER_EXIT));
        ResultActions result = mockMvc.perform(post("/registration")
                .param("login", "vasia")
                .param("email", "vasia@ya.ru")
                .param("password", "32143214"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(registrationDelegate, times(1)).submitRegistration(anyObject());
        verifyNoMoreInteractions(registrationDelegate);
    }

    /*
    This test case testing create response with user registration failure.
     */
    @Test
    public void submitRegistrationFailureTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseModel<>(1,
                ControllerUtils.USER_REGISTRATION_FAILURE));
        ResultActions result = mockMvc.perform(post("/registration")
                .param("login", "vasia")
                .param("email", "vasia@ya.ru")
                .param("password", "32143214"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(registrationDelegate, times(1)).submitRegistration(anyObject());
        verifyNoMoreInteractions(registrationDelegate);
    }

    /*
    This test case testing create response with not valid params.
     */
    @Test
    public void submitRegistrationNotValidParamsTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseModel<>(1,
                        ControllerUtils.IS_NOT_VALID_PARAMS));
        ResultActions result = mockMvc.perform(post("/registration")
                .param("login", "vasia")
                .param("email", "vasia@ya.ru")
                .param("password", "32143214"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(registrationDelegate, times(1)).submitRegistration(anyObject());
        verifyNoMoreInteractions(registrationDelegate);
    }

}