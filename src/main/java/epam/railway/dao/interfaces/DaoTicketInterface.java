/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.interfaces;

import epam.railway.entities.Ticket;

import java.util.List;


/**
 *
 * @author denis
 */
public interface DaoTicketInterface {

    /**
     * Adds ticket to database
     * @param ticket ticket to be appended to database
     */
    void addTicket (Ticket ticket);

    /**
     * Finds ticket in database by id
     * @param ticketId id of ticket
     * @return a ticket with specified id
     */
    Ticket findByTicketid(Integer ticketId);

    /**
     * Finds all tickets of specified user
     * @param userId id of user
     * @param actual determine the relevance: actual - true, archive - false
     * @return an ordered ticket list of specified user
     */
    List<Ticket> findByUserid(Integer userId, Boolean actual);

    /**
     * Finds tickets which are waiting for refund
     * @return a waiting for refund ticket list
     */
    List<Ticket> findByReturnStatus();

    /**
     * Delete specified ticket from database
     * @param ticketId id of ticket
     */
    void deleteByTicketId(Integer ticketId);

    /**
     * Change return status of specified ticket
     * @param returnStatus status of refund: 1 - waiting for refund, 2 - refunded
     * @param ticketId id of ticket
     */
    boolean returnByTicketId(Integer returnStatus, Integer ticketId);

}
