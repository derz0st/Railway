/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.entities;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Train entity class
 */
public class Train {

    private Integer id;
    private Integer number;
    private String departureCity;
    private String arrivalCity;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Calendar travelTime;
    private Double price;
    private TrainTicketsOnDate trainTicketsOnDate;
    private List<Station> stations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Calendar getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Calendar travelTime) {
        this.travelTime = travelTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public TrainTicketsOnDate getTrainTicketsOnDate() {
        return trainTicketsOnDate;
    }

    public void setTrainTicketsOnDate(TrainTicketsOnDate trainTicketsOnDate) {
        this.trainTicketsOnDate = trainTicketsOnDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.number);
        hash = 89 * hash + Objects.hashCode(this.departureTime);
        hash = 89 * hash + Objects.hashCode(this.arrivalTime);
        hash = 89 * hash + Objects.hashCode(this.travelTime);
        hash = 89 * hash + Objects.hashCode(this.price);
        hash = 89 * hash + Objects.hashCode(this.stations);
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
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.departureTime, other.departureTime)) {
            return false;
        }
        if (!Objects.equals(this.arrivalTime, other.arrivalTime)) {
            return false;
        }
        if (!Objects.equals(this.travelTime, other.travelTime)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return Objects.equals(this.stations, other.stations);
    }
    
    
}
