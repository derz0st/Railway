/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command that display the user's tickets.
 */
public class CommandUserTickets implements ICommand {

    private static final String USER_ENTITY = "userentity",
            TICKETS = "tickets",
            STATUS = "status",
            ACTUAL = "actual",
            ARCHIVE = "archive";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User)session.getAttribute(USER_ENTITY);

        List<Ticket> tickets = null;

        String status = request.getParameter(STATUS);

        if (status.equals(ACTUAL)) {
            tickets = TicketService.getInstance().getActualTicketsByUserId(currentUser.getId());
        } else if (status.equals(ARCHIVE)) {
            tickets = TicketService.getInstance().getArchiveTicketsByUserId(currentUser.getId());
        }
        
        request.setAttribute(STATUS, status);
        request.setAttribute(TICKETS, tickets);
        page = Config.getInstance().getProperty(Config.USER_TICKETS);
        return page;
    }
    
}
