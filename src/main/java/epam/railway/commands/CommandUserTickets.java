/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.entities.Ticket;
import epam.railway.manager.Config;
import epam.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author denis
 */
public class CommandUserTickets implements ICommand{
    private static final String USER_ID = "userid";
    private static final String TICKETS = "tickets";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute(USER_ID);

        List<Ticket> tickets = null;

        String status = request.getParameter("status");

        if (status.equals("actual")) {
            tickets = TicketService.getInstance().getActualTicketsByUserId(userId);
        } else if (status.equals("archive")) {
            tickets = TicketService.getInstance().getArchiveTicketsByUserId(userId);
        }

        
        request.setAttribute("status", status);
        request.setAttribute(TICKETS, tickets);
        page = Config.getInstance().getProperty(Config.USER_TICKETS);
        return page;
    }
    
}
