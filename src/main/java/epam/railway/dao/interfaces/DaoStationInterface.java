package epam.railway.dao.interfaces;

import epam.railway.entities.Station;

import java.util.List;

/**
 * Created by denis on 30.12.16.
 */
public interface DaoStationInterface {
    List<Station> findByTrainNumber(Integer trainNumber);
}
