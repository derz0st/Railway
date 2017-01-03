/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.service.UserService;

/**
 *
 * @author denis
 */
public class CommandDeleteUser implements ICommand{
    private static final String USER_ID = "userid";
    private static final String ERROR = "error";
    private static final String USERS = "users";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        Integer userId = Integer.parseInt(request.getParameter(USER_ID));

        HttpSession session = request.getSession(false);
        Integer sessionUserId = (Integer) session.getAttribute(USER_ID);
        
        try {
            UserService.deleteUser(userId, sessionUserId);
        
            List<User> users = DaoFactory.getDaoUser().findAllNotAdmin();
            request.setAttribute(USERS, users);
            page = Config.getInstance().getProperty(Config.ADMINISTRATE);
            
        } catch (SecurityException ex){
            request.setAttribute(ERROR, ex.getMessage());
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        
        return page;
    }
}
