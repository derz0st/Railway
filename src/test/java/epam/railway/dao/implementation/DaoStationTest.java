package epam.railway.dao.implementation;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoStationInterface;
import epam.railway.entities.Station;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnection;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by denis on 01.02.17.
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(Neo4jConnectionPool.class)
public class DaoStationTest {

    private Neo4jConnectionPool mockConnectionPool;
    private Neo4jConnection mockConnection;
    private PreparedStatement mockPreparedStmnt;
    private ResultSet mockResultSet;

    @Before
    public void setupMock() {
        mockConnectionPool = Mockito.mock(Neo4jConnectionPool.class);
        mockConnection = Mockito.mock(Neo4jConnection.class);
        mockPreparedStmnt = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
    }


    @Test
    public void findByTrainNumberNotExistedStations() throws Exception {
        PowerMockito.mockStatic(Neo4jConnectionPool.class);
        PowerMockito.when(Neo4jConnectionPool.getInstance()).thenReturn(mockConnectionPool);

        when(mockConnectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        DaoStationInterface daoStation = DaoFactory.getDaoStation();
        List<Station> stations = daoStation.findByTrainNumber(1);

        assertTrue(stations.isEmpty());
    }

    @Test
    public void findByTrainNumberFewStations() throws Exception {
        PowerMockito.mockStatic(Neo4jConnectionPool.class);
        PowerMockito.when(Neo4jConnectionPool.getInstance()).thenReturn(mockConnectionPool);

        when(mockConnectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);

        DaoStationInterface daoStation = DaoFactory.getDaoStation();
        List<Station> stations = daoStation.findByTrainNumber(1);

        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).executeQuery();
    }

}