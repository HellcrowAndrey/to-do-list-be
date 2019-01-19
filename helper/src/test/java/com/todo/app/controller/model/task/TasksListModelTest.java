package com.todo.app.controller.model.task;

import com.todo.app.dao.model.TaskDaoModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TasksListModelTest {

    private String token;

    private List<TaskModel> tasks;

    private String toString;

    public TasksListModelTest(String token, List<TaskModel> tasks, String toString) {
        this.token = token;
        this.tasks = tasks;
        this.toString = toString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //This is actual test case
                {
                        "token", new ArrayList<>(), "TasksListModel{token='token', tasks=[]}"
                }
        });
    }

    @Test
    public void taskModelFullConstructorTest() {
        TasksListModel taskModel = new TasksListModel();
        taskModel.setToken(token);
        taskModel.setTasks(tasks);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTasks(), tasks);
    }

    @Test
    public void taskModelConstructorWithEmptyIdTest() {
        TasksListModel taskModel = new TasksListModel();
        taskModel.setToken(token);
        taskModel.setTasks(tasks);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTasks(), tasks);
    }

    @Test
    public void taskModelSetTest() {
        TasksListModel taskModel = new TasksListModel();
        taskModel.setToken(token);
        taskModel.setTasks(tasks);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTasks(), tasks);
    }

    @Test
    public void taskModelEqualsSameModelTest() {
        TasksListModel first = new TasksListModel();
        first.setToken(token);
        first.setTasks(tasks);

        TasksListModel second = new TasksListModel();
        second.setToken(token);
        second.setTasks(tasks);

        assertTrue(first.equals(second));
    }

    @Test
    public void taskModelEqualsDifferentModelTest() {
        TasksListModel first = new TasksListModel();
        first.setToken(token);
        first.setTasks(tasks);

        TasksListModel second = new TasksListModel();
        second.setToken(token);
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelNullTest() {
        TasksListModel first = new TasksListModel();
        first.setToken(token);
        first.setTasks(tasks);
        TaskDaoModel second = null;
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelTest() {
        TasksListModel first = new TasksListModel();
        first.setToken(token);
        first.setTasks(tasks);
        assertTrue(first.equals(first));
    }

    @Test
    public void taskModelHashCodeModelTest() {
        TasksListModel first = new TasksListModel();

        first.setToken(token);
        first.setTasks(tasks);

        TasksListModel second = new TasksListModel();
        second.setToken(token);
        second.setTasks(tasks);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void taskModelToStringModelTest() {
        TasksListModel first = new TasksListModel();

        first.setToken(token);
        first.setTasks(tasks);
        assertEquals(first.toString(), toString);
    }

}