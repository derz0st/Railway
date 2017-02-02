package epam.railway.services;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoTrainTicketsOnDateInterface;
import epam.railway.entities.TrainTicketsOnDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

/**
 * Service for work with train's tickets on date
 */
public class TicketsOnDateService {

    private static TicketsOnDateService instance;
    private static final Logger log = LogManager.getLogger(TicketsOnDateService.class.getName());
    private final DaoTrainTicketsOnDateInterface daoTrainTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();

    private TicketsOnDateService(){}

    public static TicketsOnDateService getInstance() {
        if (instance == null) {
            instance = new TicketsOnDateService();
        }
        return instance;
    }

    public TrainTicketsOnDate find(Integer trainNumber, Timestamp date){
        return daoTrainTicketsOnDate.findByTrainNumberAndDate(trainNumber, date);
    }

    public boolean incBusySeats(final Integer trainNumber, Timestamp date){
        return daoTrainTicketsOnDate.incBusySeatsByTrainNumberAndDate(trainNumber, date);
    }

}
