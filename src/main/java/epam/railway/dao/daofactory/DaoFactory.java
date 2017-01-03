/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.daofactory;

import epam.railway.dao.implementation.*;

/**
 *
 * @author denis
 */
public class DaoFactory {
    
    public static DaoUser getDaoUser(){
        return DaoUser.getInstance();
    }
    
    public static DaoTrain getDaoTrain(){
        return DaoTrain.getInstance();
    }

    public static DaoStation getDaoStation(){
        return DaoStation.getInstance();
    }
    
    public static DaoTicket getDaoTicket(){
        return DaoTicket.getInstance();
    }
    
    public static DaoTrainNeo getDaoTrainNeo(){
        return DaoTrainNeo.getInstance();
    }

    public static DaoTrainTicketsOnDate getDaoTrainTicketsOnDate(){
        return DaoTrainTicketsOnDate.getInstance();
    }
    
}

