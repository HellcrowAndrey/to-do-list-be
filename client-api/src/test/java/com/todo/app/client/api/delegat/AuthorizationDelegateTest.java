package com.todo.app.client.api.delegat;

import com.todo.app.cache.manager.CacheManager;
import com.todo.app.controller.model.response.ResponseModel;
import com.todo.app.controller.model.task.TaskModel;
import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.password.IPasswords;
import com.todo.app.password.impl.PasswordsImpl;
import com.todo.app.service.tasks.IServiceTasks;
import com.todo.app.service.users.IServiceUsers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.todo.app.constant.ToDoConstant.MOCK_CONSTANT;
import static com.todo.app.controller.constant.ControllerUtils.*;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        IServiceUsers.class, IServiceTasks.class, AuthorizationDelegate.class,
        IPasswords.class, PasswordsImpl.class
})
public class AuthorizationDelegateTest {

    /*
        This field is mock dao interface.
        Which do crud with user data.
     */
    @Mock
    private IServiceUsers serviceUsersMock;

    /*
        This field is mock dao interface.
        Which do crud with user task.
     */
    @Mock
    private IServiceTasks serviceTasksMock;

    /*
        This field keeps info about user in db
     */
    private UserDaoModel userDaoModel;

    /*
        This field keeps info about user which
        received in controller.
     */
    private UserModel userModelWithEmail;

    /*
        This field keeps info about user which
        received in controller.
     */
    private UserModel userModelWithLogin;

    /*
        This field keeps user token
     */
    private final String token = "token";

    /*
        This field keeps password
     */
    private final String password = "2311Adw";

    @Before
    public void setup() {
        // Create UserDaoModel which keeps in db.
        IPasswords passwords = new PasswordsImpl();
        byte[] salt = passwords.getSalt64();
        byte[] hash = passwords.hash(password, salt);
        this.userDaoModel = new UserDaoModel();
        this.userDaoModel.setIdUser(1l);
        this.userDaoModel.setLogin("login");
        this.userDaoModel.setEmail("email@gmail.com");
        this.userDaoModel.setHash(hash);
        this.userDaoModel.setSalt(salt);
        this.userDaoModel.setToken("token");
        this.userDaoModel.setEnable(true);
        // Create UserModel which keeps in controller. With email.
        this.userModelWithEmail = new UserModel();
        this.userModelWithEmail.setEmail("email@gmail.com");
        this.userModelWithEmail.setPassword(password);
        // Create UserModel which keeps in controller. With login.
        this.userModelWithLogin = new UserModel();
        this.userModelWithLogin.setLogin("login");
        this.userModelWithLogin.setPassword(password);
    }

    @After
    public void upset() {
        CacheManager manager = CacheManager.getInstance();
        manager.removeTask(this.token, 1l);
        manager.removeTask(this.token, 2l);
        manager.removeTask(this.token, 3l);
        manager.removeTask(this.token, 4l);
        manager.removeTask(this.token, 5l);
    }

    //==============================================================
    //============= Tests on doStuffCache(userToken) method ========
    //==============================================================

