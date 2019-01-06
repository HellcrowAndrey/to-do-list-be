package com.todo.app.jdbc;

import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlConnection;
import com.todo.app.jdbc.dao.data.source.impl.MySqlTestConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
public class DaoConfig {

//    @Bean
//    @Profile({"realise", "default"})
//    public IDataSource getConfigRealise() {
//        return new MySqlConnection();
//    }
//
//    @Bean
//    @Profile("integration-tests")
//    public IDataSource getConfigIT() {
//        return new MySqlTestConnection();
//    }
}
