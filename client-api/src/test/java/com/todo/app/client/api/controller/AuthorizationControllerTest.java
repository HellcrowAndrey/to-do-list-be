package com.todo.app.client.api.controller;

import com.google.gson.Gson;
import com.todo.app.client.api.delegat.AuthorizationDelegate;
import com.todo.app.controller.model.response.ResponseModel;
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

import static com.todo.app.controller.constant.ControllerUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorizationDelegate authorizationDelegateMock;

    /*
        This is authorization controller.
     */
    @InjectMocks
    private AuthorizationController authorizationController;

    /*
        This method do init mocks and mockMvc.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authorizationController)
                .build();
    }

    /*
        This test case testing response: user auth success.
     */
    @Test
    public void submitAuthUserAuthSuccess() throws Exception {
        when(authorizationDelegateMock.submitAuth(anyObject())).thenReturn(
                new ResponseModel<>(1, USER_AUTHORIZATION_SUCCESS));
        ResultActions result = mockMvc.perform(post("/authorization")
                .param("login", "vasia")
                .param("email", "")
                .param("password", "31242134"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(USER_AUTHORIZATION_SUCCESS, parser.getResponse());
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(authorizationDelegateMock, times(1)).submitAuth(anyObject());
        verifyNoMoreInteractions(authorizationDelegateMock);
    }

    /*
        This test case testing response: user auth failure
     */
    @Test
    public void submitAuthUserAuthFailure() throws Exception {
        when(authorizationDelegateMock.submitAuth(anyObject())).thenReturn(
                new ResponseModel<>(1, USER_AUTHORIZATION_FAILURE));
        ResultActions result = mockMvc.perform(post("/authorization")
                .param("login", "vasia")
                .param("email", "")
                .param("password", "31242134"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(USER_AUTHORIZATION_FAILURE, parser.getResponse());
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(authorizationDelegateMock, times(1)).submitAuth(anyObject());
        verifyNoMoreInteractions(authorizationDelegateMock);
    }

    /*
        This test case test user not found.
     */
    @Test
    public void submitAuthUserAuthUserNotFound() throws Exception {
        when(authorizationDelegateMock.submitAuth(anyObject())).thenReturn(
                new ResponseModel<>(1, USER_NOT_FOUNT));
        ResultActions result = mockMvc.perform(post("/authorization")
                .param("login", "vasia")
                .param("email", "")
                .param("password", "31242134"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(USER_NOT_FOUNT, parser.getResponse());
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(authorizationDelegateMock, times(1)).submitAuth(anyObject());
        verifyNoMoreInteractions(authorizationDelegateMock);
    }

}
