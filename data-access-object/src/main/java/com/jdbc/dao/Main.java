package com.jdbc.dao;

import com.helper.controller.model.task.Task;
import com.jdbc.dao.data.source.IDataSource;
import com.jdbc.dao.data.source.impl.MySqlConnection;
import com.jdbc.dao.tasks.IDaoTasks;
import com.jdbc.dao.tasks.impl.DaoTasksImpl;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        IDataSource source = new MySqlConnection();
        IDaoTasks tasks = new DaoTasksImpl(source);
        Task task = new Task("task test", "do test task", (byte) 1, "vasia");
        //int id = tasks.create(task);
        //System.out.println(id);
        //List<Task> taskList = tasks.read(task);
        //System.out.println(Arrays.toString(taskList.toArray()));
    }

}