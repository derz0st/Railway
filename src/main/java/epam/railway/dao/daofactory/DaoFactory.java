/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.daofactory;

import epam.railway.dao.implementation.*;
import epam.railway.dao.interfaces.*;

/**
 *
 * @author denis
 */
public class DaoFactory {
    
    public static DaoUserInterface getDaoUser(){
        return DaoUser.getInstance();
    }

    public static DaoStationInterface getDaoStation(){
        return DaoStation.getInstance();
    }
    
    public static DaoTicketInterface getDaoTicket(){
        return DaoTicket.getInstance();
    }
    
    public static DaoTrainInterface getDaoTrainNeo(){
        return DaoTrain.getInstance();
    }

    public static DaoTrainTicketsOnDateInterface getDaoTrainTicketsOnDate(){
        return DaoTrainTicketsOnDate.getInstance();
    }
    
}

