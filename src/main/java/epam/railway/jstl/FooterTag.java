/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.jstl;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author denis
 */
public class FooterTag extends TagSupport{
    private static final long serialVersionUID = 1L;
    
    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<div class=\"footblock\"> Railway Tickets. Epam 2016 </div>");
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }       
        return SKIP_BODY;
    }
}