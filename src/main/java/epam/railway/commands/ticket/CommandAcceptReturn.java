package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.services.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command which is used to confirm the request for a refund of the ticket.
 * Only administrator can confirm it.
 */
public class CommandAcceptReturn implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandAcceptReturn.class);
    private static final String TICKET_ID = "ticketid",
            USER_ENTITY = "userentity",
            ERROR_MESSAGE = "Permission denied",
            ERROR = "error";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);

        if (currentUser.getAdminid().equals(1)) {

            try {

                Integer ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
                Ticket ticket = DaoFactory.getDaoTicket().findByTicketid(ticketId);
                TicketService.getInstance().acceptReturn(ticket);
                commandLogger.info("Admin - id: "+ currentUser.getId() + " accepted ticket: " + ticketId + " return.");

            } catch (Exception e) {
                commandLogger.error("Exception " + e.getMessage());
            }

            return new CommandGetReturnTickets().execute(request, response);

        } else {
            commandLogger.error("Not admin user - id: "+ currentUser.getId() + " tried to accept ticket return.");
            request.setAttribute(ERROR, ERROR_MESSAGE);
            page = Config.getInstance().getProperty(Config.ERROR);
        }

        return page;
    }

}
