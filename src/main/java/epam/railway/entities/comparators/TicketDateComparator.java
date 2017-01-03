package epam.railway.entities.comparators;

import epam.railway.entities.Ticket;

import java.util.Comparator;

/**
 * Created by denis on 03.01.17.
 */
public class TicketDateComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.getStartDateTime().compareTo(o2.getStartDateTime());
    }
}
