package com.todo.app.jdbc.dao.users.impl;

import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.jdbc.dao.testing.CreateDataBase;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlTestConnection;
import com.todo.app.jdbc.dao.users.IDaoUsers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySqlTestConnection.class)
@ActiveProfiles("integration-test")
public class DaoUsersImplIT {

    @Autowired
    private IDataSource connection;

    private CreateDataBase dataBase = null;

    private IDaoUsers users = null;

    private List<UserDaoModel> usersMockFive;

    private List<UserDaoModel> usersMockTwo;

    private List<UserDaoModel> usersMockOne;

    private List<UserDaoModel> updateUsersMockFive;

    private List<UserDaoModel> updateUsersMockTwo;

    private List<UserDaoModel> updateUsersMockOne;

    /*
        This method do create user mock.
     */
    private void createUserMock() {
        final UserDaoModel model1 = new UserDaoModel();
        model1.setIdUser(1l);
        model1.setLogin("login1");
        model1.setEmail("email1@gemail.com");
        model1.setHash(new byte[]{'1', '2', '3'});
        model1.setSalt(new byte[]{'1', '2', '3'});
        model1.setToken("token1");
        model1.setEnable(true);

        final UserDaoModel model2 = new UserDaoModel();
        model2.setIdUser(2l);
        model2.setLogin("login2");
        model2.setEmail("email2@gemail.com");
        model2.setHash(new byte[]{'1', '2', '3'});
        model2.setSalt(new byte[]{'1', '2', '3'});
        model2.setToken("token2");
        model2.setEnable(true);

        final UserDaoModel model3 = new UserDaoModel();
        model3.setIdUser(3l);
        model3.setLogin("login3");
        model3.setEmail("email3@gemail.com");
        model3.setHash(new byte[]{'1', '2', '3'});
        model3.setSalt(new byte[]{'1', '2', '3'});
        model3.setToken("token3");
        model3.setEnable(true);

        final UserDaoModel model4 = new UserDaoModel();
        model4.setIdUser(4l);
        model4.setLogin("login4");
        model4.setEmail("email4@gemail.com");
        model4.setHash(new byte[]{'1', '2', '3'});
        model4.setSalt(new byte[]{'1', '2', '3'});
        model4.setToken("token4");
        model4.setEnable(true);

        final UserDaoModel model5 = new UserDaoModel();
        model5.setIdUser(5l);
        model5.setLogin("login5");
        model5.setEmail("email5@gemail.com");
        model5.setHash(new byte[]{'1', '2', '3'});
        model5.setSalt(new byte[]{'1', '2', '3'});
        model5.setToken("token5");
        model5.setEnable(true);

        final List<UserDaoModel> listFive = new ArrayList<>();
        listFive.add(model1);
        listFive.add(model2);
        listFive.add(model3);
        listFive.add(model4);
        listFive.add(model5);
        this.usersMockFive = listFive;

        final List<UserDaoModel> listTwo = new ArrayList<>();
        listTwo.add(model1);
        listTwo.add(model2);

        this.usersMockTwo = listTwo;

        List<UserDaoModel> listOne = new ArrayList<>();
        listOne.add(model1);

        this.usersMockOne = listOne;
    }

    /*
        This method do create mock for update.
     */
    private void updateUserMock() {
        final UserDaoModel model1 = new UserDaoModel();
        model1.setIdUser(1l);
        model1.setLogin("login11");
        model1.setEmail("email1@gemail.com");
        model1.setHash(new byte[]{'1', '2', '3'});
        model1.setSalt(new byte[]{'1', '2', '3'});
        model1.setToken("token11");
        model1.setEnable(false);

        final UserDaoModel model2 = new UserDaoModel();
        model2.setIdUser(2l);
        model2.setLogin("login22");
        model2.setEmail("email2@gemail.com");
        model2.setHash(new byte[]{'1', '2', '3'});
        model2.setSalt(new byte[]{'1', '2', '3'});
        model2.setToken("token22");
        model2.setEnable(false);

        final UserDaoModel model3 = new UserDaoModel();
        model3.setIdUser(3l);
        model3.setLogin("login33");
        model3.setEmail("email3@gemail.com");
        model3.setHash(new byte[]{'1', '2', '3'});
        model3.setSalt(new byte[]{'1', '2', '3'});
        model3.setToken("token33");
        model3.setEnable(false);

        final UserDaoModel model4 = new UserDaoModel();
        model4.setIdUser(4l);
        model4.setLogin("login44");
        model4.setEmail("email4@gemail.com");
        model4.setHash(new byte[]{'1', '2', '3'});
        model4.setSalt(new byte[]{'1', '2', '3'});
        model4.setToken("token44");
        model4.setEnable(false);

        final UserDaoModel model5 = new UserDaoModel();
        model5.setIdUser(5l);
        model5.setLogin("login55");
        model5.setEmail("email5@gemail.com");
        model5.setHash(new byte[]{'1', '2', '3'});
        model5.setSalt(new byte[]{'1', '2', '3'});
        model5.setToken("token55");
        model5.setEnable(false);

        final List<UserDaoModel> listFive = new ArrayList<>();
        listFive.add(model1);
        listFive.add(model2);
        listFive.add(model3);
        listFive.add(model4);
        listFive.add(model5);
        this.updateUsersMockFive = listFive;

        final List<UserDaoModel> listTwo = new ArrayList<>();
        listTwo.add(model1);
        listTwo.add(model2);

        this.updateUsersMockTwo = listTwo;

        final List<UserDaoModel> listOne = new ArrayList<>();
        listOne.add(model1);

        this.updateUsersMockOne = listOne;
    }

