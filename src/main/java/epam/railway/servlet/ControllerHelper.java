/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.servlet;

import epam.railway.commands.*;
import epam.railway.commands.ticket.*;
import epam.railway.commands.train.*;
import epam.railway.commands.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 *
 * @author denis
 */
public class ControllerHelper {
    private static ControllerHelper instance = null;
    private final HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put("order", new CommandOrder());
        commands.put("findtickets", new CommandFindTickets());
        commands.put("login", new CommandLogin());
        commands.put("signup", new CommandSignUp());
        commands.put("signin", new CommandSignIn());
        commands.put("signupaction", new CommandSignUpAction());
        commands.put("pay", new CommandPay());
        commands.put("buyticket", new CommandBuyTicket());
        commands.put("usertickets", new CommandUserTickets());
        commands.put("logout", new CommandLogout());
        commands.put("administration", new CommandNotAdminUsers());
        commands.put("deleteticket", new CommandDeleteTicket());
        commands.put("blockuser", new CommandBlockUser());
        commands.put("settings", new CommandSettings());
        commands.put("settingssave", new CommandSettingsSave());
        commands.put("shoppingcart", new CommandShoppingCart());
        commands.put("customertickets", new CommandGetCustomerTickets());
        commands.put("returnticket", new CommandReturnTicket());
        commands.put("ticketsonreturn", new CommandGetReturnTickets());
        commands.put("acceptreturn", new CommandAcceptReturn());
        commands.put("newtrain", new CommandGetNewTrainPage());
        commands.put("addnewcrossing", new CommandAddNewCrossing());
        commands.put("newticketsontrain", new CommandGetNewTicketsOnDatePage());
        commands.put("addnewticketsondate", new CommandAddNewTicketsOnDate());
    }
    
    
    public ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null) {
            command = new CommandMissing();
        }
        return command;
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }
}
 