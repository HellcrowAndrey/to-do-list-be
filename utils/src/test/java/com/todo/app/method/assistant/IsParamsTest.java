package com.todo.app.method.assistant;

import com.todo.app.controller.model.task.TaskModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IsParamsTest.IsParamsTokenAndTaskModel.class,
        IsParamsTest.IsParamsSingleString.class
})
public class IsParamsTest {

    //==========================================================
    //========== This test case for testing method       =======
    //========== isParams(String token, TaskModel model) =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsParamsTokenAndTaskModel {

        // This field is user token
        private String token;

        // This field is model of user task
        private TaskModel task;

        // This field keeps suppose test result
        private boolean expected;

        // This is parameterised constructor
        public IsParamsTokenAndTaskModel(String token, TaskModel task, boolean expected) {
            this.token = token;
            this.task = task;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: isParams token and TaskModel")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is actual test
                    {
                            "token",
                            new TaskModel(1l, "first task", "do test this method", (byte) 1),
                            true
                    },
                    //Token equals null
                    {
                            null,
                            new TaskModel(1l, "first task", "do test this method", (byte) 1),
                            false
                    },
                    //Token equals empty line
                    {
                            "",
                            new TaskModel(1l, "first task", "do test this method", (byte) 1),
                            false
                    },
                    // TaskModel equals null
                    {
                            "token",
                            null,
                            false
                    },
            });
        }

        @Test
        public void isParamsTest() {
            boolean actual = IsParams.isParams(token, task);
            assertEquals(expected, actual);
        }

    }

    //==========================================================
    //========== This test case for testing method       =======
    //==========        isParams(String data)            =======
    //==========================================================

    @RunWith(Parameterized.class)
    public static class IsParamsSingleString {

        // Method isParams received this data
        private String data;

        // This field is suppose test result
        private boolean expected;

        public IsParamsSingleString(String data, boolean expected) {
            this.data = data;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}: fetchTasks")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // This is actual test case
                    {
                            "data", true
                    },
                    // Data equals null
                    {
                            null, false
                    },
                    // Data equals empty
                    {
                            "", false
                    }
            });
        }

        @Test
        public void fetchTasksTest() {
            boolean actual = IsParams.isParams(data);
            assertEquals(expected, actual);
        }

    }

}