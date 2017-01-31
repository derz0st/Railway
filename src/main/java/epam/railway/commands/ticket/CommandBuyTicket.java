/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.manager.Config;
import epam.railway.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command which adds prepared ticket object from user session to database.
 * If user tries to execute command without ticket in session, he will get error.
 */
public class CommandBuyTicket implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandBuyTicket.class);
    private static final String TICKET_IN_SHOPPING_CART = "ticketInShoppingCart",
            ERROR = "error",
            ERROR_MESSAGE = "There isn't ticket in shopping cart";
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        Ticket ticket = (Ticket) session.getAttribute(TICKET_IN_SHOPPING_CART);

        if(ticket != null) {
            TicketService.getInstance().addTicket(ticket);
            session.removeAttribute(TICKET_IN_SHOPPING_CART);
            page = Config.getInstance().getProperty(Config.ORDER);
        } else {
            commandLogger.error(ERROR_MESSAGE);
            request.setAttribute(ERROR, ERROR_MESSAGE);
            page = Config.getInstance().getProperty(Config.ERROR);
        }

        return page;
    }
    
}
