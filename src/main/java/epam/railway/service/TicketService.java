/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.service;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.dao.interfaces.DaoTicketInterface;
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

    private DaoTicketInterface daoTicket = DaoFactory.getDaoTicket();
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

    public List<Ticket> getReturnTickets(){
        List<Ticket> userTickets = DaoFactory.getDaoTicket().findByReturnStatus();
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

            DaoFactory.getDaoTrainTicketsOnDate().descBusySeatsByTrainNumberAndDate(ticket.getTrainNumber(), ticketDateWithoutTime);
            DaoFactory.getDaoTicket().returnByTicketId(2, ticket.getId());
            System.out.println("Статус транзакции: " + userTransaction.getStatus());

            System.out.println("Статус транзакции 2: " + userTransaction.getStatus());
            //это всё не работает
            if (true){
                userTransaction.rollback();
            }else userTransaction.commit();
            System.out.println("Строка commit позади");

        } catch (NamingException | HeuristicRollbackException | NotSupportedException | SystemException | HeuristicMixedException | RollbackException e) {
            System.out.println("Не получилось");
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                System.out.println("Не получилось1");
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    public static void deleteTicket(Integer ticketId) {
        DaoFactory.getDaoTicket().deleteByTicketId(ticketId);
        log.info("Remove ticket: ticketId = " + ticketId);
    }
    
}
