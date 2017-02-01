package epam.railway.dao.interfaces;

import epam.railway.entities.Station;

import java.util.List;

/**
 * Created by denis on 30.12.16.
 *
 */
public interface DaoStationInterface {

    /**
     * Finds all stations through which the train goes
     * @param trainNumber number of train
     * @return an ordered stations list through which the train goes
     */
    List<Station> findByTrainNumber(Integer trainNumber);

}
