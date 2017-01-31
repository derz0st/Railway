package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.implementation.DaoTrainTicketsOnDate;
import epam.railway.entities.TrainTicketsOnDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

/**
 * Created by denis on 30.01.17.
 */
public class TicketsOnDateService {

    private static TicketsOnDateService instance;
    private static final Logger log = LogManager.getLogger(TicketsOnDateService.class.getName());
    private DaoTrainTicketsOnDate daoTrainTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();

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
