/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.interfaces;

import epam.railway.entities.Ticket;


/**
 *
 * @author denis
 */
public interface DaoTicketInterface {
    void addTicket (Ticket ticket);
//    Ticket findById(Integer id);
//    List<Ticket> findByUserid(Integer userId);
//    void deleteById(Integer id);
//    Integer getBusySeatsOnDate(Integer trainId, String date);
}
