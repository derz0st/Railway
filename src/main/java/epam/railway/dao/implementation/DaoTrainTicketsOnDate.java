package epam.railway.dao.implementation;

import epam.railway.dao.interfaces.DaoTrainTicketsOnDateInterface;
import epam.railway.entities.TrainTicketsOnDate;
import epam.railway.manager.connectionpool.mysql.ConnectionPool;

import javax.naming.NamingException;
import java.sql.*;

/**
 * Created by denis on 29.12.16.
 */
public class DaoTrainTicketsOnDate implements DaoTrainTicketsOnDateInterface{

    private static DaoTrainTicketsOnDate instance;
    private static final String QUERY_FIND_BY_TRAIN_NUMBER_AND_DATE = "SELECT * FROM train_tickets_on_date WHERE train_number = ? AND date = ?";
    private static final String QUERY_INC_BUSY_SEATS = "UPDATE train_tickets_on_date SET busy_seats = busy_seats + 1  WHERE train_number = ? AND date = ?";
    private static final String QUERY_DESC_BUSY_SEATS = "UPDATE train_tickets_on_date SET busy_seats = busy_seats - 1  WHERE train_number = ? AND date = ?";
    private static final String QUERY_INSERT_TRAIN_ON_DATE = "INSERT INTO train_tickets_on_date (train_number, " +
            "date, busy_seats, total_seats) VALUES (?,?,0,?);";

    private DaoTrainTicketsOnDate(){}

    public static DaoTrainTicketsOnDate getInstance() {
        if (instance == null) {
            instance = new DaoTrainTicketsOnDate();
        }
        return instance;
    }

    public void addTrainOnDate(Integer trainNumber, Timestamp date, Integer totalSeats){

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_TRAIN_ON_DATE)) {

            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setTimestamp(2, date);
            preparedStatement.setInt(3, totalSeats);

            preparedStatement.execute();

        } catch (NamingException | SQLException ex) {
            System.out.println("ошибка");
        }
    }

    @Override
    public TrainTicketsOnDate findByTrainNumberAndDate(Integer trainNumber, Timestamp date) {
        TrainTicketsOnDate trainTicketsOnDate = null;

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_TRAIN_NUMBER_AND_DATE)) {

            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setString(2, date.toString());
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if(resultSet.next()){

                    trainTicketsOnDate = new TrainTicketsOnDate();
                    trainTicketsOnDate.setId(resultSet.getInt("id"));
                    trainTicketsOnDate.setTrainNumber(resultSet.getInt("train_number"));
                    trainTicketsOnDate.setDate(resultSet.getTimestamp("date"));
                    trainTicketsOnDate.setBusySeats(resultSet.getInt("busy_seats"));
                    trainTicketsOnDate.setTotalSeats(resultSet.getInt("total_seats"));
                    System.out.println(trainTicketsOnDate);
                }

            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ошибка");
        }
        return trainTicketsOnDate;
    }


    public boolean incBusySeatsByTrainNumberAndDate(Integer trainNumber, Timestamp date){

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INC_BUSY_SEATS)) {

            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setTimestamp(2, date);

            preparedStatement.executeUpdate();

        } catch (NamingException | SQLException ex) {
            System.out.println("ошибка");
            return false;
        }

        return true;
    }

    public boolean descBusySeatsByTrainNumberAndDate(Integer trainNumber, Timestamp date){

        try (Connection connection = ConnectionPool.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DESC_BUSY_SEATS)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setTimestamp(2, date);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (NamingException | SQLException ex) {
            System.out.println("ошибка");
            return false;
        }
        return true;
    }

}