    /*
        This test case doStuffCache. In CacheManage tasks exist many.
     */
    @Test
    public void doStuffCacheTaskExistManyTest() throws Exception {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        cacheManager.addTasks(token, tasks);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "doStuffCache", token);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
       This test case doStuffCache. In CacheManage tasks exist two.
    */
    @Test
    public void doStuffCacheTaskExistTwoTest() throws Exception {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1)
        ));
        cacheManager.addTasks(token, tasks);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "doStuffCache", token);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
       This test case doStuffCache. In CacheManage tasks exist one.
    */
    @Test
    public void doStuffCacheTaskExistOneTest() throws Exception {
        final CacheManager cacheManager = CacheManager.getInstance();
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1)
        ));
        cacheManager.addTasks(token, tasks);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "doStuffCache", token);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
       This test case doStuffCache. In CacheManage tasks exist zero.
       By method do call to db.
    */
    @Test
    public void doStuffCacheTaskExistZeroTest() throws Exception {
        final List<TaskModel> tasks = new ArrayList<>(Arrays.asList(
                new TaskModel(1l, "name1", "task1", (byte) 1),
                new TaskModel(2l, "name2", "task2", (byte) 1),
                new TaskModel(3l, "name3", "task3", (byte) 1),
                new TaskModel(4l, "name4", "task4", (byte) 1),
                new TaskModel(5l, "name5", "task5", (byte) 1)
        ));
        when(serviceTasksMock.read(token)).thenReturn(tasks);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "doStuffCache", token);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    //==============================================================
    //====== Tests on matchesPassword(password, result) method =====
    //==============================================================

    /*
        This test case is actual user data.
     */
    @Test
    public void matchesPasswordActualTest() throws Exception {
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "matchesPassword", password, userDaoModel);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
        This test case is not actual user data.
     */
    @Test
    public void matchesPasswordNotActualTest() throws Exception {
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = Whitebox
                .invokeMethod(delegate, "matchesPassword", "12343", userDaoModel);
        assertEquals(USER_AUTHORIZATION_FAILURE, actual.getResponse());
    }

    //==============================================================
    //============= Tests on submitAuth(user) method ===============
    //==============================================================

    /*
        This test case is actual test on submitAuth method.
     */
    @Test
    public void submitAuthWithEmailTest() {
        when(serviceUsersMock.read(MOCK_CONSTANT,
                userModelWithEmail.getEmail())).thenReturn(userDaoModel);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithEmail);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
        This test case is failure test on submitAuth method.
        User not found in db.
     */
    @Test
    public void submitAuthWithEmailFailureUserNotFoundTest() {
        when(serviceUsersMock.read(MOCK_CONSTANT,
                userModelWithEmail.getEmail())).thenReturn(new UserDaoModel());
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithEmail);
        assertEquals(USER_NOT_FOUNT, actual.getResponse());
    }

    /*
        This test case is failure test on submitAuth method.
     */
    @Test
    public void submitAuthWithEmailFailureTest() {
        userModelWithEmail.setPassword("2313123");
        when(serviceUsersMock.read(MOCK_CONSTANT,
                userModelWithEmail.getEmail())).thenReturn(userDaoModel);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithEmail);
        assertEquals(USER_AUTHORIZATION_FAILURE, actual.getResponse());
    }

    /*
       This test case is actual test on submitAuth method.
    */
    @Test
    public void submitAuthWithLoginTest() {
        when(serviceUsersMock.read(userModelWithLogin.getLogin(),
                MOCK_CONSTANT)).thenReturn(userDaoModel);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithLogin);
        assertEquals(USER_AUTHORIZATION_SUCCESS, actual.getResponse());
    }

    /*
        This test case is actual test on submitAuth method.
    */
    @Test
    public void submitAuthWithLoginUserNotFoundTest() {
        when(serviceUsersMock.read(userModelWithLogin.getLogin(),
                MOCK_CONSTANT)).thenReturn(new UserDaoModel());
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithLogin);
        assertEquals(USER_NOT_FOUNT, actual.getResponse());
    }

    /*
        This test case is failure test on submitAuth method.
     */
    @Test
    public void submitAuthWithLoginFailureTest() {
        userModelWithLogin.setPassword("2313123");
        when(serviceUsersMock.read(userModelWithLogin.getLogin(),
                MOCK_CONSTANT)).thenReturn(userDaoModel);
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelWithLogin);
        assertEquals(USER_AUTHORIZATION_FAILURE, actual.getResponse());
    }

    /*
        This test case is failure test on submitAuth method.
        UserModel has invalid login and empty email.
     */
    @Test
    public void submitAuthUserDataInvalidTest() {
        final UserModel userModelInvalid = new UserModel();
        userModelInvalid.setLogin("log@]]\\in");
        userModelInvalid.setPassword("1234231");
        final AuthorizationDelegate delegate =
                new AuthorizationDelegate(serviceUsersMock, serviceTasksMock);
        final ResponseModel<String> actual = delegate.submitAuth(userModelInvalid);
        assertEquals(INCORRECT_DATA, actual.getResponse());
    }

}