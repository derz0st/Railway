/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoUserInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import epam.railway.entities.User;
import epam.railway.manager.ConnectionPool;
import org.apache.log4j.Logger;

/**
 *
 * @author denis
 */
public class DaoUser implements DaoUserInterface {

    private static DaoUser instance;
    private static final Logger log = Logger.getLogger(DaoTrain.class.getName());
    
    private DaoUser(){}
    
    public static DaoUser getInstance() {
        if (instance == null) {
            instance = new DaoUser();
        }
        return instance;
    }
    
    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?")) {
                
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
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
                    
            }
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        return user;
    }
    
    @Override
    public User findById(Integer id) {
        User user = null;       
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id = ?")) {
                
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
                    return user;
                }
            }
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        return user;
    }
    
    @Override
    public void addUser(String firstname, String lastname, String email, String password) {     
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (firstname, lastname, email, password) VALUES (?,?,?,?)")) {
                
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
                
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public List<User> findAllNotAdmin() {
        List<User> list = new ArrayList();
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE adminid IS NULL"); 
            ResultSet resultSet = preparedStatement.executeQuery()) {
                
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAdminid(resultSet.getInt("adminid"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
            }
                
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id=?")) {
            
            preparedStatement.setString(1, id.toString());
            preparedStatement.execute();
            
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void update(User user) {
        
        try (Connection connection = ConnectionPool.createConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET "
                + "firstname = ?, "
                + "lastname = ?, "
                + "email = ?, "
                + "password = ? "
                + "WHERE "
                + "id = ?")) {
                    
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getId().toString());
                
            preparedStatement.executeUpdate();
                
        } catch (NamingException | SQLException ex) {
            log.error(ex.getMessage());
        }
        
    }



}
