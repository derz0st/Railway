/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for display user settings
 */
public class CommandSettings implements ICommand {

    private static final String USER_ENTITY = "userentity";
    private static final String USER = "user";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User)session.getAttribute(USER_ENTITY);
        
        User user = UserService.getInstance().findUser(currentUser.getId());
        request.setAttribute(USER, user);

        page = Config.getInstance().getProperty(Config.SETTINGS);
        return page;
    }

}
