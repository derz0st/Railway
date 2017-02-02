package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for display user's shopping cart
 */
public class CommandShoppingCart implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        return Config.getInstance().getProperty(Config.PAY_TICKET);

    }
}
