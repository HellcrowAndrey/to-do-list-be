package com.todo.app.client.api.delegat;

import com.google.gson.Gson;
import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.controller.model.task.TaskUpdateModel;
import com.todo.app.service.tasks.IServiceTasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.todo.app.controller.constant.ControllerUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.field;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IServiceTasks.class)
public class TasksDelegateTest {

    @Mock
    private IServiceTasks serviceTasksMock;

    /*
        This field keeps info about user token in cache
     */
    private String tokenFoCache = "token";

    /*
        This field keeps info about user token in db
     */
    private String tokenFoDb = "token1";

    /*
        Clear cache manager before tests
     */
    @Before
    public void setup() {
        CacheManager manager = CacheManager.getInstance();
        manager.removeTask(this.tokenFoDb, 1l);
        manager.removeTask(this.tokenFoDb, 2l);
        manager.removeTask(this.tokenFoDb, 3l);
        manager.removeTask(this.tokenFoDb, 4l);
        manager.removeTask(this.tokenFoDb, 5l);
    }

    /*
        Clear cache manager after tests
     */
    @After
    public void upset() {
        CacheManager manager = CacheManager.getInstance();
        manager.removeTask(this.tokenFoCache, 1l);
        manager.removeTask(this.tokenFoCache, 2l);
        manager.removeTask(this.tokenFoCache, 3l);
        manager.removeTask(this.tokenFoCache, 4l);
        manager.removeTask(this.tokenFoCache, 5l);
    }

