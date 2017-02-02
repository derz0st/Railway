package epam.railway.listeners;

import epam.railway.dao.daofactory.DaoFactory;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Timer for train tickets updating
 */
class UpdateTicketTimer extends TimerTask {
    public void run() {

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.roll(Calendar.DAY_OF_MONTH, 10);

        Date future = today.getTime();
        Timestamp timestamp = new Timestamp(future.getTime());
        timestamp.setNanos(0);

        List<Integer> allTrainNumbers = DaoFactory.getDaoTrainNeo().getAllTrainNumbers();

        for (Integer index: allTrainNumbers){
            DaoFactory.getDaoTrainTicketsOnDate().addTrainOnDate(index, timestamp, 10);
        }

    }
}
