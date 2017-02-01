/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.interfaces;

import epam.railway.entities.Train;

import java.util.List;

/**
 *
 * @author denis
 */
public interface DaoTrainInterface {

    /**
     * Finds all trains that travel between two cities
     * @param departureCity a city from which the train departs
     * @param destinationCity a city in which the train arrives
     * @return trains that travel between two cities
     */
    List<Train> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity);

    /**
     * Finds ids of all trains
     * @return id list of all trains in database
     */
    List<Integer> getAllTrainNumbers();

}
