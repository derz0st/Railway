package epam.railway.listeners;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Ticket;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Decrement amount of busy seats, when session closed
 * if there was ticket in shopping cart
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {

        HttpSession session = arg0.getSession();
        Ticket ticket = (Ticket) session.getAttribute("ticketInShoppingCart");

        if(ticket != null){
            Timestamp timestamp = ticket.getStartDateTime();
            Calendar cropTime = Calendar.getInstance();
            cropTime.setTime(timestamp);
            cropTime.set(Calendar.HOUR_OF_DAY, 0);
            cropTime.set(Calendar.MINUTE, 0);
            cropTime.set(Calendar.SECOND, 0);
            cropTime.set(Calendar.MILLISECOND, 0);

            timestamp = new Timestamp(cropTime.getTimeInMillis());
            DaoFactory.getDaoTrainTicketsOnDate().descBusySeatsByTrainNumberAndDate(ticket.getTrainNumber(), timestamp);
        }

    }
}