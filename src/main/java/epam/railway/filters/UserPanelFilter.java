/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.filters;

import epam.railway.entities.User;

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

        if (request.getParameter("command").equals("settings")) {
            try {
                User user = (User) session.getAttribute("userentity");
                System.out.println("В сессии сейчас: " + user.getLastname());

            }catch (NullPointerException e){
                System.out.println("В сессии nullPointer");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/sign-in.jsp");
                dispatcher.forward(request, response);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}