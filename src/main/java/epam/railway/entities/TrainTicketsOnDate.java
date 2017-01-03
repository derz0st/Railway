package epam.railway.entities;

import java.sql.Timestamp;

/**
 * Created by denis on 29.12.16.
 */
public class TrainTicketsOnDate {

    private Integer id;
    private Integer trainNumber;
    private Timestamp date;
    private Integer busySeats;
    private Integer totalSeats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getBusySeats() {
        return busySeats;
    }

    public void setBusySeats(Integer busySeats) {
        this.busySeats = busySeats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "TrainTicketsOnDate{" + "id=" + id + ", trainNumber=" + trainNumber + ", date=" + date + ", busySeats=" + busySeats + ", totalSeats=" + totalSeats + '}';
    }
    
    
}
