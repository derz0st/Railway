package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for redirect in login page
 */
public class CommandSignIn implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return Config.getInstance().getProperty(Config.LOGIN);
    }
}
