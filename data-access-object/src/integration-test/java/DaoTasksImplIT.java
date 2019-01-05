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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DaoTasksImplIT.CreateTasks.class,
        DaoTasksImplIT.UpdateTasks.class,
        DaoTasksImplIT.DeleteTasks.class
})
public class DaoTasksImplIT {

    //=========================================================
    //================ CREATE TASKS TESTS ======================
    //=========================================================

    @RunWith(Parameterized.class)
    public static class CreateTasks {

        private IDataSource connection = null;
        private CreateDataBase dataBase = null;
        private IDaoTasks tasks = null;
        private IDaoUsers users = null;

        private UserModel userMock;
        private List<Task> tasksMock;

        private List<Task> expTasks;

        public CreateTasks(UserModel userMock, List<Task> tasksMock, List<Task> expTasks) {
            this.userMock = userMock;
            this.tasksMock = tasksMock;
            this.expTasks = expTasks;
        }

        @Parameterized.Parameters(name = "{index}: createTasks")
        public static List<Object[]> data() {
            return Arrays.asList(new Object[][]
                    {
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                            new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                                            new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                                            new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                            new Task(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                                            new Task(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                                            new Task(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
                                    ))
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
                                    ))
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
                                    ))
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    null,
                                    null
                            },
                    });
        }

        @Before
        public void init() {
            connection = new MySqlTestConnection();
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

        @Test
        public void createTasksIT() {
            int actualUserId = users.create(userMock);
            assertTrue(actualUserId > 0);
            if (tasksMock == null) {
                assertFalse(tasks.create(null) > 0);
                List<Task> actTasks = tasks.read("vasia");
                assertTrue(actTasks.isEmpty());
            } else {
                tasksMock.stream().forEach(t -> {
                    assertTrue(tasks.create(t) > 0);
                });
                List<Task> actTasks = tasks.read("vasia");
                assertArrayEquals(expTasks.toArray(), actTasks.toArray());
            }
        }
    }

    //=========================================================
    //================ Update TASKS TESTS =====================
    //=========================================================

    @RunWith(Parameterized.class)
    public static class UpdateTasks {

        private IDataSource connection = null;
        private CreateDataBase dataBase = null;
        private IDaoTasks tasks = null;
        private IDaoUsers users = null;

        private UserModel userMock;
        private List<Task> tasksMock;
        private List<Task> tasksUpdateMock;
        private List<Task> expTasks;

        public UpdateTasks(UserModel userMock, List<Task> tasksMock, List<Task> tasksUpdateMock, List<Task> expTasks) {
            this.userMock = userMock;
            this.tasksMock = tasksMock;
            this.tasksUpdateMock = tasksUpdateMock;
            this.expTasks = expTasks;
        }

        @Parameterized.Parameters(name = "{index}: updateTasks")
        public static List<Object[]> data() {
            return Arrays.asList(new Object[][]
                    {
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                            new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                                            new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                                            new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                                            new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                                            new Task(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                                            new Task(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                                            new Task(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                                            new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia"),
                                            new Task(3, "NewTasks3", "Add configuration in beck end3", (byte) 0, "vasia"),
                                            new Task(4, "Learn English4", "I mast do learn english every day4", (byte) 0, "vasia"),
                                            new Task(5, "Learn React5", "I mast do learn react and create todo app5", (byte) 0, "vasia")
                                    )),
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                                            new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia"),
                                            new Task(2, "ToDoApp2", "Add all unit and integration tests2", (byte) 0, "vasia")
                                    )),
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
                                    )),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask1", "Todo add new test on dao task1", (byte) 0, "vasia")
                                    )),
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    null,
                                    null,
                                    null
                            },
                    });
        }

        @Before
        public void init() {
            connection = new MySqlTestConnection();
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

        @Test
        public void updateTasksIT() {
            int actualUserId = users.create(userMock);
            assertTrue(actualUserId > 0);
            if (tasksUpdateMock == null) {
                assertFalse(tasks.update(null) > 0);
                List<Task> actTasks = tasks.read("vasia");
                assertTrue(actTasks.isEmpty());
            } else {
                tasksMock.stream().forEach(t -> {
                    assertTrue(tasks.create(t) > 0);
                });
                tasksUpdateMock.stream().forEach(t -> {
                    assertTrue(tasks.update(t) > 0);
                });
                List<Task> actTasks = tasks.read("vasia");
                assertArrayEquals(expTasks.toArray(), actTasks.toArray());
            }
        }
    }

    //=========================================================
    //================ Delete TASKS TESTS =====================
    //=========================================================

    @RunWith(Parameterized.class)
    public static class DeleteTasks {

        private IDataSource connection = null;
        private CreateDataBase dataBase = null;
        private IDaoTasks tasks = null;
        private IDaoUsers users = null;

        private UserModel userMock;
        private List<Task> tasksMock;
        private Task  deleteTask;
        private List<Task> expTasks;

        public DeleteTasks(UserModel userMock, List<Task> tasksMock, Task deleteTask, List<Task> expTasks) {
            this.userMock = userMock;
            this.tasksMock = tasksMock;
            this.deleteTask = deleteTask;
            this.expTasks = expTasks;
        }

        @Parameterized.Parameters(name = "{index}: deleteTasks")
        public static List<Object[]> data() {
            return Arrays.asList(new Object[][]
                    {
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                            new Task("NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                                            new Task("Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                                            new Task("Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
                                    )),
                                    new Task(4, "Learn English", "I mast do learn english every day", (byte) 1, "vasia"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                            new Task(3, "NewTasks", "Add configuration in beck end", (byte) 1, "vasia"),
                                            new Task(5, "Learn React", "I mast do learn react and create todo app", (byte) 1, "vasia")
                                    )),
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                            new Task("ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia")
                                    )),
                                    new Task(2, "ToDoApp", "Add all unit and integration tests", (byte) 1, "vasia"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
                                    ))
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    new ArrayList<>(Arrays.asList(
                                            new Task("MyTask", "Todo add new test on dao task", (byte) 1, "vasia")
                                    )),
                                    new Task(1, "MyTask", "Todo add new test on dao task", (byte) 1, "vasia"),
                                    new ArrayList<>()
                            },
                            {
                                    new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314"),
                                    null,
                                    null,
                                    null
                            }
                    });
        }

        @Before
        public void init() {
            connection = new MySqlTestConnection();
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

        @Test
        public void deleteTasksIT() {
            int actualUserId = users.create(userMock);
            assertTrue(actualUserId > 0);
            if (tasksMock == null) {
                assertFalse(tasks.update(null) > 0);
                List<Task> actTasks = tasks.read("vasia");
                assertTrue(actTasks.isEmpty());
            } else {
                tasksMock.stream().forEach(t -> {
                    assertTrue(tasks.create(t) > 0);
                });
                int deleteId = tasks.delete(deleteTask);
                assertTrue(deleteId > 0);
                List<Task> actTasks = tasks.read("vasia");
                assertArrayEquals(expTasks.toArray(), actTasks.toArray());
            }
        }
    }

}
