package com.todo.app.client.api.controller;

import com.google.gson.Gson;
import com.todo.app.MainClientApi;
import com.todo.app.config.AppConfig;
import com.todo.app.config.DaoServiceConfig;
import com.todo.app.config.WebsocketSourceConfiguration;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.testing.CreateDataBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebsocketSourceConfiguration.class, AppConfig.class, DaoServiceConfig.class})
@SpringBootTest(classes = MainClientApi.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("debug")
public class RegistrationControllerIT {

    @Autowired
    private IDataSource connection;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    private CreateDataBase createDataBase;

    @Before
    public void setup() {
        this.createDataBase = new CreateDataBase(connection);
        this.createDataBase.createTableUsers();
    }

    @After
    public void unset() {
        this.createDataBase.dropTableUsers();
    }

    @Test
    public void submitRegistrationTest() throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia");
        body.add("email", "vasia@ya.ru");
        body.add("password", "32143214");
        final String baseUrl = "http://localhost:" + randomServerPort + "/registration";
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertNotNull(actual.getResponse());
    }

}