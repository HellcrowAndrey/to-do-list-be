package com.todo.app.cache.manager;

import com.todo.app.controller.model.task.TaskModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

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
        private List<TaskModel> expectedCache;

        //This constructor use for parameterized tests
        public AddTask(String token, TaskModel task,
                       List<TaskModel> expectedCache) {
            this.token = token;
            this.task = task;
            this.expectedCache = expectedCache;
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
                            ))
                    },
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                                    new TaskModel(2l, "second task", "do write many tests", (byte) 1)
                            ))
                    },
                    {
                            "1231232134123",
                            new TaskModel(1l, "first task", "do finish of this test", (byte) 1),
                            new ArrayList<>(Arrays.asList(
                                    new TaskModel(1l, "first task", "do finish of this test", (byte) 1)
                                    ))
                    },
                    {
                            null,
                            null,
                            null
                    },
                    {
                            "",
                            "",
                            null
                    }
            });
        }

        @Test
        public void addTaskTest() {
            CacheManager cacheManager = CacheManager.getInstance();
            long actualId = cacheManager.addTask(token, task);

        }

    }

    @RunWith(Parameterized.class)
    public static class FetchTask {

        private String token;
        private List<TaskModel> tasks;
        private Map<String, List<TaskModel>> expectedCache;

        public FetchTask(String token, List<TaskModel> tasks) {
            this.token = token;
            this.tasks = tasks;
        }

        @Parameterized.Parameters(name = "{index}: fetchTasks")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{

            });
        }

        @Test
        public void fetchTasksTest() {

        }

    }
}