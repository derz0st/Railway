/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoTrainInterface;
import epam.railway.entities.Train;
import epam.railway.manager.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denis
 */
public class DaoTrain implements DaoTrainInterface{

    private static DaoTrain instance;
    private static final Logger log = LogManager.getLogger(DaoTrain.class.getName());
    
    private DaoTrain(){}
    
    public static DaoTrain getInstance() {
        if (instance == null) {
            instance = new DaoTrain();
        }
        return instance;
    }
    
    @Override
    public List findByDeparturecityAndDestinationcity(String departureCity, String destinationCity, String date) {
        List<Train> list = new ArrayList();
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM train WHERE departurecity = ? AND destinationcity = ?")) {
                
            preparedStatement.setString(1, departureCity);
            preparedStatement.setString(2, destinationCity);
                
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    
                while(resultSet.next()){
                        
                    Train train = new Train();
                        
                    train.setId(resultSet.getInt("id"));
                    train.setDeparturecity(resultSet.getString("departurecity"));
                    train.setDestinationcity(resultSet.getString("destinationcity"));
                    train.setDeparturetime(formatter.parse(resultSet.getString("departuretime")));
                    train.setArrivaltime(formatter.parse(resultSet.getString("arrivaltime")));
                    train.setTraveltime(new Date(train.getArrivaltime().getTime() - train.getDeparturetime().getTime() - 10800000));
                    train.setPrice(resultSet.getDouble("price"));
                    train.setTotalseats(resultSet.getInt("totalseats"));
                    //train.setFreeseats(train.getTotalseats() - DaoFactory.getDaoTicket().getBusySeatsOnDate(train.getId(), date));
                    list.add(train);
                        
                }
            } 
        } catch (NamingException | SQLException | ParseException ex) {
            log.error(ex.getMessage());
        }
        return list;
    }
    
    @Override
    public Train findById(Integer id) {
        Train train = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM train WHERE id = ?")) {
                
            preparedStatement.setString(1, id.toString());
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                train =  new Train();
                
                if(resultSet.next()){
                    train.setId(resultSet.getInt("id"));
                    train.setDeparturecity(resultSet.getString("departurecity"));
                    train.setDestinationcity(resultSet.getString("destinationcity"));
                    train.setDeparturetime(formatter.parse(resultSet.getString("departuretime")));
                    train.setArrivaltime(formatter.parse(resultSet.getString("arrivaltime")));
                    train.setTraveltime(new Date(train.getArrivaltime().getTime() - train.getDeparturetime().getTime() - 10800000));
                    train.setPrice(resultSet.getDouble("price"));
                }
            } 
        } catch (NamingException | ParseException | SQLException ex) {
            log.error(ex.getMessage());
        }
        return train;
    }
  
}
