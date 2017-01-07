/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.Ticket;
import epam.railway.entities.comparators.TicketDateComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author denis
 */
public class TicketService {

    private static TicketService instance;
    private static final Logger log = LogManager.getLogger(TicketService.class.getName());

    private TicketService(){}

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public boolean addTicket(Ticket ticket){
        DaoFactory.getDaoTicket().addTicket(ticket);
        return true;
    }

    public List<Ticket> getAllTicketsByUserId(Integer userId){
        List<Ticket> userTickets = DaoFactory.getDaoTicket().findByUserid(userId, null);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public List<Ticket> getActualTicketsByUserId(Integer userId){
        List<Ticket> userTickets = DaoFactory.getDaoTicket().findByUserid(userId, true);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public List<Ticket> getArchiveTicketsByUserId(Integer userId){
        List<Ticket> userTickets = DaoFactory.getDaoTicket().findByUserid(userId, false);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }


//    private static final Logger log = Logger.getLogger(TicketService.class.getName());
//    private static final String FORMAT = "dd-MM-yyyy";
//
//    public static void addTicket(Integer userId, Integer trainId, String dateString) throws ParseException{
//
//        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
//        Date date = formatter.parse(dateString);
//        Train train = DaoFactory.getDaoTrain().findById(trainId);
//
//        if(train != null){
//            DaoFactory.getDaoTicket().addTicket(userId, train.getId(), date, train.getDeparturetime());
//        } else {
//            throw new EntityNotFoundException(Message.getInstance().getProperty(Message.TRAIN_NOT_EXIST));
//        }
//
//    }
//
//    public static void deleteTicket(Integer ticketId) {
//        DaoFactory.getDaoTicket().deleteById(ticketId);
//        log.info("Remove ticket: ticketId = " + ticketId);
//    }
    
}
