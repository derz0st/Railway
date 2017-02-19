package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoStationInterface;
import epam.railway.entities.Station;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnection;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Neo4j implementation of DaoStationInterface
 */
public class DaoStation implements DaoStationInterface{

    private static DaoStation instance;
    private static final Logger commandLogger = LogManager.getLogger(DaoStation.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss DD");
    private static final String FIND_STATION_BY_NAME = "MATCH (st: Station {name: {1}}) return st.name";
    private static final String ADD_NEW_STATION = "CREATE (st: Station {name: {1}})";
    private static final String ADD_NEW_LINK_BETWEEN_STATIONS =
            "MATCH (start: Station {name: {1}}), (end: Station {name: {2}}) CREATE (start)-[:GOES {trainnumber: {3}, departure: {4}, arrival: {5}, price: {6}, order: {7}}]->(end)";
    private static final String FIND_STATIONS_BY_TRAIN_ID = "match (n)-[rel: GOES {trainnumber: {1}}]->(m)\n"
            + "return distinct n.name as name, m.name as arname, rel.order, rel.departure, \n"
            + "rel.trainnumber, rel.arrival, rel.price\n" +
            "order by rel.order";

    private DaoStation(){}

    public static DaoStation getInstance() {
        if (instance == null) {
            instance = new DaoStation();
        }
        return instance;
    }

    @Override
    public List<Station> findByTrainNumber(Integer trainNumber) {
        List<Station> stationList = null;
        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = FIND_STATIONS_BY_TRAIN_ID;
            try (PreparedStatement stmt = con.prepareStatement(query)){

                stmt.setInt(1, trainNumber);

                try (ResultSet rs2 = stmt.executeQuery()) {

                    stationList = new ArrayList<>();

                    while(rs2.next()){
                        if(stationList.size() > 0){
                            Station prev = stationList.get(stationList.size()-1);
                            prev.setDepartureTime(new Timestamp(formatter.parse(rs2.getString("rel.departure")).getTime()));
                            prev.setPriceToNext(rs2.getDouble("rel.price"));
                            prev.setTrainNumber(rs2.getInt("rel.trainnumber"));
                        }else{
                            Station station = new Station();
                            station.setName(rs2.getString("name"));
                            station.setDepartureTime(new Timestamp(formatter.parse(rs2.getString("rel.departure")).getTime()));
                            station.setArrivalTime(new Timestamp(formatter.parse(rs2.getString("rel.departure")).getTime()));
                            station.setPriceToNext(rs2.getDouble("rel.price"));
                            station.setTrainNumber(rs2.getInt("rel.trainnumber"));
                            stationList.add(station);
                        }
                        Station station = new Station();
                        station.setName(rs2.getString("arname"));
                        station.setArrivalTime(new Timestamp(formatter.parse(rs2.getString("rel.arrival")).getTime()));
                        station.setPriceToNext(0.0);
                        stationList.add(station);
                    }
                }
            }
        } catch (SQLException | ParseException ex) {
            commandLogger.error("Find stations error: " + ex.getMessage());
        }
        return stationList;
    }

    @Override
    public Station findByName(String name) {
        Station station = null;
        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = FIND_STATION_BY_NAME;
            try (PreparedStatement stmt = con.prepareStatement(query)){

                stmt.setString(1, name);

                try (ResultSet rs2 = stmt.executeQuery()) {

                    if (rs2.next()){
                        station = new Station();
                        station.setName(rs2.getString("st.name"));
                    }

                }
            }
        } catch (SQLException ex) {
            commandLogger.error("Find stations error: " + ex.getMessage());
        }
        return station;
    }

    @Override
    public void addNewStation(String name) {

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = ADD_NEW_STATION;

            try (PreparedStatement stmt = con.prepareStatement(query)){
                stmt.setString(1, name);
                stmt.execute();
            }

        } catch (SQLException ex) {
            commandLogger.error("Find stations error: " + ex.getMessage());
        }

    }

    //@Override
    public void addNewLinkBetweenStations(String departureStation, String destinationStation, Integer trainNumber,
                                          String departureTime, String destinationTime, Double price, Integer order) {

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = ADD_NEW_LINK_BETWEEN_STATIONS;

            try (PreparedStatement stmt = con.prepareStatement(query)){
                stmt.setString(1, departureStation);
                stmt.setString(2, destinationStation);
                stmt.setInt(3, trainNumber);
                stmt.setString(4, departureTime);
                stmt.setString(5, destinationTime);
                stmt.setDouble(6, price);
                stmt.setInt(7, order);
                stmt.execute();
            }

        } catch (SQLException ex) {
            commandLogger.error("Find stations error: " + ex.getMessage());
        }
    }

}
