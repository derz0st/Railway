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
 * Created by denis on 30.12.16.
 */
public class DaoStation implements DaoStationInterface{

    private static DaoStation instance;
    private static final Logger commandLogger = LogManager.getLogger(DaoStation.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss DD");
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
            ResultSet rs2 = stmt.executeQuery();
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
        } catch (SQLException | ParseException ex) {
            commandLogger.error("Find stations error: " + ex.getMessage());
        }
        return stationList;
    }
}
