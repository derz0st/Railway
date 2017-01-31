package epam.railway.dao.implementation;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by denis on 27.01.17.
 */
public class DaoUserTest {

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;


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
    public void findByEmail() throws SQLException, NamingException {
       /* mock(ConnectionPool.class);

        when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        DaoUser instance = DaoFactory.getDaoUser();
        boolean actual = instance.findByEmail("den@gmail.com");

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStmnt, times(6)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).execute();
        verify(mockConnection, times(1)).commit();
        verify(mockResultSet, times(2)).next();
*/
    }

    @Test
    public void findByEmailAndPassword() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void addUser() throws Exception {

    }

    @Test
    public void findAllNotAdmin() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void blockById() throws Exception {

    }

    @Test
    public void unblockById() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

}