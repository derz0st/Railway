package epam.railway.dao.interfaces;

import epam.railway.entities.TrainTicketsOnDate;

import java.sql.Timestamp;

/**
 * Provides methods for working with train's tickets on date
 */
public interface DaoTrainTicketsOnDateInterface {

    /**
     * Records in database number of seats in the train on the date
     * @param trainNumber number of train
     * @param date date when train travels
     * @param totalSeats number of seats in the train
     */
    void addTrainOnDate(Integer trainNumber, Timestamp date, Integer totalSeats);

    /**
     * Find record with specified trainNumber and date
     * @param trainNumber number of train
     * @param date date when train travels
     * @return TrainTicketsOnDate object with specified trainNumber and date
     */
    TrainTicketsOnDate findByTrainNumberAndDate(Integer trainNumber, Timestamp date);

    /**
     * increases the number of occupied seats
     * @param trainNumber number of train
     * @param date date when train travels
     * @return true if changes are recorded and false if changes are not recorded
     */
    boolean incBusySeatsByTrainNumberAndDate(Integer trainNumber, Timestamp date);

    /**
     * decreases the number of occupied seats
     * @param trainNumber number of train
     * @param date date when train travels
     * @return true if changes are recorded and false if changes are not recorded
     */
    boolean descBusySeatsByTrainNumberAndDate(Integer trainNumber, Timestamp date);
}
