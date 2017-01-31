/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.service;

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

    private DaoUserInterface daoUser = DaoFactory.getDaoUser();
    private static UserService instance;
    private static final Logger log = LogManager.getLogger(UserService.class.getName());

    private UserService(){}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
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
        User user = daoUser.findById(userId);
        return user;
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
    
//    public static void deleteUser(final Integer userId, final Integer adminId) {
//
//        User admin = DaoFactory.getDaoUser().findById(adminId);
//
//        if (admin.getAdminid().equals(1)){
//            log.info("Admin [id: " + admin.getAdminid() +  "] remove user [id" + userId + "]");
//            DaoFactory.getDaoUser().deleteById(userId);
//        } else {
//            log.error("NotAdmin user [id: " + admin.getAdminid() +  "] tried to remove user [id" + userId + "]");
//            throw new SecurityException(Message.getInstance().getProperty(Message.PERMISSION_DENIED));
//        }
//
//    }
    
    public List<User> findNotAdminUsers() {
            List<User> users = daoUser.findAllNotAdmin();
            return users;
    }
    
    
}
