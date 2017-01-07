/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.service.TrainSevice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denis
 */
public class CommandOrder implements ICommand{
    
    private static final String
            ERROR = "error",
            DEPARTURE_CITY = "departurecity",
            DESTINATION_CITY = "destinationcity",
            TRAINS = "trains",
            DATE = "traveldate",
            DEPARTURE_ERROR = "departure_error",
            DESTINATION_ERROR = "destination_error",
            DATE_ERROR = "date_error",
            ERROR_EMPTY = "empty field",
            DATE_FORMAT_TEMPLATE = "dd-MM-yyyy",
            ERROR_INCORRECT_FORMAT = "Enter date in next format: dd-MM-yyyy";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        String page = Config.getInstance().getProperty(Config.ORDER);

        String departureCity = request.getParameter(DEPARTURE_CITY);
        String destinationCity = request.getParameter(DESTINATION_CITY);
        String dateInString = request.getParameter(DATE);

        request.setAttribute(DEPARTURE_CITY, departureCity);
        request.setAttribute(DESTINATION_CITY, destinationCity);
        request.setAttribute(DATE, dateInString);

        if(departureCity.isEmpty() || destinationCity.isEmpty() || dateInString.isEmpty()){

            if(departureCity.isEmpty()) request.setAttribute(DEPARTURE_ERROR, ERROR_EMPTY);
            if(destinationCity.isEmpty()) request.setAttribute(DESTINATION_ERROR, ERROR_EMPTY);
            if(dateInString.isEmpty()) request.setAttribute(DATE_ERROR, ERROR_EMPTY);

        } else {

            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_TEMPLATE);
                Date parsedDate = dateFormat.parse(request.getParameter(DATE));
                Timestamp travelDate = new Timestamp(parsedDate.getTime());
                List list = TrainSevice.getInstance().findByDeparturecityAndDestinationcity(departureCity, destinationCity, travelDate);

                if (list.size() != 0 ) {
                    request.setAttribute(TRAINS, list);

                }else{
                    request.setAttribute(ERROR, Message.getInstance().getProperty(Message.NO_TRAINS));
                }

            } catch (ParseException ex) {
                request.setAttribute(DATE_ERROR, ERROR_INCORRECT_FORMAT);
            }
        }

        return page;
    }
}