    //================================================================
    //=========== Tests on submitTasks(token) method =================
    //================================================================

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
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
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
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        final Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
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
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoCache);
        final Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
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
        when(serviceTasksMock.read(this.tokenFoDb)).thenReturn(tasks);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        final Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
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
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        final Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
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
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(this.tokenFoDb);
        final Gson gson = new Gson();
        final ResponseModel<String> expected = new ResponseModel<>(1l,
                gson.toJson(tasks));
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
      This test case test method submitTasks.This is test on null
      token.
    */
    @Test
    public void submitTasksTokenIsNullTest() {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks(null);
        final ResponseModel<String> expected = new ResponseModel<>(1l,
                INCORRECT_TOKEN);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
      This test case test method submitTasks.This is test on empty
      token.
    */
    @Test
    public void submitTasksTokenIsEmptyTest() {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitTasks("");
        final ResponseModel<String> expected = new ResponseModel<>(1l,
                INCORRECT_TOKEN);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    //================================================================
    //=========== Tests on restartAuth(token) method =================
    //================================================================

    /*
        This is actual test on null result if cache not empty.
     */
    @Test
    public void restartExistInCacheAuthTest() throws Exception {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "restartAuth", tokenFoCache);
        assertEquals(null, actual);
    }

    /*
        This is actual test on null result if cache not empty.
     */
    @Test
    public void restartEmptyInCacheAuthTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "restartAuth", "tokenUserExist");
        assertEquals(RESTART_ACCOUNT, actual.getResponse());
    }

    //================================================================
    //=========== Tests on jsonParser(data) method ===================
    //================================================================

    /*
        This test case test jsonParser method. This is actual test on
        json TaskModel.
     */
    @Test
    public void jsonParserActualTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        TaskModel actual = Whitebox.invokeMethod(delegate, "jsonParser", data);
        final TaskModel expected = new TaskModel("taskName", "this istask", (byte) 1);
        assertEquals(expected, actual);
    }

    /*
        This test case test jsonParser method. This is not actual test.
     */
    @Test
    public void jsonParserNotActualExceptionTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "{\"taskName:\"taskName\",\"task\":\"this istask\",\"status\":1}";
        TaskModel actual = Whitebox.invokeMethod(delegate, "jsonParser", data);
        final TaskModel expected = null;
        assertEquals(expected, actual);
    }

    /*
        This test case test jsonParser method. This is not actual test.
        Test on null.
     */
    @Test
    public void jsonParserNotActualNullTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = null;
        final TaskModel actual = Whitebox.invokeMethod(delegate, "jsonParser", data);
        final TaskModel expected = null;
        assertEquals(expected, actual);
    }

    //================================================================
    //=========== Tests on submitDelete(task) method =================
    //================================================================

    /*
        This test case on submitDelete method is valid test.
     */
    @Test
    public void submitDeleteActualTest() throws Exception {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.delete(1l)).thenReturn(1l);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        String data = "1";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(DELETE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitDelete", taskUpdateModel);
        assertEquals(String.valueOf(1l), actual.getResponse());
        final List<TaskModel> tasksInCache = cacheManager.fetchTasks(tokenFoCache);
        assertTrue(tasksInCache.size() < 5);
    }

    /*
       This test case on submitDelete method is not valid data.
    */
    @Test
    public void submitDeleteJsonExceptionTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "mock";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(DELETE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitDelete", taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    //================================================================
    //=========== Tests on submitUpdate(task) method =================
    //================================================================

    /*
        This test case on submitUpdate method is valid test.
     */
    @Test
    public void submitUpdateActualTest() throws Exception {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.update(anyObject())).thenReturn(1l);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(UPDATE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitUpdate", taskUpdateModel);
        assertEquals(String.valueOf(1l), actual.getResponse());
    }

    /*
        This test case on submitUpdate method isn't valid test.
     */
    @Test
    public void submitUpdateNotActualTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(UPDATE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitUpdate", taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    //================================================================
    //=========== Tests on submitCreate(task) method =================
    //================================================================

    /*
        This test case on submitUpdate method is valid test.
     */
    @Test
    public void submitCreateActualTest() throws Exception {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.create(anyObject())).thenReturn(1l);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(CREATE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitCreate", taskUpdateModel);
        assertEquals(String.valueOf(1l), actual.getResponse());
    }

    /*
        This test case on submitUpdate method isn't valid test.
     */
    @Test
    public void submitCreateNotActualTest() throws Exception {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final String data = "";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(CREATE, tokenFoCache, data);
        ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "submitCreate", taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    //================================================================
    //=========== Tests on dispatcher(task) method ===================
    //================================================================

    /*
        This test case on dispatcher method is valid test.
        Test on create;
     */
    @Test
    public void dispatcherCommandCreateTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.create(anyObject())).thenReturn(1l);
        final String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(CREATE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(String.valueOf(1l) , actual.getResponse());
    }

    /*
        This test case on dispatcher method isn't valid test.
        Test on create;
     */
    @Test
    public void dispatcherCommandCreateInvalidTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        final String data = "3214231";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(
                CREATE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case on dispatcher method is valid test.
        Test on update;
     */
    @Test
    public void dispatcherCommandUpdateTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.update(anyObject())).thenReturn(1l);
        final String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(UPDATE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(String.valueOf(1l) , actual.getResponse());
    }

    /*
        This test case on dispatcher method isn't valid test.
        Test on update;
     */
    @Test
    public void dispatcherCommandUpdateInvalidTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        final String data = "3214231";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(
                UPDATE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case on dispatcher method is valid test.
        Test on delete;
     */
    @Test
    public void dispatcherCommandDeleteTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        when(serviceTasksMock.delete(1l)).thenReturn(1l);
        final String data = "1";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(DELETE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(String.valueOf(1l) , actual.getResponse());
    }

    /*
        This test case on dispatcher method isn't valid test.
        Test on delete;
     */
    @Test
    public void dispatcherCommandDeleteInvalidTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        final String data = "mock";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(
                DELETE, tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
      This test case on dispatcher method isn't valid test.
      Test on default case;
   */
    @Test
    public void dispatcherDefaultCaseTest() {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        final CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.addTasks(tokenFoCache, tasks);
        final String data = "mock";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(
                "default", tokenFoCache, data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case on dispatcher method is valid test.
        Test on restartAuth;
     */
    @Test
    public void dispatcherRestartAuthTest() {
        final String data = "{\"taskName\":\"taskName\",\"task\":\"this istask\",\"status\":1}";
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel(CREATE, "tokenOnRestartAuth", data);
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        assertEquals(RESTART_ACCOUNT , actual.getResponse());
    }

    /*
        This test case on dispatcher method isn't valid test.
        Test on null;
     */
    @Test
    public void dispatcherNullTest() {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final TaskUpdateModel taskUpdateModel = null;
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        final ResponseModel<String> expected = new ResponseModel<>(
                1l, IS_NOT_VALID_PARAMS);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

    /*
        This test case on dispatcher method isn't valid test.
        Test on empty model;
     */
    @Test
    public void dispatcherEmptyModelTest() {
        final TasksDelegate delegate = new TasksDelegate(serviceTasksMock);
        final TaskUpdateModel taskUpdateModel = new TaskUpdateModel();
        final ResponseModel<String> actual = delegate.dispatcher(taskUpdateModel);
        final ResponseModel<String> expected = new ResponseModel<>(
                1l, IS_NOT_VALID_PARAMS);
        assertEquals(expected.getResponse(), actual.getResponse());
    }

}