package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.services.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command to request a refund of the ticket.
 */
public class CommandReturnTicket implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandReturnTicket.class);
    private static final String ERROR = "error",
            TICKET_ID = "ticketid",
            USER_ENTITY = "userentity",
            TICKETS = "tickets",
            STATUS = "status",
            ACTUAL = "actual";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User)session.getAttribute(USER_ENTITY);

        Integer ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
        Ticket ticket = TicketService.getInstance().findTicketById(ticketId);

        if (ticket.getUserid().equals(currentUser.getId())) {

            TicketService.getInstance().returnTicket(ticketId);

            List<Ticket> tickets = TicketService.getInstance().getActualTicketsByUserId(currentUser.getId());
            commandLogger.info("User id: " + currentUser.getId() + " returned ticket id: " + ticketId);
            request.setAttribute(STATUS, ACTUAL);
            request.setAttribute(TICKETS, tickets);
            page = Config.getInstance().getProperty(Config.USER_TICKETS);

        } else {
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.PERMISSION_DENIED));
            commandLogger.error("User id: " + currentUser.getId() + " tried to return ticket id: " + ticketId);
            page = Config.getInstance().getProperty(Config.ERROR);
        }

        return page;
    }

}
