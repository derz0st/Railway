package epam.railway.commands.train;

import epam.railway.commands.ICommand;
import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.TrainTicketsOnDate;
import epam.railway.manager.Config;
import epam.railway.services.TicketsOnDateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by denis on 03.02.17.
 */
public class CommandAddNewTicketsOnDate  implements ICommand {

    private static final String
            INFO = "info",
            DATE = "date",
            TRAIN_NUMBER = "trainnumber",
            TOTAL_SEATS = "totalseats";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String dateString = request.getParameter(DATE);
            Integer trainNumber = Integer.valueOf(request.getParameter(TRAIN_NUMBER));
            Integer totalSeats = Integer.valueOf(request.getParameter(TOTAL_SEATS));


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            Date date = dateFormat.parse(dateString);
            TrainTicketsOnDate trainTickets = TicketsOnDateService.getInstance().find(trainNumber, new Timestamp(date.getTime()));

            if (trainTickets == null) {
                DaoFactory.getDaoTrainTicketsOnDate().addTrainOnDate(trainNumber, new Timestamp(date.getTime()), totalSeats);
                request.setAttribute(INFO, "Билеты успешно добавлены");
            } else {
                request.setAttribute(INFO, "На эту дату билеты добавлены на поезд");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Config.getInstance().getProperty(Config.ADMINISTRATE);
    }
}
