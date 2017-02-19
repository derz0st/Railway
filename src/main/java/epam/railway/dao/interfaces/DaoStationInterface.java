package epam.railway.dao.interfaces;

import epam.railway.entities.Station;

import java.util.List;

/**
 * Provides methods for working with stations
 */
public interface DaoStationInterface {

    /**
     * Finds all stations through which the train goes
     * @param trainNumber number of train
     * @return an ordered stations list through which the train goes
     */
    List<Station> findByTrainNumber(Integer trainNumber);

    Station findByName(String name);

    void addNewStation(String name);

    void addNewLinkBetweenStations(String departureStation, String destinationStation, Integer trainNumber,
                                          String departureTime, String destinationTime, Double price, Integer order);

}
