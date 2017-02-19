package epam.railway.services;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoStationInterface;
import epam.railway.dao.interfaces.DaoTrainInterface;
import epam.railway.dao.interfaces.DaoTrainTicketsOnDateInterface;
import epam.railway.entities.Station;
import epam.railway.entities.Train;
import epam.railway.entities.TrainTicketsOnDate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Service for work with trains
 */
public class TrainService {

    private static TrainService instance;
    private final DaoTrainInterface daoTrain = DaoFactory.getDaoTrainNeo();
    private final DaoStationInterface daoStation = DaoFactory.getDaoStation();
    private final DaoTrainTicketsOnDateInterface daoTicketsOnTrain = DaoFactory.getDaoTrainTicketsOnDate();

    private static final String
            CURRENT_DAY = ":00 00",
            NEXT_DAY = ":00 01";

    private TrainService(){}

    public static TrainService getInstance() {
        if (instance == null) {
            instance = new TrainService();
        }
        return instance;
    }

    public void createLinkBetweenStations(String departureCity, String destinationCity, String departureTimeString,
        String arrivalTimeString, Integer trainNumber, Double price, Integer moveNumber) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date startDate = dateFormat.parse(departureTimeString);
        Date finishDate = dateFormat.parse(arrivalTimeString);


        Station depStation = DaoFactory.getDaoStation().findByName(departureCity);
        Station destStation = DaoFactory.getDaoStation().findByName(destinationCity);

        if (depStation == null){
            DaoFactory.getDaoStation().addNewStation(departureCity);
        } if (destStation == null){
            DaoFactory.getDaoStation().addNewStation(destinationCity);
        }

        departureTimeString = departureTimeString + CURRENT_DAY;

        if (startDate.after(finishDate)) {
            arrivalTimeString = arrivalTimeString + NEXT_DAY;
        } else {
            arrivalTimeString = arrivalTimeString + CURRENT_DAY;
        }

        DaoFactory.getDaoStation().addNewLinkBetweenStations(departureCity, destinationCity,
                trainNumber, departureTimeString, arrivalTimeString, price, moveNumber);

    }


    public List<Train> findByDeparturecityAndDestinationcity(String departureCity, String destinationCity, Timestamp date){

        List<Train> trains = daoTrain.findByDeparturecityAndDestinationcity(departureCity, destinationCity);
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

            TrainTicketsOnDate trainTicketsOnDate = daoTicketsOnTrain.findByTrainNumberAndDate(train.getId(), dateWithoutTime);

            if(trainTicketsOnDate != null && trainTicketsOnDate.getBusySeats() < trainTicketsOnDate.getTotalSeats() ) {

                Integer departureSt = 0, destinationSt = 0;

                stationList = daoStation.findByTrainNumber(train.getNumber());
                train.setStations(stationList);


                for (int i = 0; i < stationList.size(); i++) {
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

}
