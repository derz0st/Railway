package epam.railway.dao.interfaces;

import epam.railway.entities.TrainTicketsOnDate;

import java.sql.Timestamp;

/**
 * Created by denis on 29.12.16.
 */
public interface DaoTrainTicketsOnDateInterface {
    TrainTicketsOnDate findByTrainNumberAndDate(Integer trainNumber, Timestamp date);
}
