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
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MySqlConnection.class, DaoUsersImpl.class})
@PowerMockIgnore({"java.sql.*"})
public class DaoUsersUpdateImplTest {

    @Mock
    private MySqlConnection dataSourceMock;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement statementMock;

    private UserDaoModel model;

    @Before
    public void init() throws SQLException, IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        assertNotNull(dataSourceMock);
        when(connectionMock.prepareStatement(any(String.class),
                any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString()))
                .thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);
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
    }

    @Test
    public void updateTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(model);
        assertEquals(expected, 1);
    }

    @Test
    public void updateNullTest() {
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(null);
        assertEquals(expected, 0);
    }

    @Test
    public void updateIllegalAccessExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(IllegalAccessException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(model);
        assertEquals(expected, 0);
    }

    @Test
    public void updateInstantiationExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(InstantiationException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(model);
        assertEquals(expected, 0);
    }

    @Test
    public void updateSQLExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(SQLException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(model);
        assertEquals(expected, 0);
    }

    @Test
    public void updateClassNotFoundExceptionTest() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        when(connectionMock.prepareStatement(any(String.class), any(Integer.class))).thenReturn(statementMock);
        when(dataSourceMock.getConnect()).thenThrow(ClassNotFoundException.class);
        DaoUsersImpl daoUsers = new DaoUsersImpl(dataSourceMock);
        long expected = daoUsers.update(model);
        assertEquals(expected, 0);
    }

}