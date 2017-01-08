/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author denis
 */
public class TimeTag extends TagSupport{
    private static final long serialVersionUID = 2L;
	
    private Calendar calendar;
    
    public void setDate(Calendar calendar) {
        this.calendar = calendar;
    }

    
    @Override
    public int doStartTag() throws JspException {
        try {

            Integer days;
            String time;
            if (calendar.get(Calendar.DAY_OF_YEAR) == 365) {
                time = calendar.get(Calendar.HOUR_OF_DAY) + "h" + calendar.get(Calendar.MINUTE) + "m";
            } else {
                time = calendar.get(Calendar.DAY_OF_YEAR) + "d"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "h"
                        + calendar.get(Calendar.MINUTE) + "m";
            }



            pageContext.getOut().print(time);
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }       
        return SKIP_BODY;
    }
}