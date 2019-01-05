import com.todo.app.controller.model.user.UserModel;
import com.todo.app.jdbc.dao.data.source.IDataSource;
import com.todo.app.jdbc.dao.data.source.impl.MySqlTestConnection;
import com.todo.app.jdbc.dao.users.IDaoUsers;
import com.todo.app.jdbc.dao.users.impl.DaoUsersImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoUsersImplIT {

    private IDataSource connection = null;
    private CreateDataBase dataBase = null;
    private IDaoUsers users = null;

    @Before
    public void init() {
        connection = new MySqlTestConnection();
        dataBase = new CreateDataBase(connection);
        dataBase.createTableUsers();
        users = new DaoUsersImpl(connection);
    }

    @After
    public void drop() {
        dataBase.dropTableUsers();
    }

    //=========================================================
    //================ CREATE USER TESTS ======================
    //=========================================================

    @Test
    public void createUserManyIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        UserModel model2 = new UserModel(2l, "kolian", "kolian@ya.ru", "32143423", "32142314");
        UserModel model3 = new UserModel(3l, "vasian", "vasian@ya.ru", "32143423", "32142314");
        UserModel model4 = new UserModel(4l, "tupian", "tupian@ya.ru", "32143423", "32142314");
        UserModel model5 = new UserModel(5l, "kazulia", "kazulia@ya.ru", "32143423", "32142314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        UserModel actualModel1 = users.read(model1);
        assertEquals(model1, actualModel1);

        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        UserModel actualModel = users.read(model2);
        assertEquals(model2, actualModel);

        int actual3 = users.create(model3);
        assertTrue(actual3 > 0);
        UserModel actualModel3 = users.read(model3);
        assertEquals(model3, actualModel3);

        int actual4 = users.create(model4);
        assertTrue(actual4 > 0);
        UserModel actualModel4 = users.read(model4);
        assertEquals(model4, actualModel4);

        int actual5 = users.create(model5);
        assertTrue(actual5 > 0);
        UserModel actualModel5 = users.read(model5);
        assertEquals(model5, actualModel5);
    }

    @Test
    public void createUserTwoIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        UserModel actualModel1 = users.read(model1);
        assertEquals(model1, actualModel1);

        UserModel model2 = new UserModel(2l, "pupkin", "pupkin@ya.ru", "32143423", "32142314");
        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        UserModel actualModel2 = users.read(model2);
        assertEquals(model2, actualModel2);
    }

    @Test
    public void createUserOneIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        UserModel actualModel1 = users.read(model1);
        assertEquals(model1, actualModel1);
    }

    @Test
    public void createUserNullIT() {
        UserModel model1 = null;
        int actual1 = users.create(model1);
        assertFalse(actual1 > 0);
        UserModel actualModel1 = users.read(model1);
        assertEquals(model1, actualModel1);
    }

    //=========================================================
    //================ UPDATE USER TESTS ======================
    //=========================================================

    @Test
    public void updateUserManyIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        UserModel model2 = new UserModel(2l, "kolian", "kolian@ya.ru", "32143423", "32142314");
        UserModel model3 = new UserModel(3l, "vasian", "vasian@ya.ru", "32143423", "32142314");
        UserModel model4 = new UserModel(4l, "tupian", "tupian@ya.ru", "32143423", "32142314");
        UserModel model5 = new UserModel(5l, "kazulia", "kazulia@ya.ru", "32143423", "32142314");

        UserModel updateModel1 = new UserModel(1l, "vasia", "vasia@ya.ru", "3214DSAcsad3423", "32142dsacasc314");
        UserModel updateModel2 = new UserModel(2l, "kolianMorian", "kolian@ya.ru", "dasdasd23211d12", "dasdd21321d");
        UserModel updateModel3 = new UserModel(3l, "vasian", "birusian@ya.ru", "dsadDSADdsade12wq", "dsaewqrve32123");
        UserModel updateModel4 = new UserModel(4l, "kiribian", "tupian@ya.ru", "KUSAUEdwqS", "dsae2e1wqe");
        UserModel updateModel5 = new UserModel(5l, "kazulia", "dupka@ya.ru", "qwew3213ewqeq", "AQDWSdCX32142314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.update(updateModel1);
        assertEquals(actual1, actualId1);

        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        int actualId2 = users.update(updateModel2);
        assertEquals(actual2, actualId2);

        int actual3 = users.create(model3);
        assertTrue(actual3 > 0);
        int actualId3 = users.update(updateModel3);
        assertEquals(actual3, actualId3);

        int actual4 = users.create(model4);
        assertTrue(actual4 > 0);
        int actualId4 = users.update(updateModel4);
        assertEquals(actual4, actualId4);

        int actual5 = users.create(model5);
        assertTrue(actual5 > 0);
        int actualId5 = users.update(updateModel5);
        assertEquals(actual5, actualId5);
    }

    @Test
    public void updateUserTwoIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        UserModel model2 = new UserModel(2l, "kolian", "kolian@ya.ru", "32143423", "32142314");

        UserModel updateModel1 = new UserModel(1l, "vasia", "vasia@ya.ru", "3214DSAcsad3423", "32142dsacasc314");
        UserModel updateModel2 = new UserModel(2l, "kolianMorian", "kolian@ya.ru", "dasdasd23211d12", "dasdd21321d");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.update(updateModel1);
        assertEquals(actual1, actualId1);

        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        int actualId2 = users.update(updateModel2);
        assertEquals(actual2, actualId2);
    }

    @Test
    public void updateUserOneIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        UserModel updateModel1 = new UserModel(1l, "vasia", "vasia@ya.ru", "3214DSAcsad3423", "32142dsacasc314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.update(updateModel1);
        assertEquals(actual1, actualId1);
    }

    @Test
    public void updateUserNullIT() {
        UserModel updateModel1 = null;
        int actualId1 = users.update(updateModel1);
        assertEquals(0, actualId1);
    }

    //=========================================================
    //================ DELETE USER TESTS ======================
    //=========================================================

    @Test
    public void deleteUserManyIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        UserModel model2 = new UserModel(2l, "kolian", "kolian@ya.ru", "32143423", "32142314");
        UserModel model3 = new UserModel(3l, "vasian", "vasian@ya.ru", "32143423", "32142314");
        UserModel model4 = new UserModel(4l, "tupian", "tupian@ya.ru", "32143423", "32142314");
        UserModel model5 = new UserModel(5l, "kazulia", "kazulia@ya.ru", "32143423", "32142314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.delete(model1);
        assertEquals(actual1, actualId1);

        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        int actualId2 = users.delete(model2);
        assertEquals(actual2, actualId2);

        int actual3 = users.create(model3);
        assertTrue(actual3 > 0);
        int actualId3 = users.delete(model3);
        assertEquals(actual3, actualId3);

        int actual4 = users.create(model4);
        assertTrue(actual4 > 0);
        int actualId4 = users.delete(model4);
        assertEquals(actual4, actualId4);

        int actual5 = users.create(model5);
        assertTrue(actual5 > 0);
        int actualId5 = users.delete(model5);
        assertEquals(actual5, actualId5);
    }

    @Test
    public void deleteUserTwoIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");
        UserModel model2 = new UserModel(2l, "pupkin", "pupkin@ya.ru", "32143423", "32142314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.delete(model1);
        assertEquals(actual1, actualId1);

        int actual2 = users.create(model2);
        assertTrue(actual2 > 0);
        int actualId2 = users.delete(model2);
        assertEquals(actual2, actualId2);
    }

    @Test
    public void deleteUserOneIT() {
        UserModel model1 = new UserModel(1l, "vasia", "vasia@ya.ru", "32143423", "32142314");

        int actual1 = users.create(model1);
        assertTrue(actual1 > 0);
        int actualId1 = users.delete(model1);
        assertEquals(actual1, actualId1);
    }

    @Test
    public void deleteUserNullIT() {
        UserModel model1 = null;
        int actual1 = users.create(model1);
        assertFalse(actual1 > 0);
        int actualId1 = users.delete(model1);
        assertEquals(actual1, actualId1);
    }

}
