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
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MySqlConnection.class, DaoUsersImpl.class})
@PowerMockIgnore({"java.sql.*"})
public class DaoUsersDeleteImplTest {

    @Mock
    private MySqlConnection dataSourceMock;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement statementMock;

    private UserDaoModel model;

    @Before
    public void init() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        assertNotNull(dataSourceMock);
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenReturn(connectionMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        model = new UserDaoModel();
        model.setIdUser(1l);
        model.setLogin("login");
        model.setEmail("email@gmail.com");
        model.setPasswordHash("password_hash");
        model.setSalt("salt");
        model.setToken("token");
        model.setEnable(true);
    }

    @Test
    public void deleteTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("email@gemail.com");
        assertEquals(expected, 1);
    }

    @Test
    public void deleteNullTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete(null);
        assertEquals(expected, 0);
    }

    @Test
    public void deleteEmptyTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("");
        assertEquals(expected, 0);
    }

    @Test
    public void deleteClassNotFoundExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(ClassNotFoundException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("email@gemail.com");
        assertEquals(expected, 0);
    }

    @Test
    public void deleteIllegalAccessExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(IllegalAccessException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("email@gemail.com");
        assertEquals(expected, 0);
    }

    @Test
    public void deleteInstantiationExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(InstantiationException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("email@gemail.com");
        assertEquals(expected, 0);
    }

    @Test
    public void deleteSQLExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(SQLException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.delete("email@gemail.com");
        assertEquals(expected, 0);
    }

}