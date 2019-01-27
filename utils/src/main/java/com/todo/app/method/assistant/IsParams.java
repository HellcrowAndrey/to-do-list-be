package com.todo.app.method.assistant;

import com.todo.app.controller.model.task.TaskModel;

import java.util.List;

/**
 * Tha class IsParams encapsulate methods witch do check params on null or empty.
 * All methods in this class are static boolean and return true if all params
 * valid or false if at least one params doesn't valid.
 */
public class IsParams {

    /**
     * This method do check on valid token or TaskModel. Do return false if
     * param token equals null or empty and if object TaskModel equals null.
     *
     * @param token its user token use if need do authorization in this server.
     * @param model its object which keeps info about task.
     * @return true if params valid or false if doesn't valid.
     */
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

    public static boolean isParams(String data, List<TaskModel> array) {
        if (!isParams(data) || array == null) {
            return false;
        }
        return true;
    }

}
