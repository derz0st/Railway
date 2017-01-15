package epam.railway.commands;

import epam.railway.entities.Ticket;
import epam.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by denis on 16.01.17.
 */
public class CommandAcceptReturn implements ICommand{
    private static final String TICKET_ID = "ticketid";
    private static final String USER_ID = "userid";
    private static final String TICKETS = "tickets";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute(USER_ID);

        List<Ticket> tickets = null;

        Integer ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
        TicketService.getInstance().acceptReturn(ticketId);


        return page = new CommandGetReturnTickets().execute(request, responce);
    }
}
