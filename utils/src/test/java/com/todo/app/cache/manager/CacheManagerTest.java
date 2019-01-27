package com.todo.app.cache.manager;

import com.todo.app.controller.model.task.TaskModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CacheManagerTest.AddTask.class,
        CacheManagerTest.FetchTask.class,
        CacheManagerTest.AddTasks.class,
        CacheManagerTest.AddTasksMaxCache.class,
        CacheManagerTest.FetchTasks.class,
        CacheManagerTest.UpdateTask.class,
        CacheManagerTest.RemoveTask.class,
})
public class CacheManagerTest {

    //=====================================================================
    //== This test case for testing method addTask in CacheManager class ==
    //=====================================================================

    @RunWith(Parameterized.class)
    public static class AddTask {

        //This field is user token
        private String token;

        //This field is task model
        private TaskModel task;

        //This is list with TaskModel object
        private List<TaskModel> addTaskToCache;

        //This is expected result
        private TaskModel expected;

        //This constructor use for parameterized tests
        public AddTask(String token, TaskModel task, List<TaskModel> addTaskToCache, TaskModel expected) {
            this.token = token;
            this.task = task;
            this.addTaskToCache = addTaskToCache;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: addTask")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                                    new TaskModel(4l, "new task three", "i think need read about docker", (byte) 1)
                            )),
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            )),
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    {
                            "3213586786758",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    {
                            null,
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            null,
                            null
                    },
                    {
                            "",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            null,
                            null
                    }
            });
        }

        @Test
        public void addTaskTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTask(token, task);
            TaskModel actual = cacheManager.fetchTask(token, task.getIdTask());
            assertEquals(expected, actual);
        }

    }

    //======================================================================
    //== This test case for testing method fetchTask in CacheManager class =
    //======================================================================

    @RunWith(Parameterized.class)
    public static class FetchTask {

        private String token;
        private TaskModel task;
        private TaskModel expected;

        public FetchTask(String token, TaskModel task, TaskModel expected) {
            this.token = token;
            this.task = task;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: fetchTasks")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is actual test case
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    // This is actual test case
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    // This is test not valid. Doesn't find this task in cache
                    {
                            "3213586786758",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            null
                    },
                    // This is test not valid. Test on NullPointer
                    {
                            null,
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            null
                    },
                    // This is test not valid. Test on NullPointer
                    {
                            "",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            null
                    }
            });
        }

        @Test
        public void fetchTasksTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTask(token, task);
            if (token != null && token.equals("3213586786758")) {
                TaskModel actual = cacheManager.fetchTask("343214532543215", task.getIdTask());
                assertEquals(expected, actual);
            } else {
                TaskModel actual = cacheManager.fetchTask(token, task.getIdTask());
                assertEquals(expected, actual);
            }
        }

    }

    //======================================================================
    //== This test case for testing method addTasks in CacheManager class ==
    //======================================================================

    @RunWith(Parameterized.class)
    public static class AddTasks {

        // This field keeps info about token
        private String token;

        // This field keeps info about tasks
        private List<TaskModel> tasks;

        // This field keeps info about task id
        private long idTask;

        // This is object in cache
        private TaskModel expected;

        //This is constructor use for parameterized tests
        public AddTasks(String token, List<TaskModel> tasks, long idTask, TaskModel expected) {
            this.token = token;
            this.tasks = tasks;
            this.idTask = idTask;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: addTasks")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is valid test case many
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                                    new TaskModel(5l, "new task three", "i think need read about docker", (byte) 1)
                            )),
                            4l,
                            new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1)
                    },
                    // This is valid test case two
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            )),
                            2l,
                            new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                    },
                    // This is valid test case one
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            1l,
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                    },
                    // This is not valid test case token is null
                    {
                            null,
                            new ArrayList<>(),
                            0l,
                            null
                    },
                    // This is not valid test case token is empty
                    {
                            null,
                            new ArrayList<>(),
                            0l,
                            null
                    },
                    // This is not valid test case array is null
                    {
                            "31234213421356432",
                            null,
                            0l,
                            null
                    },
            });
        }

        @Test
        public void addTasksTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTasks(token, tasks);
            TaskModel actual = cacheManager.fetchTask(token, idTask);
            assertEquals(expected, actual);
        }

    }

    //======================================================================
    //== This test case for testing method addTasks if max cache size ======
    //==                    in CacheManager class                     ======
    //======================================================================

    @RunWith(PowerMockRunner.class)
    @PrepareForTest(CacheManager.class)
    public static class AddTasksMaxCache {

        @Test
        public void addTasksTest() throws Exception {
            //Now do set cache
            CacheManager cacheManager = CacheManager.getInstance();
            Whitebox.setInternalState(CacheManager.class, "MAX_CACHE_SIZE", 5);
            Thread.sleep(2l);
            cacheManager.addTasks("12332112", new ArrayList<>(Arrays.asList(
                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
            )));
            Thread.sleep(2l);
            cacheManager.addTasks("3213124", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("3213321", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("3213", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("6546456", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.fetchTask("12332112", 1l);
            List<TaskModel> mock = new ArrayList<>(Arrays.asList(
                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                    new TaskModel(5l, "new task three", "i think need read about docker", (byte) 1)
            ));
            //Call to method maxCacheSize
            Whitebox.invokeMethod(cacheManager, "maxCacheSize", "1231232134123", mock);
            TaskModel actual = cacheManager.fetchTask("3213124", 5l);
            assertNull(actual);
        }

        @Test
        public void addTasksExistInCacheTest() throws Exception {
            //Now do set cache
            CacheManager cacheManager = CacheManager.getInstance();
            Whitebox.setInternalState(CacheManager.class, "MAX_CACHE_SIZE", 5);
            Thread.sleep(2l);
            cacheManager.addTasks("12332112", new ArrayList<>(Arrays.asList(
                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
            )));
            Thread.sleep(2l);
            cacheManager.addTasks("3213124", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("3213321", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("3213", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.addTasks("6546456", new ArrayList<>());
            Thread.sleep(2l);
            cacheManager.fetchTask("12332112", 1l);
            List<TaskModel> mock = new ArrayList<>(Arrays.asList(
                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                    new TaskModel(5l, "new task three", "i think need read about docker", (byte) 1)
            ));
            //Call to method maxCacheSize
            Whitebox.invokeMethod(cacheManager, "maxCacheSize", "6546456", mock);
            TaskModel actual = cacheManager.fetchTask("3213124", 5l);
            assertNull(actual);
        }
    }

    //========================================================================
    //== This test case for testing method fetchTasks in CacheManager class ==
    //========================================================================

    @RunWith(Parameterized.class)
    public static class FetchTasks {

        //This is user token
        private String token;

        //This is list with tasks
        private List<TaskModel> tasks;

        //This is constructor use for parameterized tests
        public FetchTasks(String token, List<TaskModel> tasks) {
            this.token = token;
            this.tasks = tasks;
        }

        @Parameterized.Parameters(name = "{index}: fetchTasks")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //This is actual test
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                                    new TaskModel(4l, "new task three", "i think need read about docker", (byte) 1)
                            ))
                    },
                    //This is actual test
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            ))
                    },
                    //This is actual test
                    {
                            "1231232134123",
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            ))
                    },
                    //This is doesn't valid test. Token is null
                    {
                            null,
                            null
                    },
                    //This is doesn't valid test. Token is empty
                    {
                            "",
                            null
                    },
            });
        }

        @Test
        public void fetchTasksTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTasks(token, tasks);
            if (token == null || token.equals("")) {
                List<TaskModel> actual = cacheManager.fetchTasks(token);
                assertNull(actual);
            } else {
                List<TaskModel> actual = cacheManager.fetchTasks(token);
                assertArrayEquals(tasks.toArray(), actual.toArray());
            }
        }
    }

    //========================================================================
    //== This test case for testing method updateTask in CacheManager class ==
    //========================================================================

    @RunWith(Parameterized.class)
    public static class UpdateTask {

        //This is user token
        private String token;

        //This is task
        private TaskModel task;

        //This is list with tasks
        private List<TaskModel> tasks;

        //This field is task id
        private long expected;

        //This is constructor use for parameterized tests
        public UpdateTask(String token, TaskModel task,
                          List<TaskModel> tasks, long expected) {
            this.token = token;
            this.task = task;
            this.tasks = tasks;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: updateTask")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is actual test
                    {
                            "41324231412",
                            new TaskModel(1l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                                    new TaskModel(4l, "new task three", "i think need read about docker", (byte) 1)
                            )),
                            1l
                    },
                    // This is actual test
                    {
                            "123432543245",
                            new TaskModel(2l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            )),
                            2l
                    },
                    // This is actual test
                    {
                            "6758767864566868765",
                            new TaskModel(1l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            1l
                    },
                    // This is actual test
                    {
                            "1111111111",
                            new TaskModel(1l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            1l
                    },
                    // This is doesn't actual test. Token equals null
                    {
                            null,
                            new TaskModel(1l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    },
                    // This is doesn't actual test. Token equals empty
                    {
                            "",
                            new TaskModel(1l, "I think is new task", "I do update this task", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    },
                    // This is doesn't actual test. Task equals empty
                    {
                            "5435235325123",
                            null,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    }
            });
        }

        @Test
        public void fetchTasksTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            if (token != null && token.equals("1111111111")) {
                long actual = cacheManager.updateTask(token, task);
                assertEquals(expected, actual);
            } else {
                cacheManager.addTasks(token, tasks);
                long actual = cacheManager.updateTask(token, task);
                assertEquals(expected, actual);
            }
        }
    }

    //========================================================================
    //== This test case for testing method updateTasks in CacheManager class =
    //========================================================================

    @RunWith(Parameterized.class)
    public static class RemoveTask {

        //This is user token
        private String token;

        //This is task id
        private long id;

        //This is list with tasks
        private List<TaskModel> tasks;

        //This field is task id
        private long expected;

        //This is constructor use for parameterized tests
        public RemoveTask(String token, long id, List<TaskModel> tasks, long expected) {
            this.token = token;
            this.id = id;
            this.tasks = tasks;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: updateTask")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is actual test
                    {
                            "41324231412",
                            4l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1),
                                    new TaskModel(3l, "new task", "i hope its right step", (byte) 1),
                                    new TaskModel(4l, "new task two", "i think need add kafka to this project", (byte) 1),
                                    new TaskModel(5l, "new task three", "i think need read about docker", (byte) 1)
                            )),
                            4l
                    },
                    // This is actual test
                    {
                            "41324231412",
                            2l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            )),
                            2l
                    },
                    // This is actual test
                    {
                            "41324231412",
                            1l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                                    )),
                            1l
                    },
                    {
                            "11111111111",
                            1l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    },
                    // This is doesn't valid test token equals null
                    {
                            null,
                            1l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    },
                    // This is doesn't valid test token equals empty
                    {
                            "",
                            1l,
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                            )),
                            0l
                    }
            });
        }

        @Test
        public void fetchTasksTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            cacheManager.addTasks(token, tasks);
            if (token != null && token.equals("11111111111")) {
                long actual = cacheManager.removeTask("222222222222222", id);
                assertEquals(expected, actual);
            } else {
                long actual = cacheManager.removeTask(token, id);
                assertEquals(expected, actual);
            }
        }
    }


}
