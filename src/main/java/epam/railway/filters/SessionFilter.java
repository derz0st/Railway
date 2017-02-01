/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.filters;

import epam.railway.manager.Config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author denis
 */
public class SessionFilter implements Filter {

    private FilterConfig filterConfig;
    private static final String COMMAND = "command",
            USER_ENTITY = "userentity",
            SIGN_UP = "signup",
            SIGN_UP_ACTION = "signupaction",
            LOGIN = "login";

    @Override
    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);
        String command = req.getParameter(COMMAND);

        if (session == null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Config.getInstance().getProperty(Config.LOGIN));
            dispatcher.forward(request, response);
        } else if (session.getAttribute(USER_ENTITY) == null && !command.equals(SIGN_UP) && !command.equals(SIGN_UP_ACTION) && !command.equals(LOGIN)) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Config.getInstance().getProperty(Config.LOGIN));
            dispatcher.forward(request, response);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}