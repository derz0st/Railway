/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Ticket;
import epam.railway.entities.User;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author denis
 */
public class CommandPay implements ICommand{
    private static final String TRAIN_ID = "trainid";
    private static final String DEPARTURE_CITY = "departurecity";
    private static final String DESTINATION_CITY = "destinationcity";
    private static final String DEPARTURE_TIME = "departuretime";
    private static final String ARRIVAL_TIME = "arrivaltime";
    private static final String USER_ID = "userid";
    private static final String TRAIN = "train";
    private static final String DATE = "traveldate";
    private static final String USER = "user";
    private static final String PRICE = "price";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // departure_city, destination_city
        String departureCity = request.getParameter(DEPARTURE_CITY);
        String destinationCity = request.getParameter(DESTINATION_CITY);

        Double price = Double.valueOf(request.getParameter(PRICE));

        //startdate
        Date parsedDepartureTime = null;
        Date parsedArrivalTime = null;
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(request.getParameter(DATE));
            parsedDepartureTime = timeFormat.parse(request.getParameter(DEPARTURE_TIME));
            parsedArrivalTime = timeFormat.parse(request.getParameter(ARRIVAL_TIME));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // train_number
        String trainId = request.getParameter(TRAIN_ID);
        Integer trainNumber = Integer.valueOf(trainId);
        //Train train = DaoFactory.getDaoTrain().findById(Integer.parseInt(trainId));
        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute(USER_ID);
        //user_id, user_name, user_last_name
        User user = DaoFactory.getDaoUser().findById(userId);

        Ticket ticket = new Ticket();

        ticket.setUserid(user.getId());
        ticket.setUserName(user.getFirstname());
        ticket.setUserLastName(user.getLastname());
        ticket.setStartDateTime(new Timestamp(parsedDate.getTime() + parsedDepartureTime.getTime() + 10800000));
        ticket.setEndDateTime(new Timestamp(parsedArrivalTime.getTime()));
        ticket.setPrice(price);
        ticket.setTrainNumber(trainNumber);
        ticket.setDepartureCity(departureCity);
        ticket.setDestinationCity(destinationCity);
        
        request.setAttribute(USER, user);
        //request.setAttribute(TRAIN, train);
        request.setAttribute("ticket", ticket);
        request.setAttribute("status", "new");

        DaoFactory.getDaoTrainTicketsOnDate().incBusySeatsByTrainNumberAndDate(trainNumber, new Timestamp(parsedDate.getTime()));

        session.setAttribute("ticketInShoppingCart", ticket);
       
        page = Config.getInstance().getProperty(Config.USER_TICKETS);
        return page;
    }
}
