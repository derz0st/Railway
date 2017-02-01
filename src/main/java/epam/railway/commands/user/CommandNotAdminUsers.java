/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command for display all not admin users. Only administrator can execute it.
 */
public class CommandNotAdminUsers implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandNotAdminUsers.class);
    private static final String USERS = "users",
            ERROR = "error",
            USER_ENTITY = "userentity";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);

        if (currentUser.getAdminid().equals(1)) {
            List<User> users = UserService.getInstance().findNotAdminUsers();
            request.setAttribute(USERS, users);
            page = Config.getInstance().getProperty(Config.ADMINISTRATE);
        } else {
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.PERMISSION_DENIED));
            page = Config.getInstance().getProperty(Config.ERROR);
            commandLogger.error("Not admin user id: " + currentUser.getId() + " tried to get user list");
        }

        return page;
    }
  
}
