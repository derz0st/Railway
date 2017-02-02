/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.ticket;

import epam.railway.commands.ICommand;
import epam.railway.entities.Ticket;
import epam.railway.entities.TrainTicketsOnDate;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.services.TicketsOnDateService;
import epam.railway.services.UserService;

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
 * Command that generates the ticket object and adds it to the user's session
 */
public class CommandPay implements ICommand {

    private static final String TRAIN_ID = "trainid",
            DEPARTURE_CITY = "departurecity",
            DESTINATION_CITY = "destinationcity",
            DEPARTURE_TIME = "departuretime",
            ARRIVAL_TIME = "arrivaltime",
            DATE = "traveldate",
            ERROR = "error",
            USER = "user",
            USER_ENTITY = "userentity",
            PRICE = "price",
            TICKET_IN_SHOPPING_CART = "ticketInShoppingCart",
            DATE_FORMAT = "dd-MM-yyyy",
            TIME_FORMAT = "HH:mm",
            ERROR_TEXT = "You can't add a ticket to cart at the moment",
            TICKET = "ticket",
            STATUS = "status",
            NEW = "new";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        Ticket ticketInShoppingCart = (Ticket) session.getAttribute(TICKET_IN_SHOPPING_CART);

        if (ticketInShoppingCart == null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

            // get departure_city, destination_city
            String departureCity = request.getParameter(DEPARTURE_CITY);
            String destinationCity = request.getParameter(DESTINATION_CITY);

            Double price = Double.valueOf(request.getParameter(PRICE));

            // get departure date
            Date parsedDepartureTime;
            Date parsedArrivalTime;
            Date parsedDate;
            try {
                parsedDate = dateFormat.parse(request.getParameter(DATE));
                parsedDepartureTime = timeFormat.parse(request.getParameter(DEPARTURE_TIME));
                parsedArrivalTime = timeFormat.parse(request.getParameter(ARRIVAL_TIME));


                // get train_number
                String trainId = request.getParameter(TRAIN_ID);
                Integer trainNumber = Integer.valueOf(trainId);


                User currentUser = (User) session.getAttribute(USER_ENTITY);
                // get user_id, user_name, user_last_name
                User user = UserService.getInstance().findUser(currentUser.getId());

                Ticket ticket = new Ticket();
                // generate ticket object
                ticket.setUserid(user.getId());
                ticket.setUserName(user.getFirstname());
                ticket.setUserLastName(user.getLastname());
                ticket.setStartDateTime(new Timestamp(parsedDate.getTime() + parsedDepartureTime.getTime() + 10800000));
                ticket.setEndDateTime(new Timestamp(parsedArrivalTime.getTime() + 86400000));
                ticket.setPrice(price);
                ticket.setTrainNumber(trainNumber);
                ticket.setDepartureCity(departureCity);
                ticket.setDestinationCity(destinationCity);

                request.setAttribute(USER, user);
                request.setAttribute(TICKET, ticket);
                request.setAttribute(STATUS, NEW);
                TrainTicketsOnDate ticketsOnDate =
                        TicketsOnDateService.getInstance().find(trainNumber, new Timestamp(parsedDate.getTime()));

                // if there are still tickets on the train
                if (ticketsOnDate.getBusySeats() < ticketsOnDate.getTotalSeats()) {

                    if (TicketsOnDateService.getInstance().incBusySeats(trainNumber, new Timestamp(parsedDate.getTime()))) {
                        session.setAttribute(TICKET_IN_SHOPPING_CART, ticket);
                        page = Config.getInstance().getProperty(Config.USER_TICKETS);

                    } else {
                        page = Config.getInstance().getProperty(Config.ERROR);
                        request.setAttribute(ERROR, ERROR_TEXT);
                    }

                } else {
                    page = Config.getInstance().getProperty(Config.ERROR);
                    request.setAttribute(ERROR, Message.getInstance().getProperty(Message.NO_SEATS));
                }

            } catch (ParseException e) {
                page = Config.getInstance().getProperty(Config.ERROR);
                request.setAttribute(ERROR, Message.getInstance().getProperty(Message.PARSE_EXCEPTION));
            }

        } else {
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.EXISTED_TICKET));
            page = Config.getInstance().getProperty(Config.ERROR);
        }


        return page;
    }
}
