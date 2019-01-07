import com.todo.app.controller.model.task.Task;
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

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new Task(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new Task(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new Task(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));

        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void createTasksNullIT() {
        assertFalse(tasks.create(null) > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Update TASKS TESTS =====================
    //=========================================================

    @Test
    public void updateTasksManyIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));
        List<Task> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                new Task(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                new Task(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                new Task(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
        ));
        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                new Task(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                new Task(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                new Task(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));
        List<Task> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
        ));
        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));
        List<Task> tasksUpdateMock = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
        ));
        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
        ));
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        tasksUpdateMock.stream().forEach(t -> {
            assertTrue(tasks.update(t) > 0);
        });
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void updateTasksNullIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

    //=========================================================
    //================ Delete TASKS TESTS =====================
    //=========================================================

    @Test
    public void deleteTasksManyIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        Task deleteTask = new Task(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia");

        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                new Task(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                new Task(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
        ));

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksTwoIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
        ));

        Task deleteTask = new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia");

        List<Task> expTasks = new ArrayList<>(Arrays.asList(
                new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksOneIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        List<Task> tasksMock = new ArrayList<>(Arrays.asList(
                new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
        ));

        Task deleteTask = new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia");

        List<Task> expTasks = new ArrayList<>();

        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        tasksMock.stream().forEach(t -> {
            assertTrue(tasks.create(t) > 0);
        });
        int deleteId = tasks.delete(deleteTask);
        assertTrue(deleteId > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertArrayEquals(expTasks.toArray(), actTasks.toArray());
    }

    @Test
    public void deleteTasksNullIT() {
        UserModel userMock = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actualUserId = users.create(userMock);
        assertTrue(actualUserId > 0);
        assertFalse(tasks.update(null) > 0);
        List<Task> actTasks = tasks.read("vasia");
        assertTrue(actTasks.isEmpty());
    }

}
