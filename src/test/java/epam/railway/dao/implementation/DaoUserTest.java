package epam.railway.dao.implementation;


import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoUserInterface;
import epam.railway.entities.User;
import epam.railway.manager.connectionpool.mysql.ConnectionPool;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by denis on 27.01.17.
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class DaoUserTest {

    private static final Integer ID = 10;
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Den";
    private static final String LAST_NAME = "Kochubey";
    private Connection mockConnection;
    private PreparedStatement mockPreparedStmnt;
    private ResultSet mockResultSet;

    @Before
    public void setupMock() {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStmnt = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() {
    }





    @After
    public void tearDown() {
    }


    @Test
    public void findByEmailWithExistedEmail() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        boolean actual = userDao.findByEmail(EMAIL);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertTrue(actual);
    }

    @Test
    public void findByEmailWithUnexistedEmail() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        boolean actual = userDao.findByEmail(EMAIL);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertFalse(actual);
    }

    @Test
    public void findByEmailAndPasswordNotAdminNotBlocked() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(anyString())).thenReturn(10, 0, 0);
        when(mockResultSet.getString(anyString())).thenReturn("Den", "Kochubey", "den@gmail.com", "password");

        User userExpected = new User();

        userExpected.setId(10);
        userExpected.setAdminid(0);
        userExpected.setFirstname("Den");
        userExpected.setLastname("Kochubey");
        userExpected.setEmail("den@gmail.com");
        userExpected.setIsBlocked(0);
        userExpected.setPassword("password");

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        User userActual = userDao.findByEmailAndPassword(EMAIL, PASSWORD);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(2)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(4)).getString(anyString());
        verify(mockResultSet, times(3)).getInt(anyString());

        assertEquals(userExpected, userActual);
    }

    @Test
    public void findByEmailAndPasswordNotExistedUser() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        User userActual = userDao.findByEmailAndPassword(EMAIL, PASSWORD);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(2)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertNull(userActual);
    }


    @Test
    public void findByIdNotAdminNotBlocked() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(anyString())).thenReturn(10, 0, 0);
        when(mockResultSet.getString(anyString())).thenReturn("Den", "Kochubey", "den@gmail.com", "password");

        User userExpected = new User();

        userExpected.setId(10);
        userExpected.setAdminid(0);
        userExpected.setFirstname("Den");
        userExpected.setLastname("Kochubey");
        userExpected.setEmail("den@gmail.com");
        userExpected.setIsBlocked(0);
        userExpected.setPassword("password");

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        User userActual = userDao.findById(ID);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(4)).getString(anyString());
        verify(mockResultSet, times(3)).getInt(anyString());

        assertEquals(userExpected, userActual);
    }

    @Test
    public void findByIdNotExistedUser() throws SQLException, NamingException {

        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        User userActual = userDao.findById(ID);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertNull(userActual);
    }


    @Test
    public void findByEmailAndPassword() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void addUser() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        userDao.addUser(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(4)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeUpdate();

    }

    @Test
    public void findAllNotAdminZeroUsers() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        List<User> users = userDao.findAllNotAdmin();

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertTrue(users.isEmpty());
    }

    @Test
    public void findAllNotAdminFerUsers() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        List<User> users = userDao.findAllNotAdmin();

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();

        assertEquals(users.size(), 2);
    }

    @Test
    public void blockById() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        userDao.blockById(ID);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).execute();
    }

    @Test
    public void unblockById() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoUserInterface userDao = DaoFactory.getDaoUser();
        userDao.blockById(ID);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).execute();
    }

    @Test
    public void update() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        User userExpected = new User();

        userExpected.setId(10);
        userExpected.setAdminid(0);
        userExpected.setFirstname("Den");
        userExpected.setLastname("Kochubey");
        userExpected.setEmail("den@gmail.com");
        userExpected.setIsBlocked(0);
        userExpected.setPassword("password");
        DaoUserInterface userDao = DaoFactory.getDaoUser();
        userDao.update(userExpected);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(5)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeUpdate();
    }

}