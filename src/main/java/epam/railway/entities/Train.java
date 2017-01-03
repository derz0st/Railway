/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author denis
 */
@Table(name = "User")
public class Train {
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "departurecity")
    private String departurecity;
    @Column(name = "destinationcity")
    private String destinationcity;
    @Column(name = "departuretime")
    private Date departuretime;
    @Column(name = "arrivaltime")
    private Date arrivaltime;
    @Column(name = "price")
    private Double price;
    @Column(name = "totalseats")
    private Integer totalseats;
    @Column(name = "freeseats")
    private Integer freeseats;
    
    private Date traveltime;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeparturecity() {
        return departurecity;
    }

    public void setDeparturecity(String departurecity) {
        this.departurecity = departurecity;
    }

    public String getDestinationcity() {
        return destinationcity;
    }

    public void setDestinationcity(String destinationcity) {
        this.destinationcity = destinationcity;
    }

    public Date getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(Date departuretime) {
        this.departuretime = departuretime;
    }

    public Date getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(Date arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Date getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(Date traveltime) {
        this.traveltime = traveltime;
    }
    
    public Integer getTotalseats() {
        return totalseats;
    }

    public void setTotalseats(Integer totalseats) {
        this.totalseats = totalseats;
    }

    public Integer getFreeseats() {
        return freeseats;
    }

    public void setFreeseats(Integer freeseats) {
        this.freeseats = freeseats;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.departurecity);
        hash = 97 * hash + Objects.hashCode(this.destinationcity);
        hash = 97 * hash + Objects.hashCode(this.departuretime);
        hash = 97 * hash + Objects.hashCode(this.arrivaltime);
        hash = 97 * hash + Objects.hashCode(this.price);
        hash = 97 * hash + Objects.hashCode(this.traveltime);
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
        final Train other = (Train) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.departurecity, other.departurecity)) {
            return false;
        }
        if (!Objects.equals(this.destinationcity, other.destinationcity)) {
            return false;
        }
        if (!Objects.equals(this.departuretime, other.departuretime)) {
            return false;
        }
        if (!Objects.equals(this.arrivaltime, other.arrivaltime)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.traveltime, other.traveltime)) {
            return false;
        }
        return true;
    }
    
    
    
}
