/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for logout
 */
public class CommandLogout implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandLogout.class);
    private static final String USER_ENTITY = "userentity";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);
        session.invalidate();
        commandLogger.info("User id: " + currentUser.getId() + " log out");

        return Config.getInstance().getProperty(Config.LOGIN);
    }

}