    /*
        This method do create tables in db.
     */
    @Before
    public void init() {
        this.dataBase = new CreateDataBase(connection);
        this.dataBase.createTableUsers();
        this.dataBase.createTableTasks();
        this.users = new DaoUsersImpl(connection);
        createUserMock();
        updateUserMock();
    }

    /*
        This method do drop tables.
     */
    @After
    public void drop() {
        this.dataBase.dropTableTasks();
        this.dataBase.dropTableUsers();
    }

    //=========================================================
    //================ CREATE USER TESTS ======================
    //=========================================================

    /*
        This test on many users in db.
     */
    @Test
    public void createUserManyIT() {
        usersMockFive.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            user.setIdUser(actual1);
            UserDaoModel actualModel = users.read(user.getLogin(), user.getEmail());
            assertEquals(user, actualModel);
        });
    }

    /*
        This test on two users in db.
     */
    @Test
    public void createUserTwoIT() {
        usersMockTwo.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            user.setIdUser(actual1);
            UserDaoModel actualModel = users.read(user.getLogin(), user.getEmail());
            assertEquals(user, actualModel);
        });
    }

    /*
        This test on one user in db.
     */
    @Test
    public void createUserOneIT() {
        usersMockOne.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            user.setIdUser(actual1);
            UserDaoModel actualModel = users.read(user.getLogin(), user.getEmail());
            assertEquals(user, actualModel);
        });
    }

    /*
        This test on null.
     */
    @Test
    public void createUserNullIT() {
        UserDaoModel model = null;
        long actual1 = users.create(model);
        assertFalse(actual1 > 0);
    }

    //=========================================================
    //================ UPDATE USER TESTS ======================
    //=========================================================

    /*
        This test on update many user in db.
     */
    @Test
    public void updateUserManyIT() {
        for (int i = 0; i < usersMockFive.size(); i++) {
            long actual = users.create(usersMockFive.get(i));
            assertTrue(actual > 0);
            long actualId1 = users.update(updateUsersMockFive.get(i));
            assertTrue(actualId1 > 0);
        }
    }

    /*
        This test on update two user in db.
     */
    @Test
    public void updateUserTwoIT() {
        for (int i = 0; i < usersMockTwo.size(); i++) {
            long actual = users.create(usersMockTwo.get(i));
            assertTrue(actual > 0);
            long actualId1 = users.update(updateUsersMockTwo.get(i));
            assertTrue(actualId1 > 0);
        }
    }

    /*
        This test on update one user in db.
     */
    @Test
    public void updateUserOneIT() {
        for (int i = 0; i < usersMockOne.size(); i++) {
            long actual = users.create(usersMockOne.get(i));
            assertTrue(actual > 0);
            long actualId1 = users.update(updateUsersMockOne.get(i));
            assertTrue(actualId1 > 0);
        }
    }

    /*
        This test on null user in db.
     */
    @Test
    public void updateUserNullIT() {
        UserDaoModel updateModel1 = null;
        long actualId1 = users.update(updateModel1);
        assertEquals(0, actualId1);
    }

    //=========================================================
    //================ DELETE USER TESTS ======================
    //=========================================================

    /*
        This test on delete many users in db.
     */
    @Test
    public void deleteUserManyIT() {
        usersMockFive.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            long actualId1 = users.delete(user.getEmail());
            assertTrue(actualId1 > 0);
        });
    }

    /*
        This test on delete two users in db.
     */
    @Test
    public void deleteUserTwoIT() {
        usersMockTwo.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            long actualId1 = users.delete(user.getEmail());
            assertTrue(actualId1 > 0);
        });
    }

    /*
        This test on delete one users in db
     */
    @Test
    public void deleteUserOneIT() {
        usersMockOne.stream().forEach((user) -> {
            long actual1 = users.create(user);
            assertTrue(actual1 > 0);
            long actualId1 = users.delete(user.getEmail());
            assertTrue(actualId1 > 0);
        });
    }

    /*
        This test on null.
     */
    @Test
    public void deleteUserNullIT() {
        UserDaoModel model1 = null;
        long actual1 = users.create(model1);
        assertFalse(actual1 > 0);
        long actualId1 = users.delete("");
        assertTrue(actualId1 == 0);
    }

}
