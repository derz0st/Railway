package epam.railway.dao.implementation;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoTrainTicketsOnDateInterface;
import epam.railway.entities.TrainTicketsOnDate;
import epam.railway.manager.connectionpool.mysql.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by denis on 01.02.17.
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class DaoTrainTicketsOnDateTest {

    private static final Integer TRAIN_NUMBER = 6;
    private static final Timestamp DATE = new Timestamp(1000);
    private static final Integer TOTAL_SEATS = 10;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStmnt;
    private ResultSet mockResultSet;

    @Before
    public void setupMock() {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStmnt = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
    }


    @Test
    public void addTrainOnDate() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);


        DaoTrainTicketsOnDateInterface daoTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();
        daoTicketsOnDate.addTrainOnDate(TRAIN_NUMBER, DATE, TOTAL_SEATS);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setTimestamp(2, DATE);
        verify(mockPreparedStmnt, times(1)).execute();

    }

    @Test
    public void findByTrainNumberAndDate() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, TRAIN_NUMBER, 0, TOTAL_SEATS);
        when(mockResultSet.getTimestamp(anyInt())).thenReturn(DATE);

        TrainTicketsOnDate expected = new TrainTicketsOnDate();
        expected.setId(1);
        expected.setTrainNumber(TRAIN_NUMBER);
        expected.setBusySeats(0);
        expected.setTotalSeats(TOTAL_SEATS);

        DaoTrainTicketsOnDateInterface daoTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();
        TrainTicketsOnDate actual = daoTicketsOnDate.findByTrainNumberAndDate(TRAIN_NUMBER, DATE);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();

        assertEquals(expected, actual);
    }

    @Test
    public void incBusySeatsByTrainNumberAndDate() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoTrainTicketsOnDateInterface daoTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();
        daoTicketsOnDate.incBusySeatsByTrainNumberAndDate(TRAIN_NUMBER, DATE);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setTimestamp(2, DATE);
        verify(mockPreparedStmnt, times(1)).executeUpdate();
    }

    @Test
    public void descBusySeatsByTrainNumberAndDate() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoTrainTicketsOnDateInterface daoTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();
        daoTicketsOnDate.descBusySeatsByTrainNumberAndDate(TRAIN_NUMBER, DATE);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setTimestamp(2, DATE);
        verify(mockPreparedStmnt, times(1)).executeUpdate();
    }

}