package epam.railway.entities.comparators;

import epam.railway.entities.Ticket;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for Tickets by date
 */
public class TicketDateComparator implements Comparator<Ticket>, Serializable {

    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.getStartDateTime().compareTo(o2.getStartDateTime());
    }
}
