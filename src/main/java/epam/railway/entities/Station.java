/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author denis
 */
public class Station {
    private Integer id;
    private String name;
    private Integer trainNumber;
    private Date arrivalTime;
    private Date departureTime;
    private Double priceToNext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Double getPriceToNext() {
        return priceToNext;
    }

    public void setPriceToNext(Double priceToNext) {
        this.priceToNext = priceToNext;
    }

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.arrivalTime);
        hash = 17 * hash + Objects.hashCode(this.departureTime);
        hash = 17 * hash + Objects.hashCode(this.priceToNext);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.arrivalTime, other.arrivalTime)) {
            return false;
        }
        if (!Objects.equals(this.departureTime, other.departureTime)) {
            return false;
        }
        if (!Objects.equals(this.priceToNext, other.priceToNext)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Station{" + "id=" + id + ", name=" + name + ", trainNumber=" + trainNumber + ", arrivalTime=" + arrivalTime + ", departureTime=" + departureTime + ", priceToNext=" + priceToNext + '}';
    }
    
    
    
}
