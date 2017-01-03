/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.User;
import epam.railway.manager.Message;
import java.util.List;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Logger;

/**
 *
 * @author denis
 */
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class.getName());
    
    public static User findUser(final String email, final String password) throws LoginException{
        
        if (!password.isEmpty() && !email.isEmpty()) {
            User user = DaoFactory.getDaoUser().findByEmailAndPassword(email, password);
            if (user != null) {
                return user;
            } else {
                throw new LoginException(Message.getInstance().getProperty(Message.LOGIN_ERROR));
            }
        } else {
            throw new LoginException(Message.getInstance().getProperty(Message.INPUT_ERROR));
        }
    }
   
    
    public static void deleteUser(final Integer userId, final Integer adminId) {
        
        User admin = DaoFactory.getDaoUser().findById(adminId);
        
        if (admin.getAdminid().equals(1)){
            log.info("Admin [id: " + admin.getAdminid() +  "] remove user [id" + userId + "]");
            DaoFactory.getDaoUser().deleteById(userId);
        } else {
            log.error("NotAdmin user [id: " + admin.getAdminid() +  "] tried to remove user [id" + userId + "]");
            throw new SecurityException(Message.getInstance().getProperty(Message.PERMISSION_DENIED));
        }
        
    }
    
    public static List<User> findNotAdminUsers(final Boolean isAdmin) {
        if (isAdmin != null){
            List<User> users = DaoFactory.getDaoUser().findAllNotAdmin();
            return users;
        } else {
            throw new SecurityException(Message.getInstance().getProperty(Message.PERMISSION_DENIED));
        }
        
    }
    
    
}
