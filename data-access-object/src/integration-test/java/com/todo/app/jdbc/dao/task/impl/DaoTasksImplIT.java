package com.todo.app.jdbc.dao.task.impl;

import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.dao.model.TaskDaoModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.CreateDataBase;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlDebugConnection;
import com.todo.app.jdbc.dao.data.source.impl.MySqlTestConnection;
import com.todo.app.jdbc.dao.tasks.IDaoTasks;
import com.todo.app.jdbc.dao.tasks.impl.DaoTasksImpl;
import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.jdbc.dao.users.impl.DaoUsersImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySqlTestConnection.class)
@ActiveProfiles("integration-tests")
public class DaoTasksImplIT {

    @Autowired
    private IDataSource connection;
    private CreateDataBase dataBase = null;
    private IDaoTasks tasks = null;
    private IDaoUsers users = null;

    private UserDaoModel userMock = new UserDaoModel();

    private List<TaskDaoModel> createTasksFive() {
        List<TaskDaoModel> list = new ArrayList<>();
        TaskDaoModel t1 = new TaskDaoModel();
        t1.setToken("token1");
        t1.setTaskModel(new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1));
        TaskDaoModel t2 = new TaskDaoModel();
        t2.setToken("token1");
        t2.setTaskModel(new TaskModel("NewTasks", "Add configuration in beck end", (byte) 1));
        TaskDaoModel t3 = new TaskDaoModel();
        t3.setToken("token1");
        t3.setTaskModel(new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1));
        TaskDaoModel t4 = new TaskDaoModel();
        t4.setToken("token1");
        t4.setTaskModel(new TaskModel("Learn React", "I mast do learn react and create todo app", (byte) 1));
        TaskDaoModel t5 = new TaskDaoModel();
        t5.setToken("token1");
        t5.setTaskModel(new TaskModel("Learn English", "I mast do learn english every day", (byte) 1));
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        return list;
    }

    private List<TaskDaoModel> createTasksTwo() {
        List<TaskDaoModel> list = new ArrayList<>();
        TaskDaoModel t1 = new TaskDaoModel();
        t1.setToken("token1");
        t1.setTaskModel(new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1));
        TaskDaoModel t2 = new TaskDaoModel();
        t2.setToken("token1");
        t2.setTaskModel(new TaskModel("NewTasks", "Add configuration in beck end", (byte) 1));
        list.add(t1);
        list.add(t2);
        return list;
    }

    private List<TaskDaoModel> createTasksOne() {
        List<TaskDaoModel> list = new ArrayList<>();
        TaskDaoModel t1 = new TaskDaoModel();
        t1.setToken("token1");
        t1.setTaskModel(new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1));
        list.add(t1);
        return list;
    }

    @Before
    public void init() {
        byte[] hash = new byte[]{1, 2, 3, 4, 5, 6};
        byte[] salt = new byte[]{'r', 'q', 'y', 'u', 'i'};
        userMock.setIdUser(1l);
        userMock.setLogin("login1");
        userMock.setEmail("email1@gemail.com");
        userMock.setHash(hash);
        userMock.setSalt(salt);
        userMock.setToken("token1");
        userMock.setEnable(true);

        dataBase = new com.todo.app.jdbc.dao.CreateDataBase(connection);
        dataBase.createTableUsers();
        dataBase.createTableTasks();
        tasks = new DaoTasksImpl(connection);
        users = new DaoUsersImpl(connection);
    }

    @After
    public void drop() {
        dataBase.dropTableTasks();
        dataBase.dropTableUsers();
    }

    //=========================================================
    //================ CREATE TASKS TESTS =====================
    //=========================================================

    @Test
    public void createTasksManyIT() {

        List<TaskDaoModel> tasksMock = createTasksFive();
        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "MyTask", "Todo add new test on dao task", (byte) 1),
                new TaskModel(2l, "NewTasks", "Add configuration in beck end", (byte) 1),
                new TaskModel(3l, "ToDoApp", "Add all unit and integration tests", (byte) 1),
                new TaskModel(4l, "Learn React", "I mast do learn react and create todo app", (byte) 1),
                new TaskModel(5l, "Learn English", "I mast do learn english every day", (byte) 1)
        ));
        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksTwoIT() {

        List<TaskDaoModel> tasksMock = createTasksTwo();

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "MyTask", "Todo add new test on dao task", (byte) 1),
                new TaskModel(2l, "NewTasks", "Add configuration in beck end", (byte) 1)
        ));

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksOneIT() {
        byte[] hash = new byte[]{1, 2, 3, 4, 5, 6};
        byte[] salt = new byte[]{'r', 'q', 'y', 'u', 'i'};
        UserDaoModel userMock = new UserDaoModel();
        userMock.setIdUser(1l);
        userMock.setLogin("login1");
        userMock.setEmail("email1@gemail.com");
        userMock.setHash(hash);
        userMock.setSalt(salt);
        userMock.setToken("token1");
        userMock.setEnable(true);
        List<TaskDaoModel> tasksMock = createTasksOne();
        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "MyTask", "Todo add new test on dao task", (byte) 1)
        ));
        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksNullIT() {
        assertFalse(tasks.create(null) > 0);
        List<TaskModel> actTasks = tasks.read("token1");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Update TASKS TESTS =====================
    //=========================================================

    @Test
    public void updateTasksManyIT() {
        List<TaskDaoModel> tasksMock = createTasksFive();

        TaskDaoModel taskDaoModel1 = new TaskDaoModel();
        taskDaoModel1.setToken("token1");
        taskDaoModel1.setTaskModel(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));

        TaskDaoModel taskDaoModel2 = new TaskDaoModel();
        taskDaoModel2.setToken("token1");
        taskDaoModel2.setTaskModel(new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0));

        TaskDaoModel taskDaoModel3 = new TaskDaoModel();
        taskDaoModel3.setToken("token1");
        taskDaoModel3.setTaskModel(new TaskModel(3, "NewTasks3", "Add configuration in beck end3", (byte) 0));

        TaskDaoModel taskDaoModel4 = new TaskDaoModel();
        taskDaoModel4.setToken("token1");
        taskDaoModel4.setTaskModel(new TaskModel(4, "Learn English4", "I mast do learn english every day4", (byte) 0));

        TaskDaoModel taskDaoModel5 = new TaskDaoModel();
        taskDaoModel5.setToken("token1");
        taskDaoModel5.setTaskModel(new TaskModel(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0));

        List<TaskDaoModel> tasksUpdate = new ArrayList<>();
        tasksUpdate.add(taskDaoModel1);
        tasksUpdate.add(taskDaoModel2);
        tasksUpdate.add(taskDaoModel3);
        tasksUpdate.add(taskDaoModel4);
        tasksUpdate.add(taskDaoModel5);

        List<TaskModel> actualMock = new ArrayList<>();
        actualMock.add(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));
        actualMock.add(new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0));
        actualMock.add(new TaskModel(3, "NewTasks3", "Add configuration in beck end3", (byte) 0));
        actualMock.add(new TaskModel(4, "Learn English4", "I mast do learn english every day4", (byte) 0));
        actualMock.add(new TaskModel(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0));

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdate.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(actualMock.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksTwoIT() {
        List<TaskDaoModel> tasksMock = createTasksTwo();

        TaskDaoModel taskDaoModel1 = new TaskDaoModel();
        taskDaoModel1.setToken("token1");
        taskDaoModel1.setTaskModel(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));

        TaskDaoModel taskDaoModel2 = new TaskDaoModel();
        taskDaoModel2.setToken("token1");
        taskDaoModel2.setTaskModel(new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0));

        List<TaskDaoModel> tasksUpdate = new ArrayList<>();
        tasksUpdate.add(taskDaoModel1);
        tasksUpdate.add(taskDaoModel2);

        List<TaskModel> actualMock = new ArrayList<>();
        actualMock.add(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));
        actualMock.add(new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0));

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdate.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(actualMock.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksOneIT() {
        List<TaskDaoModel> tasksMock = createTasksOne();

        TaskDaoModel taskDaoModel1 = new TaskDaoModel();
        taskDaoModel1.setToken("token1");
        taskDaoModel1.setTaskModel(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));

        List<TaskDaoModel> tasksUpdate = new ArrayList<>();
        tasksUpdate.add(taskDaoModel1);

        List<TaskModel> actualMock = new ArrayList<>();
        actualMock.add(new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0));

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdate.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(actualMock.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksNullIT() {
        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<TaskModel> actTasks = tasks.read("token1");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Delete TASKS TESTS =====================
    //=========================================================

    @Test
    public void deleteTasksManyIT() {
        List<TaskDaoModel> tasksMock = createTasksFive();

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1),
                new TaskModel(2, "NewTasks", "Add configuration in beck end", (byte) 1),
                new TaskModel(3, "ToDoApp", "Add all unit and integration tests", (byte) 1),
                new TaskModel(5, "Learn English", "I mast do learn english every day", (byte) 1)
        ));
        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        long deleteId = tasks.delete(4);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksTwoIT() {
        List<TaskDaoModel> tasksMock = createTasksTwo();

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1)
        ));

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        long deleteId = tasks.delete(2);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksOneIT() {
        List<TaskDaoModel> tasksMock = createTasksOne();

        List<TaskModel> expTasks = new ArrayList<>();

        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        long deleteId = tasks.delete(1);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("token1");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksNullIT() {
        long actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

}
