import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
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

    @Before
    public void init() {
        dataBase = new CreateDataBase(connection);
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
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new TaskModel("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new TaskModel("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new TaskModel("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new TaskModel(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new TaskModel(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new TaskModel(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksNullIT() {
        assertFalse(tasks.create(null) > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Update TASKS TESTS =====================
    //=========================================================

    @Test
    public void updateTasksManyIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new TaskModel("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new TaskModel("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new TaskModel("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));
        List<TaskModel> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                new TaskModel(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                new TaskModel(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                new TaskModel(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
        ));
        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                new TaskModel(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                new TaskModel(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                new TaskModel(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));
        List<TaskModel> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
        ));
        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new TaskModel(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));
        List<TaskModel> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
        ));
        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksNullIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Delete TASKS TESTS =====================
    //=========================================================

    @Test
    public void deleteTasksManyIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new TaskModel("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new TaskModel("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new TaskModel("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        TaskModel deleteTask = new TaskModel(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia");

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new TaskModel(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new TaskModel(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new TaskModel("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));

        TaskModel deleteTask = new TaskModel(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia");

        List<TaskModel> expTasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<TaskModel> tasksMock = new ArrayList<>(Arrays.asList(
                new TaskModel("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        TaskModel deleteTask = new TaskModel(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia");

        List<TaskModel> expTasks = new ArrayList<>();

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksNullIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<TaskModel> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

}
