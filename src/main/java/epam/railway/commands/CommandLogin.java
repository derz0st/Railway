/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.regexp.RegExp;
import epam.railway.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



/**
 *
 * @author denis
 */
public class CommandLogin implements ICommand{
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LANGUAGE = "language";
    private static final String ENGLISH = "en";
    private static final String RUSSIAN = "ru";
    private static final String USER_ID = "userid";
    private static final String TRAINS = "trains";
    private static final String ERROR = "error";
    private static final String ADMIN_RIGHTS = "adminrights";
     
    //private static Logger log = Logger.getLogger(CommandLogin.class.getName());

    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger commandLogger = LogManager.getLogger(CommandLogin.class);
    
   // static final Logger userLogger = LogManager.getLogger(User.class);
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        request.setAttribute("entered_email", email);
        request.setAttribute("entered_password", password);

        page = "/views/sign-in.jsp";
        if (email.isEmpty() || password.isEmpty()){
            if(email.isEmpty()) {
                request.setAttribute("emailerror", "• это обязательное для заполнения поле");
            }
            if(password.isEmpty()) {
                request.setAttribute("passworderror", "• это обязательное для заполнения поле");
            }


            page = "/views/sign-in.jsp";
        } else if (RegExp.validateEmail(email)) {
            User user;
            try {


                user = UserService.findUser(email, password);
                commandLogger.info("User login: id = " + user.getId() + ", email = " + user.getEmail());
                rootLogger.info("User login: id = " + user.getId() + ", email = " + user.getEmail());

                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID, user.getId());
                session.setAttribute("userentity", user);
                session.setAttribute(LANGUAGE, ENGLISH);

                if (user.getIsBlocked() != 1) {
                    String lang = (String) session.getAttribute(LANGUAGE);
                    request.setAttribute(TRAINS, null);
                    if (user.getAdminid() == 1){
                        page = new CommandGetReturnTickets().execute(request, responce);
                    } else {
                        page = Config.getInstance().getProperty(Config.ORDER);
                    }
                } else {
                    request.setAttribute(ERROR, "Пользователь заблокирован. Обратитесь в службу поддержки");
                    page = Config.getInstance().getProperty(Config.ERROR);
                }



            } catch (LoginException ex) {
                request.setAttribute(ERROR, ex.getMessage());
                page = Config.getInstance().getProperty(Config.ERROR);
            }
        } else {
            request.setAttribute("emailerror", "• неккоректно введенный e-mail");
            page = "/views/sign-in.jsp";

        }

        return page;
    }
}
