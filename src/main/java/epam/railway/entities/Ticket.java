/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.entities;

import java.sql.Timestamp;

/**
 * Ticket entity class
 */
public class Ticket {

    private Integer id;
    private Integer userid;
    private String userName;
    private String userLastName;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Double price;
    private Integer trainNumber;
    private String departureCity;
    private String destinationCity;
    private Integer returnStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != null ? !id.equals(ticket.id) : ticket.id != null) return false;
        if (userid != null ? !userid.equals(ticket.userid) : ticket.userid != null) return false;
        if (userName != null ? !userName.equals(ticket.userName) : ticket.userName != null) return false;
        if (userLastName != null ? !userLastName.equals(ticket.userLastName) : ticket.userLastName != null)
            return false;
        if (startDateTime != null ? !startDateTime.equals(ticket.startDateTime) : ticket.startDateTime != null)
            return false;
        if (endDateTime != null ? !endDateTime.equals(ticket.endDateTime) : ticket.endDateTime != null) return false;
        if (price != null ? !price.equals(ticket.price) : ticket.price != null) return false;
        if (trainNumber != null ? !trainNumber.equals(ticket.trainNumber) : ticket.trainNumber != null) return false;
        if (departureCity != null ? !departureCity.equals(ticket.departureCity) : ticket.departureCity != null)
            return false;
        return destinationCity != null ? destinationCity.equals(ticket.destinationCity) : ticket.destinationCity == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userLastName != null ? userLastName.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (departureCity != null ? departureCity.hashCode() : 0);
        result = 31 * result + (destinationCity != null ? destinationCity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userid=" + userid +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", price=" + price +
                ", trainNumber=" + trainNumber +
                ", departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                '}';
    }
}
