/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import java.io.IOException;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author denis
 */
public class CommandMissing implements ICommand{
    
    private static final String ERROR = "error";
    private static final String COMMAND = "command";
    private static final Logger log = Logger.getLogger(CommandMissing.class.getName());
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        log.error("404. Command: " + request.getParameter(COMMAND));
        request.setAttribute(ERROR, Message.getInstance().getProperty(Message.UNEXIST_PAGE));
        page = Config.getInstance().getProperty(Config.ERROR);
        return page;
    }
}
