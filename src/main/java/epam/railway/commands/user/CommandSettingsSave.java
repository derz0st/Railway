/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.regexp.RegExp;
import epam.railway.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for change user settings
 */
public class CommandSettingsSave implements ICommand {

    private static final String USER = "user";
    private static final String USER_EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String LANGUAGE = "language";
    private static final String USER_ENTITY = "userentity",EMAIL_ERROR = "email_error",
            PASSWORD_ERROR = "password_error",
            FIRST_NAME_ERROR = "first_name_error",
            LAST_NAME_ERROR = "last_name_error",
            REQUIRED_FIELD = "• required field",
            INCORRECT_EMAIL = "• incorrect e-mail";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);

        User currentUser = (User)session.getAttribute(USER_ENTITY);

        String email = request.getParameter(USER_EMAIL);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            if (email.isEmpty()) request.setAttribute(EMAIL_ERROR, REQUIRED_FIELD);
            if (password.isEmpty()) request.setAttribute(PASSWORD_ERROR, REQUIRED_FIELD);
            if (firstName.isEmpty()) request.setAttribute(FIRST_NAME_ERROR, REQUIRED_FIELD);
            if (lastName.isEmpty()) request.setAttribute(LAST_NAME_ERROR, REQUIRED_FIELD);

            return new CommandSettings().execute(request, response);

        } else {

            if (RegExp.validateEmail(email)) {

                User user = UserService.getInstance().findUser(currentUser.getId());
                user.setEmail(email);
                user.setPassword(password);
                user.setFirstname(firstName);
                user.setLastname(lastName);

                UserService.getInstance().updateUser(user);

                session.setAttribute(USER_ENTITY, user);
                session.setAttribute(LANGUAGE, request.getParameter(LANGUAGE));

                request.setAttribute(USER, user);

                page = Config.getInstance().getProperty(Config.SETTINGS);

            } else {
                request.setAttribute(EMAIL_ERROR, INCORRECT_EMAIL);
                return new CommandSettings().execute(request, response);
            }
        }
        return page;
    }
}
