/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.service.TrainSevice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    
    private static final String USER_ID = "userid";
    private static final String ERROR = "error";
    private static final String DEPARTURE_CITY = "departurecity";
    private static final String DESTINATION_CITY = "destinationcity";
    private static final String TRAINS = "trains";
    private static final String DATE = "traveldate";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute(USER_ID);
        if(userId != null){
            
            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                String departureCity = request.getParameter(DEPARTURE_CITY);
                String destinationCity = request.getParameter(DESTINATION_CITY);

                Date parsedDate = dateFormat.parse(request.getParameter(DATE));
                Timestamp travelDate = new Timestamp(parsedDate.getTime());

                List list = TrainSevice.getInstance().findByDeparturecityAndDestinationcity(departureCity, destinationCity, travelDate);
                User user = (User) session.getAttribute("userentity");
                request.setAttribute("userentity", user);

                if (list.size() != 0 ) {
                    
                    request.setAttribute(TRAINS, list);
                    request.setAttribute(DEPARTURE_CITY, departureCity);
                    request.setAttribute(DESTINATION_CITY, destinationCity);
                    request.setAttribute(DATE, request.getParameter(DATE));

                    page = Config.getInstance().getProperty(Config.ORDER);
                    
                } else {
                    request.setAttribute(ERROR, Message.getInstance().getProperty(Message.NO_TRAINS));
                    page = Config.getInstance().getProperty(Config.ORDER);
                }
            } catch (ParseException ex) {
                request.setAttribute(ERROR, "Введите дату в формате yyyy-mm-dd");
                page = Config.getInstance().getProperty(Config.ORDER);
            }
        }else{
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
        return page;
    }
}
