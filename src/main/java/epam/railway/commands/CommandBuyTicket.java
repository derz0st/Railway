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

/**
 *
 * @author denis
 */
public class CommandBuyTicket implements ICommand{
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CommandBuyTicket.class.getName());
    private static final String TRAIN_ID = "trainid";
    private static final String DATE = "date";
    private static final String USER_ID = "userid";
    private static final String ERROR = "error";
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        
        HttpSession session = request.getSession(false);
        Ticket ticket = (Ticket) session.getAttribute("ticketInShoppingCart");
        System.out.println("Это билет из сессии: " + ticket);
        
        //try {

            TicketService.getInstance().addTicket(ticket);

            session.removeAttribute("ticketInShoppingCart");


            page = Config.getInstance().getProperty(Config.ORDER);

        
//        }  catch (EntityNotFoundException ex) {
//            request.setAttribute(ERROR, ex.getMessage());
//            page = Config.getInstance().getProperty(Config.ERROR);
//
//        }

        return page;
    }
    
}
