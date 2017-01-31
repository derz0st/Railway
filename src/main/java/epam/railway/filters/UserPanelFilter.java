/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author denis
 */
public class UserPanelFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);
        System.out.println(req);
        System.out.println(req.getParameter("command"));
        String command = req.getParameter("command");

        if (session.getAttribute("userentity") == null && !command.equals("signup") && !command.equals("signupaction") && !command.equals("login")){
            String page;

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/views/sign-in.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.println(session.toString() + "это какая то сессия");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}