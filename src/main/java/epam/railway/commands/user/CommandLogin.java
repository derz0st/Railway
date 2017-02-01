/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.commands.ticket.CommandGetReturnTickets;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.regexp.RegExp;
import epam.railway.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



/**
 * Command for login
 */
public class CommandLogin implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandLogin.class);
    private static final String EMAIL = "email",
            PASSWORD = "password",
            LANGUAGE = "language",
            ENGLISH = "en",
            USER_ID = "userid",
            TRAINS = "trains",
            USER_ENTITY = "userentity",
            ERROR = "error",
            EMAIL_ERROR = "emailerror",
            PASSWORD_ERROR = "passworderror",
            ENTERED_EMAIL = "entered_email",
            ENTERED_PASSWORD = "entered_password",
            REQUIRED_FIELD = "•  it's a required field",
            INCORRECT_EMAIL = "• incorrect e-mail";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        request.setAttribute(ENTERED_EMAIL, email);
        request.setAttribute(ENTERED_PASSWORD, password);

        String page = Config.getInstance().getProperty(Config.LOGIN);

        if (email.isEmpty() || password.isEmpty()){
            if(email.isEmpty()) {
                request.setAttribute(EMAIL_ERROR, REQUIRED_FIELD);
            }
            if(password.isEmpty()) {
                request.setAttribute(PASSWORD_ERROR, REQUIRED_FIELD);
            }

        } else if (RegExp.validateEmail(email)) {

            User user;
            user = UserService.getInstance().findUser(email, password);
            if (user == null){
                request.setAttribute(ERROR, Message.getInstance().getProperty(Message.INCORRECT_L_OR_P));
                page = Config.getInstance().getProperty(Config.ERROR);
            } else if (user.getIsBlocked().equals(0)){

                commandLogger.info("Logging. Email: " + email + ", password: " + password);

                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID, user.getId());
                session.setAttribute(USER_ENTITY, user);
                session.setAttribute(LANGUAGE, ENGLISH);

                request.setAttribute(TRAINS, null);
                if (user.getAdminid() == 1){
                    page = new CommandGetReturnTickets().execute(request, response);
                } else {
                    page = Config.getInstance().getProperty(Config.ORDER);
                }

            } else {
                commandLogger.error("Unsuccessful login attempt. Email: " + email + ", password: " + password);
                request.setAttribute(ERROR, Message.getInstance().getProperty(Message.BLOCKED_USER));
                page = Config.getInstance().getProperty(Config.ERROR);
            }
        } else {
            request.setAttribute(EMAIL_ERROR, INCORRECT_EMAIL);
        }

        return page;
    }

}
