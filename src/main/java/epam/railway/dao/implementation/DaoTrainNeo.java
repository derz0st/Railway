/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoTrainNeoInterface;
import epam.railway.entities.TrainNeo;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnection;
import epam.railway.manager.connectionpool.neo4j.Neo4jConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class DaoTrainNeo implements DaoTrainNeoInterface{

    private static DaoTrainNeo instance;
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private static final String FIND_TRAINS_BETWEEN_STATIONS = 
                "MATCH (start: Station {name: {1}})-[g: GOES*]->(end: Station {name: {2}})\n"
                + "return distinct g[0].trainnumber as trainnumber";
    private static final String FIND_ALL_TRAIN_NUMBERS = "MATCH ()-[d: GOES]->() RETURN DISTINCT d.trainnumber";
    
    
    //private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DaoTrainNeo.class.getName());
    
    private DaoTrainNeo(){}
    
    public static DaoTrainNeo getInstance() {
        if (instance == null) {
            instance = new DaoTrainNeo();
        }
        return instance;
    }
    
    @Override
    public List<TrainNeo> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity) {
        List<TrainNeo> trainlist = new ArrayList<>();

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            //Connection con = Neo4jConnectionPool.getInstance().retrieve();
            String query = FIND_TRAINS_BETWEEN_STATIONS;
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            stmt.setString(1, departureCity);
            stmt.setString(2, destinationCity);
            ResultSet rs = stmt.executeQuery();
            TrainNeo train = null;
            
            while (rs.next()) {

                train = new TrainNeo();
                train.setId(rs.getInt("trainnumber"));
                train.setNumber(rs.getInt("trainnumber"));

                trainlist.add(train);
                
                System.out.print(train.getStations());
            }
        } catch ( SQLException ex ) {
            Logger.getLogger(DaoTrainNeo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainlist;
    }

    public List<Integer> getAllTrainNumbers(){
        List<Integer> trainNumbers = new ArrayList<>();

        try (Neo4jConnection con = Neo4jConnectionPool.getInstance().getConnection()){

            //Connection con = Neo4jConnectionPool.getInstance().retrieve();
            String query = FIND_ALL_TRAIN_NUMBERS;

            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trainNumbers.add(rs.getInt("d.trainnumber"));
            }

        } catch ( SQLException ex ) {
            Logger.getLogger(DaoTrainNeo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainNumbers;
    }
    
}
