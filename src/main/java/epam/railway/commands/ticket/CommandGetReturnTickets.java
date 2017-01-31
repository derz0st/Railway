package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command to display a tickets for refund. Used by the administrator.
 */
public class CommandGetReturnTickets implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandGetCustomerTickets.class);
    private static final String TICKETS = "tickets",
            ERROR = "error",
            USER_ENTITY = "userentity";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        String page;

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);

        if (currentUser.getAdminid().equals(1)) {
            List<Ticket> tickets = TicketService.getInstance().getReturnTickets();
            request.setAttribute(TICKETS, tickets);
            page = Config.getInstance().getProperty(Config.RETURN_TICKETS);
        } else {
            commandLogger.error("User id: " + currentUser.getId() + "tried to get refund tickets without permissions");
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.PERMISSION_DENIED));
            page = Config.getInstance().getProperty(Config.ERROR);
        }

        return page;
    }

}
