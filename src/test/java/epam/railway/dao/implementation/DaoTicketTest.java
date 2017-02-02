package epam.railway.dao.implementation;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoTicketInterface;
import epam.railway.entities.Ticket;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for DaoTicket
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class DaoTicketTest {

    private static final Integer TRAIN_NUMBER = 6;
    private static final Timestamp DATE = new Timestamp(1000);
    private static final Timestamp END_DATE = new Timestamp(2000);
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
    public void addTicket() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setUserid(10);
        ticket.setUserName("Denis");
        ticket.setUserLastName("Kochubey");
        ticket.setStartDateTime(DATE);
        ticket.setEndDateTime(END_DATE);
        ticket.setPrice(100.0);
        ticket.setTrainNumber(TRAIN_NUMBER);
        ticket.setDepartureCity("Kiev");
        ticket.setDestinationCity("Moscow");
        ticket.setReturnStatus(0);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        daoTicket.addTicket(ticket);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setDouble(anyInt(), anyDouble());
        verify(mockPreparedStmnt, times(4)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeUpdate();
    }

    @Test
    public void findByTicketid() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        Ticket ticket = daoTicket.findByTicketid(1);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertNotNull(ticket);
    }

    @Test
    public void findByTicketidNotExistedTicket() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        Ticket ticket = daoTicket.findByTicketid(1);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertNull(ticket);
    }

    @Test
    public void findByUseridNoTickets() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        List<Ticket> tickets = daoTicket.findByUserid(1, true);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertTrue(tickets.isEmpty());
    }

    @Test
    public void findByUseridFewTickets() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        List<Ticket> tickets = daoTicket.findByUserid(1, true);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();

        assertEquals(tickets.size(), 2);
    }

    @Test
    public void findByReturnStatusNoTickets() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        List<Ticket> tickets = daoTicket.findByReturnStatus();

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();

        assertTrue(tickets.isEmpty());
    }

    @Test
    public void deleteByTicketId() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        daoTicket.deleteByTicketId(1);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).execute();
    }

    @Test
    public void returnByTicketId() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        PowerMockito.when(ConnectionPool.createConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
        daoTicket.returnByTicketId(0, 1);

        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).execute();
    }

}