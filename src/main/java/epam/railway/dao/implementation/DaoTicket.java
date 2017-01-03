/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoTicketInterface;
import epam.railway.entities.Ticket;
import epam.railway.manager.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class DaoTicket implements DaoTicketInterface{
    
    private static DaoTicket instance;
    private static final Logger log = LogManager.getLogger(DaoTicket.class.getName());
    private static final String QUERY_ADD_TICKET =
            "INSERT INTO tiket_test (user_id, user_name, user_last_name, start_date_time, end_date_time, price," +
                    "train_number, departure_city, destination_city) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String QUERY_SELECT_ACTUAL_TICKETS_BY_USER_ID =
            "SELECT * FROM tiket_test WHERE user_id = ? AND start_date_time > ?";
    private static final String QUERY_SELECT_ARCHIVE_TICKETS_BY_USER_ID =
            "SELECT * FROM tiket_test WHERE user_id = ? AND start_date_time < ?";
    
    private DaoTicket(){}
    
    public static DaoTicket getInstance() {
        if (instance == null) {
            instance = new DaoTicket();
        }
        return instance;
    }
    
    @Override
    public void addTicket(Ticket ticket) {

        try (Connection connection = ConnectionPool.createConnection()) {
            PreparedStatement preparedStatement = null;
            
            try {    

                preparedStatement = connection.prepareStatement(QUERY_ADD_TICKET);
                preparedStatement.setInt(1, ticket.getUserid());
                preparedStatement.setString(2, ticket.getUserName());
                preparedStatement.setString(3, ticket.getUserLastName());
                preparedStatement.setTimestamp(4, ticket.getStartDateTime());
                preparedStatement.setTimestamp(5, ticket.getEndDateTime());
                preparedStatement.setDouble(6, ticket.getPrice());
                preparedStatement.setInt(7, ticket.getTrainNumber());
                preparedStatement.setString(8, ticket.getDepartureCity());
                preparedStatement.setString(9, ticket.getDestinationCity());
                preparedStatement.executeUpdate();
                
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
            
        } catch (NamingException | SQLException ex) {
            System.out.println("Ошибка" + ex.getMessage());
        }
    }
    
/*
    @Override
    public Ticket findById(Integer id) {
        Ticket ticket = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            
            preparedStatement.setString(1, id.toString());
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ticket = new Ticket();
                
                if(resultSet.next()){
                    ticket.setId(resultSet.getInt("id"));
                    ticket.setUserid(resultSet.getInt("userid"));
                    ticket.setTrainid(resultSet.getInt("trainid"));
                    ticket.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                }
            }
            
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        return ticket;
    }*/


    public List<Ticket> findByUserid(Integer userId, Boolean actual) {
        List<Ticket> list = new ArrayList();
        String query;
        if (actual) {
            query = QUERY_SELECT_ACTUAL_TICKETS_BY_USER_ID;
        } else {
            query = QUERY_SELECT_ARCHIVE_TICKETS_BY_USER_ID;
        }

        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());

            preparedStatement.setString(1, userId.toString());
            preparedStatement.setTimestamp(2, now);
                
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Ticket ticket;
                while(resultSet.next()){
                    ticket = new Ticket();
                    ticket.setId(resultSet.getInt("id"));
                    ticket.setUserid(resultSet.getInt("user_id"));
                    ticket.setUserName(resultSet.getString("user_name"));
                    ticket.setUserLastName(resultSet.getString("user_last_name"));
                    ticket.setStartDateTime(resultSet.getTimestamp("start_date_time"));
                    ticket.setEndDateTime(resultSet.getTimestamp("end_date_time"));
                    ticket.setPrice(resultSet.getDouble("price"));
                    ticket.setTrainNumber(resultSet.getInt("train_number"));
                    ticket.setDepartureCity(resultSet.getString("departure_city"));
                    ticket.setDestinationCity(resultSet.getString("destination_city"));

                    list.add(ticket);
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("Ошибка");
        }
        return list;
    }
    /*
    @Override
    public void deleteById(Integer id) {
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ticket WHERE id=?")) {
                
            preparedStatement.setString(1, id.toString());
            preparedStatement.execute();
                
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        
    }

    @Override
    public Integer getBusySeatsOnDate(Integer trainId, String dateString) {
        Integer busySeats = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = 
            connection.prepareStatement("SELECT COUNT(*) FROM ticket WHERE trainid = ? and date = ?")) {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateString);
            
            preparedStatement.setString(1, trainId.toString());
            preparedStatement.setString(2, formatter.format(date));
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                if(resultSet.next()){
                    busySeats = resultSet.getInt(1);
                }
            }
            
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DaoTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return busySeats;
    }*/
    
    

}
