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
     *
     * @param ticket ticket to be appended to database
     */
    void addTicket (Ticket ticket);
    Ticket findByTicketid(Integer ticketId);
    List<Ticket> findByUserid(Integer userId, Boolean actual);
    List<Ticket> findByReturnStatus();
    void deleteByTicketId(Integer ticketId);
    void returnByTicketId(Integer returnStatus, Integer ticketId);
//    Ticket findById(Integer id);
//    List<Ticket> findByUserid(Integer userId);
//    void deleteById(Integer id);
//    Integer getBusySeatsOnDate(Integer trainId, String date);
}
