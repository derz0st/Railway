/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that determines the home page for a user or administrator.
 */
public class CommandFindTickets implements ICommand {

    private static final String USER_ENTITY = "userentity";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);

        if (currentUser.getAdminid().equals(1)){
            page = Config.getInstance().getProperty(Config.RETURN_TICKETS);
        } else {
            page = Config.getInstance().getProperty(Config.ORDER);
        }

        return page;
    }
}
