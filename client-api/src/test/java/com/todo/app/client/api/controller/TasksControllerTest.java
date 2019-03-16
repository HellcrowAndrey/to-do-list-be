package com.todo.app.client.api.controller;

import com.google.gson.Gson;
import com.todo.app.client.api.delegat.TasksDelegate;
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

import static com.todo.app.controller.constant.ControllerUtils.INCORRECT_TOKEN;
import static com.todo.app.controller.constant.ControllerUtils.IS_NOT_VALID_PARAMS;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TasksControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TasksDelegate tasksDelegateMock;

    /*
        This is tasks controller.
     */
    @InjectMocks
    private TasksController tasksController;

    /*
        This method do init mock and mockMvc
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(tasksController)
                .build();
    }

    /*
        This test case test incorrect token.
     */
    @Test
    public void tasksOnIncorrectTokenTest() throws Exception {
        when(tasksDelegateMock.submitTasks(anyString())).thenReturn(
                new ResponseModel<>(1, INCORRECT_TOKEN));
        ResultActions result = mockMvc.perform(post("/tasks")
                .param("token", "token"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).submitTasks(anyString());
        verifyNoMoreInteractions(tasksDelegateMock);
    }

    /*
       This test case test response data.
    */
    @Test
    public void tasksOnTest() throws Exception {
        when(tasksDelegateMock.submitTasks(anyString())).thenReturn(
                new ResponseModel<>(1, "{}"));
        ResultActions result = mockMvc.perform(post("/tasks")
                .param("token", "token"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).submitTasks(anyString());
        verifyNoMoreInteractions(tasksDelegateMock);
    }

    /*
        This test case test response data. Command create actual.
     */
    @Test
    public void taskCommandCreateActualTest() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(tasksController)
                .build();
        when(tasksDelegateMock.dispatcher(anyObject())).thenReturn(
                new ResponseModel<>(1, "1"));
        ResultActions result = mockMvc.perform(post("/{command}/task", "create")
                .param("token", "token").param("data", "data"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).dispatcher(anyObject());
        verifyNoMoreInteractions(tasksDelegateMock);
        assertEquals("1", parser.getResponse());
    }

    /*
        This test case test response data. Command create invalid.
    */
    @Test
    public void taskCommandCreateInvalidTest() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(tasksController)
                .build();
        when(tasksDelegateMock.dispatcher(anyObject())).thenReturn(
                new ResponseModel<>(1, IS_NOT_VALID_PARAMS));
        ResultActions result = mockMvc.perform(post("/{command}/task", "create")
                .param("token", "token").param("data", "data"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(IS_NOT_VALID_PARAMS, parser.getResponse());
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).dispatcher(anyObject());
        verifyNoMoreInteractions(tasksDelegateMock);
    }

    /*
        This test case test response data. Command create actual.
    */
    @Test
    public void taskCommandUpdateActualTest() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(tasksController)
                .build();
        when(tasksDelegateMock.dispatcher(anyObject())).thenReturn(
                new ResponseModel<>(1, "1"));
        ResultActions result = mockMvc.perform(post("/{command}/task", "create")
                .param("token", "token").param("data", "data"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).dispatcher(anyObject());
        verifyNoMoreInteractions(tasksDelegateMock);
        assertEquals("1", parser.getResponse());
    }

    /*
        This test case test response data. Command create invalid.
    */
    @Test
    public void taskCommandUpdateInvalidTest() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(tasksController)
                .build();
        when(tasksDelegateMock.dispatcher(anyObject())).thenReturn(
                new ResponseModel<>(1, IS_NOT_VALID_PARAMS));
        ResultActions result = mockMvc.perform(post("/{command}/task", "create")
                .param("token", "token").param("data", "data"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        final String tmp = actual.getResponse().getContentAsString();
        final Gson gson = new Gson();
        final ResponseModel parser = gson.fromJson(tmp, ResponseModel.class);
        assertEquals(IS_NOT_VALID_PARAMS, parser.getResponse());
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegateMock, times(1)).dispatcher(anyObject());
        verifyNoMoreInteractions(tasksDelegateMock);
    }

}