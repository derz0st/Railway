/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import epam.railway.manager.Config;

/**
 *
 * @author denis
 */
public class CommandLogout implements ICommand{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;
        HttpSession session = request.getSession(false);  
        session.invalidate();
        
        page = Config.getInstance().getProperty(Config.LOGIN);
        return page;
        
    }
}
