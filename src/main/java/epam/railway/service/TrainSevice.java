package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Station;
import epam.railway.entities.TrainNeo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created by denis on 29.12.16.
 */
public class TrainSevice {

    private static TrainSevice instance;
    private static final Logger log = LogManager.getLogger(TrainSevice.class.getName());

    private TrainSevice(){}

    public static TrainSevice getInstance() {
        if (instance == null) {
            instance = new TrainSevice();
        }
        return instance;
    }


    public List<TrainNeo> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity, Timestamp date){
        List<TrainNeo> trains = DaoFactory.getDaoTrainNeo().findByDeparturecityAndDestinationcity(departureCity, destinationCity);
        List<Station> stationList;

        for (TrainNeo train: trains) {

            Integer departureSt = 0, destinationSt = 0;

            stationList = DaoFactory.getDaoStation().findByTrainNumber(train.getNumber());
            train.setStations(stationList);


            for(int i = 0; i < stationList.size(); i++){
                if(stationList.get(i).getName().equals(departureCity)){
                    departureSt = i;
                } else if(stationList.get(i).getName().equals(destinationCity)){
                    destinationSt = i;
                }
            }

            train.setDepartureTime(stationList.get(departureSt).getDepartureTime());
            train.setDepartureCity(stationList.get(0).getName());
            train.setArrivalTime(stationList.get(destinationSt).getArrivalTime());
            train.setArrivalCity(stationList.get(stationList.size()-1).getName());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(train.getArrivalTime().getTime() - train.getDepartureTime().getTime() - 97200000);
            train.setTravelTime(calendar);

            Double price = 0.0;

            for(int i = departureSt; i <= destinationSt - 1; i++){
                price += stationList.get(i).getPriceToNext();
            }

            train.setPrice(price);
            train.setTrainTicketsOnDate(DaoFactory.getDaoTrainTicketsOnDate().findByTrainNumberAndDate(train.getId(), date));
        }
        return trains;
    }

    public TrainNeo findByTrainNumber(Integer trainNumber, String departureCity, String destinationCity, Timestamp date){
        TrainNeo train = new TrainNeo();
        train.setNumber(trainNumber);
        train.setId(trainNumber);
        List<Station> stationList;

        Integer departureSt = 0, destinationSt = 0;

        stationList = DaoFactory.getDaoStation().findByTrainNumber(trainNumber);
        train.setStations(stationList);

        for(int i = 0; i < stationList.size(); i++){
            if(stationList.get(i).getName().equals(departureCity)){
                departureSt = i;
            } else if(stationList.get(i).getName().equals(destinationCity)){
                destinationSt = i;
            }
        }

        train.setDepartureTime(stationList.get(departureSt).getDepartureTime());
        train.setDepartureCity(stationList.get(0).getName());
        train.setArrivalTime(stationList.get(destinationSt).getArrivalTime());
        train.setArrivalCity(stationList.get(stationList.size()-1).getName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(train.getArrivalTime().getTime() - train.getDepartureTime().getTime() - 97200000);
        train.setTravelTime(calendar);

        Double price = 0.0;

        for(int i = departureSt; i <= destinationSt; i++){
            price += stationList.get(i).getPriceToNext();
        }

        train.setPrice(price);
        train.setTrainTicketsOnDate(DaoFactory.getDaoTrainTicketsOnDate().findByTrainNumberAndDate(train.getId(), date));

        return train;
    }
}
