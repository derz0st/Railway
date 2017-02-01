/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.manager.Config;
import epam.railway.regexp.RegExp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author denis
 */
public class CommandSignUpAction implements ICommand {

    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeatpassword",
            EMAIL_ERROR = "email_error",
            PASSWORD_ERROR = "password_error",
            FIRSTNAME_ERROR = "firstname_error",
            LASTNAME_ERROR = "lastname_error",
            REPEAT_PASSWORS_ERROR = "repeat_password_error",
            REQUIRED_FIELD = "•  it's a required field",
            INCORRECT_EMAIL = "• incorrect e-mail",
            ALREADY_USED_EMAIL = "• e-mail is already used",
            ENTERED_EMAIL = "entered_email",
            ENTERED_PASSWORD = "entered_password",
            ENTERED_FIRSTNAME = "entered_firstname",
            ENTERED_LASTNAME = "entered_lastname",
            ENTERED_REPEAT_PASSWORD = "entered_repeat_password",
            PASSWORDS_NOT_EQUALS = "• passwords not equals";
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        String page;
        String firstname = request.getParameter(FIRST_NAME);
        String lastname = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);     
        String password = request.getParameter(PASSWORD);
        String repeatpassword = request.getParameter(REPEAT_PASSWORD);
        request.setAttribute(ENTERED_EMAIL, email);
        request.setAttribute(ENTERED_PASSWORD, password);
        request.setAttribute(ENTERED_FIRSTNAME, firstname);
        request.setAttribute(ENTERED_LASTNAME, lastname);
        request.setAttribute(ENTERED_REPEAT_PASSWORD, repeatpassword);

        page = Config.getInstance().getProperty(Config.SIGN_UP);

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || repeatpassword.isEmpty()){
            if(email.isEmpty()) {
                request.setAttribute(EMAIL_ERROR, REQUIRED_FIELD);
            }
            if(password.isEmpty()) {
                request.setAttribute(PASSWORD_ERROR, REQUIRED_FIELD);
            }
            if(firstname.isEmpty()) {
                request.setAttribute(FIRSTNAME_ERROR, REQUIRED_FIELD);
            }
            if(lastname.isEmpty()) {
                request.setAttribute(LASTNAME_ERROR, REQUIRED_FIELD);
            }
            if(repeatpassword.isEmpty()) {
                request.setAttribute(REPEAT_PASSWORS_ERROR, REQUIRED_FIELD);
            }


        } else if (password.equals(repeatpassword)) {
            if (RegExp.validateEmail(email)){
                if (!DaoFactory.getDaoUser().findByEmail(email)) {
                    DaoFactory.getDaoUser().addUser(firstname, lastname, email, password);
                    page = Config.getInstance().getProperty(Config.LOGIN);
                } else {
                    request.setAttribute(EMAIL_ERROR, ALREADY_USED_EMAIL);
                }
            } else {
                request.setAttribute(EMAIL_ERROR, INCORRECT_EMAIL);
            }
        } else {
            request.setAttribute(REPEAT_PASSWORS_ERROR, PASSWORDS_NOT_EQUALS);
            request.setAttribute(PASSWORD_ERROR, PASSWORDS_NOT_EQUALS);
        }
        return page;
    }
    
}
