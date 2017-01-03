/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import java.io.IOException;
import epam.railway.dao.daofactory.DaoFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epam.railway.manager.Config;

/**
 *
 * @author denis
 */
public class CommandSignUp implements ICommand{
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeatpassword";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        String firstname = request.getParameter(FIRST_NAME);
        String lastname = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);     
        String password = request.getParameter(PASSWORD);
        String repeatpassword = request.getParameter(REPEAT_PASSWORD);
        
        if (password.equals(repeatpassword)) {
            DaoFactory.getDaoUser().addUser(firstname, lastname, email, password);
            page = Config.getInstance().getProperty(Config.LOGIN);
        } else {
            request.setAttribute("error", "Пароли не совпадают");
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        return page;
    }
    
}
