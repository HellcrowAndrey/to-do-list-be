package com.todo.app.controller.model.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TaskModelTest {

    private long idTask;

    private String taskName;

    private String task;

    private byte status;

    private String toString;

    public TaskModelTest(long idTask, String taskName, String task, byte status, String toString) {
        this.idTask = idTask;
        this.taskName = taskName;
        this.task = task;
        this.status = status;
        this.toString = toString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //This is actual test case
                {
                        1l, "first task", "Do test case", (byte) 1,
                        "TaskModel{idTask=1, taskName='first task', task='Do test case', status=1}"
                }
        });
    }

    @Test
    public void taskModelFullConstructorTest() {
        TaskModel taskModel = new TaskModel(idTask, taskName, task, status);
        assertEquals(taskModel.getIdTask(), idTask);
        assertEquals(taskModel.getTaskName(), taskName);
        assertEquals(taskModel.getTask(), task);
        assertEquals(taskModel.getStatus(), status);
    }

    @Test
    public void taskModelConstructorWithEmptyIdTest() {
        TaskModel taskModel = new TaskModel(taskName, task, status);
        assertEquals(taskModel.getTaskName(), taskName);
        assertEquals(taskModel.getTask(), task);
        assertEquals(taskModel.getStatus(), status);
    }

    @Test
    public void taskModelSetTest() {
        TaskModel taskModel = new TaskModel();
        taskModel.setIdTask(idTask);
        taskModel.setTaskName(taskName);
        taskModel.setTask(task);
        taskModel.setStatus(status);
        assertEquals(taskModel.getIdTask(), idTask);
        assertEquals(taskModel.getTaskName(), taskName);
        assertEquals(taskModel.getTask(), task);
        assertEquals(taskModel.getStatus(), status);
    }

    @Test
    public void taskModelEqualsSameModelTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        TaskModel second = new TaskModel(idTask, taskName, task, status);
        assertTrue(first.equals(second));
    }

    @Test
    public void taskModelEqualsDifferentModelTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        TaskModel second = new TaskModel(taskName, task, status);
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelNullTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        TaskModel second = null;
        assertFalse(first.equals(second));
    }

    @Test
    public void taskModelEqualsOneModelTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        assertTrue(first.equals(first));
    }

    @Test
    public void taskModelHashCodeModelTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        TaskModel second = new TaskModel(idTask, taskName, task, status);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void taskModelToStringModelTest() {
        TaskModel first = new TaskModel(idTask, taskName, task, status);
        assertEquals(first.toString(), toString);
    }
}
