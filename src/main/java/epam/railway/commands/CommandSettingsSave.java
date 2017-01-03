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
public class CommandSettingsSave implements ICommand{
    private static final String USER = "user";
    private static final String USER_ID = "userid";
    private static final String USER_EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String LANGUAGE = "language";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);
        
        Integer id = (Integer)session.getAttribute(USER_ID);
        String language = (String)session.getAttribute(LANGUAGE);
        
        User user = DaoFactory.getDaoUser().findById(id);
        user.setEmail(request.getParameter(USER_EMAIL));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstname(request.getParameter(FIRST_NAME));
        user.setLastname(request.getParameter(LAST_NAME));
        
        DaoFactory.getDaoUser().update(user);
        
        session.setAttribute(LANGUAGE, request.getParameter(LANGUAGE));

        request.setAttribute(USER, user);
        request.setAttribute(LANGUAGE, language);
        page = Config.getInstance().getProperty(Config.SETTINGS);
        return page;
    }
}
