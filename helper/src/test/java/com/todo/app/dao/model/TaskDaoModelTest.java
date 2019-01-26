package com.todo.app.dao.model;

import com.todo.app.controller.model.task.TaskModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TaskDaoModelTest {

    private String token;

    private TaskModel task;

    private String toString;

    public TaskDaoModelTest(String token, TaskModel task, String toString) {
        this.token = token;
        this.task = task;
        this.toString = toString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //This is actual test case
                {
                        "token", new TaskModel(), "TaskDaoModel{token='token', taskModel=TaskModel{idTask=0, taskName='null', task='null', status=0}}"
                }
        });
    }

    @Test
    public void taskModelFullConstructorTest() {
        TaskDaoModel taskModel = new TaskDaoModel();
        taskModel.setToken(token);
        taskModel.setTaskModel(task);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTaskModel(), task);
    }

    @Test
    public void taskModelConstructorWithEmptyIdTest() {
        TaskDaoModel taskModel = new TaskDaoModel();
        taskModel.setToken(token);
        taskModel.setTaskModel(task);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTaskModel(), task);
    }

    @Test
    public void taskModelSetTest() {
        TaskDaoModel taskModel = new TaskDaoModel();
        taskModel.setToken(token);
        taskModel.setTaskModel(task);

        assertEquals(taskModel.getToken(), token);
        assertEquals(taskModel.getTaskModel(), task);
    }

    @Test
    public void taskModelEqualsSameModelTest() {
        TaskDaoModel first = new TaskDaoModel();
        first.setToken(token);
        first.setTaskModel(task);

        TaskDaoModel second = new TaskDaoModel();
        second.setToken(token);
        second.setTaskModel(task);

        assertTrue(first.equals(second));
    }

    @Test
    public void taskModelEqualsDifferentModelTest() {
        TaskDaoModel first = new TaskDaoModel();
        first.setToken(token);
        first.setTaskModel(task);

        TaskDaoModel second = new TaskDaoModel();
        second.setToken(token);
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelNullTest() {
        TaskDaoModel first = new TaskDaoModel();
        first.setToken(token);
        first.setTaskModel(task);
        TaskDaoModel second = null;
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelTest() {
        TaskDaoModel first = new TaskDaoModel();
        first.setToken(token);
        first.setTaskModel(task);
        assertTrue(first.equals(first));
    }

    @Test
    public void taskModelHashCodeModelTest() {
        TaskDaoModel first = new TaskDaoModel();

        first.setToken(token);
        first.setTaskModel(task);

        TaskDaoModel second = new TaskDaoModel();
        second.setToken(token);
        second.setTaskModel(task);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void taskModelToStringModelTest() {
        TaskDaoModel first = new TaskDaoModel();

        first.setToken(token);
        first.setTaskModel(task);
        assertEquals(first.toString(), toString);
    }

}