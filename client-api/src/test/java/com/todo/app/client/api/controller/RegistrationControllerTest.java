package com.todo.app.client.api.controller;

import com.todo.app.client.api.delegat.RegistrationDelegate;
import com.todo.app.utils.ControllerUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @InjectMocks
    private RegistrationController registrationController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(registrationController)
                .build();
    }

    @Test
    public void submitRegistrationTokenTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseEntity<String >("1234123ewqqwDSAd123d", HttpStatus.OK));
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

    @Test
    public void submitRegistrationExistTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(
                new ResponseEntity<>(
                        ControllerUtils.USER_EXIT, HttpStatus.OK));
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

    @Test
    public void submitRegistrationFailureTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(new ResponseEntity<>(
                ControllerUtils.USER_REGISTRATION_FAILURE, HttpStatus.OK));
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

    @Test
    public void submitRegistrationNotValidParamsTest() throws Exception {
        when(registrationDelegate.submitRegistration(anyObject())).thenReturn(new ResponseEntity<>(
                ControllerUtils.IS_NOT_VALID_PARAMS, HttpStatus.OK));
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