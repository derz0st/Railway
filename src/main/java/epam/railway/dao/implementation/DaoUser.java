/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoUserInterface;
import epam.railway.entities.User;
import epam.railway.manager.connectionpool.mysql.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class DaoUser implements DaoUserInterface {

    private static DaoUser instance;
    private static final Logger commandLogger = LogManager.getLogger(DaoTrainTicketsOnDate.class);
    private static final String QUERY_FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String QUERY_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String QUERY_INSERT_USER = "INSERT INTO user (firstname, lastname, email, password) VALUES (?,?,?,?)";
    private static final String QUERY_FIND_NOT_ADMIN_USERS = "SELECT * FROM user WHERE adminid = 0";
    private static final String QUERY_BLOCK_BY_ID = "UPDATE user SET is_blocked = 1 WHERE id = ?";
    private static final String QUERY_UNLOCK_BY_ID = "UPDATE user SET is_blocked = 0 WHERE id = ?";
    private static final String QUERY_UPDATE_USER = "UPDATE user SET firstname = ?, lastname = ?, email = ?, password = ? WHERE id = ?";

    private DaoUser(){}
    
    public static DaoUser getInstance() {
        if (instance == null) {
            instance = new DaoUser();
        }
        return instance;
    }


    @Override
    public boolean findByEmail(String email) {

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_EMAIL)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return true;
                }
            }
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Find user error: " + ex.getMessage());
        }
        return false;
    }


    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = null;

        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_EMAIL_AND_PASSWORD)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    
                if(resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setAdminid(resultSet.getInt("adminid"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setIsBlocked(resultSet.getInt("is_blocked"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
                    
            }
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Find user error: " + ex.getMessage());
        }
        return user;
    }


    @Override
    public User findById(Integer id) {
        User user = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_ID)) {
                
            preparedStatement.setString(1, id.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setAdminid(resultSet.getInt("adminid"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setIsBlocked(resultSet.getInt("is_blocked"));
                    return user;
                }
            }
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Find user error: " + ex.getMessage());
        }
        return user;
    }


    @Override
    public void addUser(String firstname, String lastname, String email, String password) {     
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_USER)) {
                
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
                
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Add user error: " + ex.getMessage());
        }
    }


    @Override
    public List<User> findAllNotAdmin() {
        List<User> list = new ArrayList<>();
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_NOT_ADMIN_USERS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
                
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAdminid(resultSet.getInt("adminid"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setIsBlocked(resultSet.getInt("is_blocked"));
                list.add(user);
            }
                
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Find not admin users error: " + ex.getMessage());
        }
        return list;
    }


    @Override
    public void blockById(Integer id) {

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BLOCK_BY_ID)) {

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (NamingException | SQLException ex) {
            commandLogger.error("Block user error: " + ex.getMessage());
        }
    }


    @Override
    public void unblockById(Integer id) {

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UNLOCK_BY_ID)) {

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (NamingException | SQLException ex) {
            commandLogger.error("Unlock user error: " + ex.getMessage());
        }
    }


    @Override
    public void update(User user) {
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_USER)) {
                    
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getId().toString());
                
            preparedStatement.executeUpdate();
                
        } catch (NamingException | SQLException ex) {
            commandLogger.error("Update user error: " + ex.getMessage());
        }
        
    }



}
