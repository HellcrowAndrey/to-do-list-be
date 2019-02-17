package com.todo.app.client.api.controller;

import com.google.gson.Gson;
import com.todo.app.MainClientApi;
import com.todo.app.config.AppConfig;
import com.todo.app.config.DaoServiceConfig;
import com.todo.app.config.SourceConfiguration;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.testing.CreateDataBase;
import com.todo.app.service.users.IServiceUsers;
import com.todo.app.service.users.impl.ServiceUsersImpl;
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

import java.net.InetAddress;
import java.net.URI;

import static com.todo.app.controller.constant.ControllerUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SourceConfiguration.class,
        AppConfig.class, DaoServiceConfig.class})
@SpringBootTest(classes = MainClientApi.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("debug")
public class RegistrationControllerIT {

    /*
        This field is configuration data source.
     */
    @Autowired
    private IDataSource connection;

    /*
        This field is use for testing registration controller
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /*
        This field use for keeps random port.
     */
    @LocalServerPort
    private int RANDOM_PORT;

    /*
        This field keeps exemplar class CreateDataBase.
        Methods in this class execute create db and delete.
     */
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

    /*
        This test case execute create new user in db.
     */
    @Test
    public void submitRegistrationCreateNewUserTest() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia");
        body.add("email", "vasia@ya.ru");
        body.add("password", "32143214");
        final String baseUrl = "http://" + address.getHostName() + ":" + RANDOM_PORT + REGISTRATION;
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertNotNull(actual.getResponse());
    }

    /*
        This test case testing on not valid params.
        User login.
     */
    @Test
    public void submitRegistrationNotValidParamsOneTest() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia@\\");
        body.add("email", "vasia@ya.ru");
        body.add("password", "32143214");
        final String baseUrl = "http://" + address.getHostName() + ":" + RANDOM_PORT + REGISTRATION;
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case testing on not valid params.
        User email.
     */
    @Test
    public void submitRegistrationNotValidParamsTwoTest() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia");
        body.add("email", "vasia@");
        body.add("password", "32143214");
        final String baseUrl = "http://" + address.getHostName() + ":" + RANDOM_PORT + REGISTRATION;
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case testing on not valid params.
        User password.
    */
    @Test
    public void submitRegistrationNotValidParamsThreTest() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia");
        body.add("email", "vasia@");
        body.add("password", "***@%");
        final String baseUrl = "http://" + address.getHostName() + ":" + RANDOM_PORT + REGISTRATION;
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertEquals(IS_NOT_VALID_PARAMS, actual.getResponse());
    }

    /*
        This test case testing on not user exist in db.
    */
    @Test
    public void submitRegistrationExistInDbTest() throws Exception {
        final IServiceUsers serviceUsers = new ServiceUsersImpl(connection);
        final UserDaoModel userDaoModel = new UserDaoModel();
        userDaoModel.setLogin("vasia");
        userDaoModel.setEmail("vasia@gmail.ru");
        userDaoModel.setHash(new byte[]{1, 2, 3});
        userDaoModel.setSalt(new byte[]{4, 5, 6});
        userDaoModel.setToken("token_for_test");
        userDaoModel.setEnable(true);
        serviceUsers.create(userDaoModel);
        InetAddress address = InetAddress.getLocalHost();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", "vasia");
        body.add("email", "vasia@gmail.ru");
        body.add("password", "12342134");
        final String baseUrl = "http://" + address.getHostName() + ":" + RANDOM_PORT + REGISTRATION;
        final URI uri = new URI(baseUrl);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        final Gson gson = new Gson();
        final ResponseModel<String> actual = gson.fromJson(responseEntity.getBody(), ResponseModel.class);
        assertEquals(USER_EXIT, actual.getResponse());
    }

}