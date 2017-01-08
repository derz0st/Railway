package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoStationInterface;
import epam.railway.entities.Station;
import epam.railway.manager.Neo4jConnectionPool;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by denis on 30.12.16.
 */
public class DaoStation implements DaoStationInterface{

    private static DaoStation instance;
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss DD");
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
        List<Station> stationlist = null;
        try {

            Connection con = Neo4jConnectionPool.getInstance().retrieve();
            String query = FIND_STATIONS_BY_TRAIN_ID;

            PreparedStatement stmt = con.prepareStatement(query);
            stmt = con.prepareStatement(query);
            stmt.setInt(1, trainNumber);
            ResultSet rs2 = stmt.executeQuery();
            stationlist = new ArrayList<>();
            Station next;

            while(rs2.next()){
                if(stationlist.size() > 0){
                    Station prev = stationlist.get(stationlist.size()-1);
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
                    stationlist.add(station);
                }
                    Station station = new Station();
                    station.setName(rs2.getString("arname"));
                    station.setArrivalTime(new Timestamp(formatter.parse(rs2.getString("rel.arrival")).getTime()));
                    station.setPriceToNext(0.0);
                    stationlist.add(station);
                }

        } catch (/*InstantiationException | IllegalAccessException | ClassNotFoundException |*/ SQLException | ParseException ex) {
            Logger.getLogger(DaoTrainNeo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stationlist;
    }
}
