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
        //HttpServletRequest httpRequest = (HttpServletRequest) request;
        //HttpSession session = httpRequest.getSession(false);
        //LangService.setUserPanelAttributes(session, httpRequest);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}