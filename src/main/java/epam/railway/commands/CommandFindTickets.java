/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

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
public class CommandFindTickets implements ICommand{
    
    private static final String USER_ID = "userid";
    private static final String ERROR = "error";
    private static final String DEPARTURE_CITY = "departurecity";
    private static final String DESTINATION_CITY = "destinationcity";
    private static final String TRAINS = "trains";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute(USER_ID);
        
        if(userId != null){
            page = Config.getInstance().getProperty(Config.ORDER);    
            
        }else{
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
        return page;
    }
}
