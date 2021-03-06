/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.services;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoUserInterface;
import epam.railway.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * @author denis
 */
public class UserService {

    private final DaoUserInterface daoUser = DaoFactory.getDaoUser();
    private static UserService instance = new UserService();
    private static final Logger log = LogManager.getLogger(UserService.class.getName());

    private UserService(){}

    public static UserService getInstance() {
        return instance;
    }


    public User findUser(final String email, final String password){
        User user = null;
        if (!password.isEmpty() && !email.isEmpty()) {
            user = daoUser.findByEmailAndPassword(email, password);
        }
        return user;
    }

    public User findUser(final Integer userId){
        return daoUser.findById(userId);
    }

    public void blockUser(Integer userId){
        DaoFactory.getDaoUser().blockById(userId);
    }

    public void unlockUser(Integer userId){
        DaoFactory.getDaoUser().unblockById(userId);
    }

    public void updateUser(User user){
        daoUser.update(user);
    }

    public List<User> findNotAdminUsers() {
        return daoUser.findAllNotAdmin();
    }
    
    
}
