/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.User;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author denis
 */
public class CommandSettings implements ICommand{
    private static final String USER_ID = "userid";
    private static final String USER = "user";
    private static final String LANGUAGE = "language";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession(false);
        Integer id = (Integer)session.getAttribute(USER_ID);
        
        User user = DaoFactory.getDaoUser().findById(id);
        
        request.setAttribute(USER, user);
        request.setAttribute(LANGUAGE, (String)session.getAttribute(LANGUAGE));
        page = Config.getInstance().getProperty(Config.SETTINGS);
        return page;
    }
}
