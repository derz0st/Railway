/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author denis
 */
public class CommandDeleteTicket implements ICommand{
    private static final String TICKET_ID = "ticketid";
    private static final String USER_ID = "userid";
    private static final String TICKETS = "tickets";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        Integer ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
        TicketService.deleteTicket(ticketId);
        request.setAttribute("userid", Integer.valueOf(request.getParameter("userid")));

        //List<Ticket> tickets = DaoFactory.getDaoTicket().findByUserid(userId);
        
        //request.setAttribute(TICKETS, tickets);
        //page = Config.getInstance().getProperty(Config.USER_TICKETS);
        page = new CommandGetCustomerTickets().execute(request, responce);
        return page;
    }
    
}
