package epam.railway.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by denis on 02.01.17.
 */
public class UpdateTicketListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {

        Timer timer = new Timer(true);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.roll(Calendar.DAY_OF_MONTH, 1);

        Date tomorrow = today.getTime();
        System.out.println("Update Ticket будет запущен: " + tomorrow);

        timer.scheduleAtFixedRate(new UpdateTicketTimer(), tomorrow, 86400000);

    }

    public void contextDestroyed(ServletContextEvent event) {
    }

}