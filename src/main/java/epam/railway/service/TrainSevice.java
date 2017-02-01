package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Station;
import epam.railway.entities.Train;
import epam.railway.entities.TrainTicketsOnDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
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


    public List<Train> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity, Timestamp date){
        List<Train> trains = DaoFactory.getDaoTrainNeo().findByDeparturecityAndDestinationcity(departureCity, destinationCity);
        List<Train> actualTrains = new ArrayList<>();
        List<Station> stationList;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp dateWithoutTime = new Timestamp(calendar.getTimeInMillis());

        for (Train train: trains) {

            TrainTicketsOnDate trainTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate().findByTrainNumberAndDate(train.getId(), dateWithoutTime);

            if(trainTicketsOnDate != null && trainTicketsOnDate.getBusySeats() < trainTicketsOnDate.getTotalSeats() ) {

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

                Calendar calendarTravelTime = Calendar.getInstance();
                calendarTravelTime.setTimeInMillis(train.getArrivalTime().getTime() - train.getDepartureTime().getTime() - 97200000);
                train.setTravelTime(calendarTravelTime);

                Double price = 0.0;

                for(int i = departureSt; i <= destinationSt - 1; i++){
                    price += stationList.get(i).getPriceToNext();
                }

                train.setPrice(price);
                train.setTrainTicketsOnDate(trainTicketsOnDate);
                Calendar calendarCurrent = Calendar.getInstance();
                calendarCurrent.setTimeInMillis(date.getTime());
                calendarCurrent.set(0, 0, 0);
                Calendar calendarTrain = Calendar.getInstance();
                calendarTrain.setTimeInMillis(train.getDepartureTime().getTime());

                calendarTrain.set(0, 0, 0);




                if (calendarTrain.after(calendarCurrent)) {
                    actualTrains.add(train);
                }

            }
        }
        return actualTrains;
    }

    public Train findByTrainNumber(Integer trainNumber, String departureCity, String destinationCity, Timestamp date){
        Train train = new Train();
        TrainTicketsOnDate trainTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate().findByTrainNumberAndDate(trainNumber, date);

        if (trainTicketsOnDate != null) {

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
            train.setTrainTicketsOnDate(trainTicketsOnDate);

            return train;
        } else {
            return null;
        }
    }
}
