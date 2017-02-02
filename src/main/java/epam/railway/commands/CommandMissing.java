/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import epam.railway.manager.Config;
import epam.railway.manager.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author denis
 */
public class CommandMissing implements ICommand{
    
    private static final String ERROR = "error";
    private static final String COMMAND = "command";
    private static final Logger log = LogManager.getLogger(CommandMissing.class.getName());
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.error("404. Command: " + request.getParameter(COMMAND));
        request.setAttribute(ERROR, Message.getInstance().getProperty(Message.UNEXIST_PAGE));
        return Config.getInstance().getProperty(Config.ERROR);
    }
}
