package com.todo.app.client.api.delegat;

import com.google.gson.Gson;
import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.service.tasks.IServiceTasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.todo.app.utils.ControllerUtils.INCORRECT_TOKEN;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IServiceTasks.class)
public class TasksDelegateTest {

    @Mock
    private IServiceTasks serviceTasksMock;

    private String tokenFoCache = "token";

    private String tokenFoDb = "token1";

    @Before
    public void setup() {
        CacheManager manager = CacheManager.getInstance();

        manager.removeTask(this.tokenFoDb, 1l);
        manager.removeTask(this.tokenFoDb, 2l);
        manager.removeTask(this.tokenFoDb, 3l);
        manager.removeTask(this.tokenFoDb, 4l);
        manager.removeTask(this.tokenFoDb, 5l);
    }

    @After
    public void upset() {
        CacheManager manager = CacheManager.getInstance();
        manager.removeTask(this.tokenFoCache, 1l);
        manager.removeTask(this.tokenFoCache, 2l);
        manager.removeTask(this.tokenFoCache, 3l);
        manager.removeTask(this.tokenFoCache, 4l);
        manager.removeTask(this.tokenFoCache, 5l);
    }

    /*
        This test case test method submitTasks.This is actual test
        Tasks exist in cache.
     */
    @Test
    public void submitTasksExistInCacheManyTest() {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        cacheManager.addTasks(this.tokenFoCache, tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
       This test case test method submitTasks.This is actual test
       Tasks exist in cache.
    */
    @Test
    public void submitTasksExistInCacheTwoTest() {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1)
        ));
        cacheManager.addTasks(this.tokenFoCache, tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
       This test case test method submitTasks.This is actual test
       Tasks exist in cache.
    */
    @Test
    public void submitTasksExistInCacheOneTest() {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1)
        ));
        cacheManager.addTasks(this.tokenFoCache, tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
       This test case test method submitTasks.This is actual test
       Tasks exist in db.
    */
    @Test
    public void submitTasksInDbManyTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        CacheManager cacheManager = CacheManager.getInstance();
        when(serviceTasksMock.read(this.tokenFoDb)).thenReturn(tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
       This test case test method submitTasks.This is actual test
       Tasks exist in db.
    */
    @Test
    public void submitTasksInDbTwoTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1)
        ));
        when(serviceTasksMock.read(this.tokenFoDb)).thenReturn(tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
      This test case test method submitTasks.This is actual test
      Tasks exist in db.
    */
    @Test
    public void submitTasksInDbOneTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1)
        ));
        when(serviceTasksMock.read(this.tokenFoDb)).thenReturn(tasks);
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        Gson gson = new Gson();
        ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
      This test case test method submitTasks.This is test on null
      token.
    */
    @Test
    public void submitTasksTokenIsNullTest() {
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks(null);
        ResponseModel<String> expected = new ResponseModel<>(1l,
                INCORRECT_TOKEN);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
      This test case test method submitTasks.This is test on empty
      token.
    */
    @Test
    public void submitTasksTokenIsEmptyTest() {
        TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = delegate.submitTasks("");
        ResponseModel<String> expected = new ResponseModel<>(1l,
                INCORRECT_TOKEN);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

}