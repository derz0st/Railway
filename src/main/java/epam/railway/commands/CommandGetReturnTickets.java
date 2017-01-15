package epam.railway.commands;

import epam.railway.entities.Ticket;
import epam.railway.manager.Config;
import epam.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by denis on 15.01.17.
 */
public class CommandGetReturnTickets implements ICommand{
    private static final String USER_ID = "userid";
    private static final String TICKETS = "tickets";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        //HttpSession session = request.getSession(false);
        //Integer userId = (Integer)session.getAttribute(USER_ID);

        List<Ticket> tickets = null;


        //Integer userId = Integer.valueOf(request.getParameter("userid"));
        tickets = TicketService.getInstance().getReturnTickets();





        //request.setAttribute("status", status);
        request.setAttribute(TICKETS, tickets);
        page = Config.getInstance().getProperty(Config.RETURN_TICKETS);
        return page;
    }
}
