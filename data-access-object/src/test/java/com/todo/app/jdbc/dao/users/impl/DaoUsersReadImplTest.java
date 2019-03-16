package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.data.source.impl.MySqlConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MySqlConnection.class, DaoUsersImpl.class})
@PowerMockIgnore({"java.sql.*"})
public class DaoUsersReadImplTest {

    @Mock
    private MySqlConnection dataSourceMock;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement statementMock;

    @Mock
    private ResultSet resultSetMock;

    private UserDaoModel model;

    @Before
    public void init() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        assertNotNull(dataSourceMock);
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenReturn(connectionMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        byte[] hash = new byte[]{1, 2, 3, 4, 5, 6};
        byte[] salt = new byte[]{'r', 'q', 'y', 'u', 'i'};
        model = new UserDaoModel();
        model.setIdUser(1l);
        model.setLogin("login");
        model.setEmail("email@gmail.com");
        model.setHash(hash);
        model.setSalt(salt);
        model.setToken("token");
        model.setEnable(true);

        when(resultSetMock.getLong(1)).thenReturn(1l);
        when(resultSetMock.getString(2)).thenReturn("login");
        when(resultSetMock.getString(3)).thenReturn("email@gmail.com");
        when(resultSetMock.getBytes(4)).thenReturn(hash);
        when(resultSetMock.getBytes(5)).thenReturn(salt);
        when(resultSetMock.getString(6)).thenReturn("token");
        when(resultSetMock.getBoolean(7)).thenReturn(true);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void readTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("login","email");
        assertEquals(expected, model);
    }

    @Test
    public void readNullTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read(null, null);
        assertEquals(expected, null);
    }

    @Test
    public void readEmptyTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("", "");
        assertEquals(expected, null);
    }

    @Test
    public void readClassNotFoundExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(ClassNotFoundException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("login","email");
        UserDaoModel actual = new UserDaoModel();
        assertEquals(expected, actual);
    }

    @Test
    public void readIllegalAccessExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(IllegalAccessException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("login","email");
        UserDaoModel actual = new UserDaoModel();
        assertEquals(expected, actual);
    }

    @Test
    public void readInstantiationExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(InstantiationException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("login", "email");
        UserDaoModel actual = new UserDaoModel();
        assertEquals(expected, actual);
    }

    @Test
    public void readSQLExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(SQLException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        UserDaoModel expected = daoUsers.read("login","email");
        UserDaoModel actual = new UserDaoModel();
        assertEquals(expected, actual);
    }

}