/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.services;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoTicketInterface;
import epam.railway.dao.interfaces.DaoTrainTicketsOnDateInterface;
import epam.railway.entities.Ticket;
import epam.railway.entities.comparators.TicketDateComparator;
import epam.railway.manager.jotmtransaction.JotmTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import javax.transaction.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author denis
 */
public class TicketService {

    private final DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
    private final DaoTrainTicketsOnDateInterface daoTrainTicketsOnDate = DaoFactory.getDaoTrainTicketsOnDate();
    private static TicketService instance;
    private static final Logger log = LogManager.getLogger(TicketService.class.getName());

    private TicketService(){}

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public Ticket findTicketById(Integer ticketId){
        return DaoFactory.getDaoTicket().findByTicketid(ticketId);
    }

    public boolean addTicket(Ticket ticket){
        daoTicket.addTicket(ticket);
        return true;
    }

    public List<Ticket> getAllTicketsByUserId(Integer userId){
        List<Ticket> userTickets = daoTicket.findByUserid(userId, null);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public List<Ticket> getActualTicketsByUserId(Integer userId){
        List<Ticket> userTickets = daoTicket.findByUserid(userId, true);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public List<Ticket> getArchiveTicketsByUserId(Integer userId){
        List<Ticket> userTickets = daoTicket.findByUserid(userId, false);
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public List<Ticket> getReturnTickets(){
        List<Ticket> userTickets = daoTicket.findByReturnStatus();
        Collections.sort(userTickets, new TicketDateComparator());
        return userTickets;
    }

    public void returnTicket(Integer ticketId){
        daoTicket.returnByTicketId(1, ticketId);
    }

    public void acceptReturn(Ticket ticket) {

        UserTransaction userTransaction = null;

        try {
            userTransaction = JotmTransaction.getUserTransaction();

            Calendar ticketDate = Calendar.getInstance();
            ticketDate.setTimeInMillis(ticket.getStartDateTime().getTime());
            ticketDate.set(Calendar.HOUR_OF_DAY, 0);
            ticketDate.set(Calendar.MINUTE, 0);
            ticketDate.set(Calendar.SECOND, 0);
            Timestamp ticketDateWithoutTime = new Timestamp(ticketDate.getTimeInMillis());

            userTransaction.begin();

            boolean descRequest = daoTrainTicketsOnDate.descBusySeatsByTrainNumberAndDate(ticket.getTrainNumber(), ticketDateWithoutTime);
            boolean returnRequest = daoTicket.returnByTicketId(2, ticket.getId());

            if (!descRequest || !returnRequest) {
                userTransaction.rollback();
            } else {
                userTransaction.commit();
            }

        } catch (NamingException | HeuristicRollbackException | NotSupportedException | SystemException | HeuristicMixedException | RollbackException e) {
            log.error("Refund transaction error: " + e.getMessage() + ". ticket id: " + ticket.getId());
            try {
                if (userTransaction != null) {
                    userTransaction.rollback();
                }
            } catch (SystemException ex) {
                log.error("Rollback error: " + ex.getMessage());
            }
            e.printStackTrace();
        }
    }


    public void deleteTicket(Integer ticketId) {
        daoTicket.deleteByTicketId(ticketId);
        log.info("Remove ticket: ticketId = " + ticketId);
    }
    
}
