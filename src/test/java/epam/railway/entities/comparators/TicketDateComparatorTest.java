package epam.railway.entities.comparators;

import epam.railway.entities.Ticket;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by denis on 16.01.17.
 */
public class TicketDateComparatorTest {

    TicketDateComparator comparator = new TicketDateComparator();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Ticket ticket1 = new Ticket();
    Ticket ticket2 = new Ticket();
    Integer result;

    @Test
    public void compareEqualsTicketDates() throws ParseException {
        ticket1.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        ticket2.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        result = comparator.compare(ticket1, ticket2);
        assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void compareWithEqualsDateAndDifferentTime() throws ParseException {
        ticket1.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        ticket2.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:26:00").getTime()));
        result = comparator.compare(ticket1, ticket2);
        assertEquals(Integer.valueOf(-1), result);
    }

    @Test
    public void compareFirstBigger() throws ParseException {
        ticket1.setStartDateTime(new Timestamp(dateFormat.parse("2017-05-18 08:00:00").getTime()));
        ticket2.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        result = comparator.compare(ticket1, ticket2);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void compareFirstLess() throws ParseException {
        ticket1.setStartDateTime(new Timestamp(dateFormat.parse("1999-12-17 08:00:00").getTime()));
        ticket2.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        result = comparator.compare(ticket1, ticket2);
        assertEquals(Integer.valueOf(-1), result);
    }

    @Test(expected = NullPointerException.class)
    public void compareWithNullPointer() throws ParseException {
        ticket1.setStartDateTime(null);
        ticket2.setStartDateTime(new Timestamp(dateFormat.parse("2017-01-16 08:00:00").getTime()));
        result = comparator.compare(ticket1, ticket2);
    }


}