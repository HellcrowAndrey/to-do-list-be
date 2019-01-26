package com.todo.app.method.assistant;

import com.todo.app.controller.model.task.TaskModel;

public class IsParams {

    public static boolean isParams(String token, TaskModel model) {
        if (token == null || token.equals("") || model == null) {
            return false;
        }
        return true;
    }

    public static boolean isParams(String data) {
        if (data == null || data.equals("")) {
            return false;
        }
        return true;
    }

}
