/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.filters;

import javax.servlet.*;

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

//        HttpServletRequest req = (HttpServletRequest) request;
//
//        HttpSession session = req.getSession(false);
//
//        if (request.getParameter("command").equals("settings")) {
//            try {
//                User user = (User) session.getAttribute("userentity");
//                System.out.println("В сессии сейчас: " + user.getLastname());
//
//            }catch (NullPointerException e){
//                System.out.println("В сессии nullPointer");
//                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/views/sign-in.jsp");
//                dispatcher.forward(request, response);
//            }
//        }
//        if (request == null) System.out.println("request id null");
//        if (response == null) System.out.println("response id null");
//        if (chain == null) System.out.println("error chain");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}