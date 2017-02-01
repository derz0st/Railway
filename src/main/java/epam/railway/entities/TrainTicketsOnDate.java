package epam.railway.entities;

import java.sql.Timestamp;

/**
 * TrainTicketsOnDate entity class
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainTicketsOnDate that = (TrainTicketsOnDate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (trainNumber != null ? !trainNumber.equals(that.trainNumber) : that.trainNumber != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (busySeats != null ? !busySeats.equals(that.busySeats) : that.busySeats != null) return false;
        return totalSeats != null ? totalSeats.equals(that.totalSeats) : that.totalSeats == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (busySeats != null ? busySeats.hashCode() : 0);
        result = 31 * result + (totalSeats != null ? totalSeats.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrainTicketsOnDate{" + "id=" + id + ", trainNumber=" + trainNumber + ", date=" + date + ", busySeats=" + busySeats + ", totalSeats=" + totalSeats + '}';
    }
    
    
}
