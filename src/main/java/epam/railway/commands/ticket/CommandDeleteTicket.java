/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.services.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that deletes ticket from database. Only administrator or owner can delete ticket.
 */
public class CommandDeleteTicket implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandDeleteTicket.class);
    private static final String TICKET_ID = "ticketid",
            USER_ID = "userid",
            USER_ENTITY = "userentity";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = null;

        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute(USER_ENTITY);

        Integer ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
        Ticket ticket = TicketService.getInstance().findTicketById(ticketId);

        if (ticket != null && (sessionUser.getId() == ticket.getUserid() || sessionUser.getAdminid().equals(1))) {

            TicketService.getInstance().deleteTicket(ticketId);
            commandLogger.info("User id: " + sessionUser.getId() + " deleted ticket id: " + ticketId);
            String currentUser = request.getParameter(USER_ID);

            // if command was executed by administrator
            if (currentUser != null) {
                Integer userId = Integer.valueOf(currentUser);
                request.setAttribute(USER_ID, userId);
                page = new CommandGetCustomerTickets().execute(request, response);
            } else {
                page = new CommandUserTickets().execute(request, response);
            }
        } else {
            commandLogger.error("User id: " + sessionUser.getId() + " tried to delete ticket id: " + ticketId);
        }

        return page;
    }
    
}
