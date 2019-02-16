package com.todo.app.client.api.controller;

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

import static com.todo.app.utils.ControllerUtils.INCORRECT_TOKEN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TasksControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TasksDelegate tasksDelegate;

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

    @Test
    public void tasksOnIncorrectTokenTest() throws Exception {
        when(tasksDelegate.submitTasks(anyString())).thenReturn(
                new ResponseModel<>(1, INCORRECT_TOKEN));
        ResultActions result = mockMvc.perform(post("/tasks")
                .param("token", "token"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegate, times(1)).submitTasks(anyString());
        verifyNoMoreInteractions(tasksDelegate);
    }

    @Test
    public void tasksOnTest() throws Exception {
        when(tasksDelegate.submitTasks(anyString())).thenReturn(
                new ResponseModel<>(1, "{}"));
        ResultActions result = mockMvc.perform(post("/tasks")
                .param("token", "token"))
                .andExpect(status().isOk());
        MvcResult actual = result.andReturn();
        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        verify(tasksDelegate, times(1)).submitTasks(anyString());
        verifyNoMoreInteractions(tasksDelegate);
    }

}