/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.jstl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author denis
 */
public class TimeTag extends TagSupport{
    private static final long serialVersionUID = 2L;
	
    private Date date;
    private String format;
    
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            String time = simpleDateFormat.format(date);
            pageContext.getOut().print(time);
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }       
        return SKIP_BODY;
    }
}