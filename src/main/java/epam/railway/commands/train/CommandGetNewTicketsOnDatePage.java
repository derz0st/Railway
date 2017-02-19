package epam.railway.commands.train;

import epam.railway.commands.ICommand;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denis on 03.02.17.
 */
public class CommandGetNewTicketsOnDatePage implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return Config.getInstance().getProperty(Config.ADD_TICKETS_ON_DATE);
    }
}
