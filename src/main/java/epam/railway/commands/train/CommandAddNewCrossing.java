package epam.railway.commands.train;

import epam.railway.commands.ICommand;
import epam.railway.manager.Config;
import epam.railway.services.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by denis on 03.02.17.
 */
public class CommandAddNewCrossing implements ICommand {

    private static final String
            DEPARTURE_CITY = "departurecity",
            DESTINATION_CITY = "destinationcity",
            DEPARTURE_TIME = "departuretime",
            ARRIVAL_TIME = "arrivaltime",
            INFO = "info",
            TRAIN_NUMBER = "trainnumber",
            PRICE = "price",
            MOVE_NUMBER = "movenumber",
            CURRENT_DAY = ":00 00",
            NEXT_DAY = ":00 01";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String departureCity = request.getParameter(DEPARTURE_CITY);
            String destinationCity = request.getParameter(DESTINATION_CITY);
            String departureTimeString = request.getParameter(DEPARTURE_TIME);
            String arrivalTimeString = request.getParameter(ARRIVAL_TIME);
            Integer trainNumber = Integer.valueOf(request.getParameter(TRAIN_NUMBER));
            Double price = Double.valueOf(request.getParameter(PRICE));
            Integer moveNumber = Integer.valueOf(request.getParameter(MOVE_NUMBER));


            TrainService.getInstance().createLinkBetweenStations(departureCity, destinationCity, departureTimeString,
                    arrivalTimeString, trainNumber, price, moveNumber);

            request.setAttribute(INFO, "Поезд успешно добавлен");


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return Config.getInstance().getProperty(Config.ADMINISTRATE);
    }


}

