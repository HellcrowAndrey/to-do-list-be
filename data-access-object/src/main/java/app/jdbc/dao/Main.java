package app.jdbc.dao;

import com.todo.app.controller.model.task.Task;
import app.jdbc.dao.tasks.IDaoTasks;
import app.jdbc.dao.tasks.impl.DaoTasksImpl;
import app.jdbc.dao.data.source.IDataSource;
import app.jdbc.dao.data.source.impl.MySqlConnection;

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