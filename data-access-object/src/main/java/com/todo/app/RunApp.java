package com.todo.app;

import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlConnection;
import com.todo.app.jdbc.dao.data.source.impl.MySqlTestConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@SpringBootApplication
public class RunApp {

    @Autowired
    private IDataSource my;

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }

    @PostConstruct
    public void show() {
        try {
            my.getConnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
