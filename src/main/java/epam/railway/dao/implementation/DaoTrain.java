/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoTrainInterface;
import epam.railway.entities.Train;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnection;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class DaoTrain implements DaoTrainInterface {

    private static DaoTrain instance;
    private static final Logger commandLogger = LogManager.getLogger(DaoTrain.class);

    private static final String FIND_TRAINS_BETWEEN_STATIONS = 
                "MATCH (start: Station {name: {1}})-[g: GOES*]->(end: Station {name: {2}})\n"
                + "return distinct g[0].trainnumber as trainnumber";
    private static final String FIND_ALL_TRAIN_NUMBERS = "MATCH ()-[d: GOES]->() RETURN DISTINCT d.trainnumber";
    
    private DaoTrain(){}
    
    public static DaoTrain getInstance() {
        if (instance == null) {
            instance = new DaoTrain();
        }
        return instance;
    }


    @Override
    public List<Train> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity) {
        List<Train> trainList = new ArrayList<>();

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = FIND_TRAINS_BETWEEN_STATIONS;
            
            try (PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, departureCity);
                stmt.setString(2, destinationCity);

                try (ResultSet rs = stmt.executeQuery()) {

                    Train train;
                    while (rs.next()) {

                        train = new Train();
                        train.setId(rs.getInt("trainnumber"));
                        train.setNumber(rs.getInt("trainnumber"));

                        trainList.add(train);

                    }
                }
            }
        } catch ( SQLException ex ) {
            commandLogger.error("Find train error: " + ex.getMessage());
        }
        return trainList;
    }


    @Override
    public List<Integer> getAllTrainNumbers(){
        List<Integer> trainNumbers = new ArrayList<>();

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            String query = FIND_ALL_TRAIN_NUMBERS;

            try (PreparedStatement stmt = con.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    trainNumbers.add(rs.getInt("d.trainnumber"));
                }
            }

        } catch ( SQLException ex ) {
            commandLogger.error("Get all trains numbers error: " + ex.getMessage());
        }
        return trainNumbers;
    }
    
}
