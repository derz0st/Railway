/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.servlet;

import epam.railway.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 *
 * @author denis
 */
public class ControllerHelper {
    private static ControllerHelper instance = null;
    HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put("order", new CommandOrder());
        commands.put("findtickets", new CommandFindTickets());
        commands.put("login", new CommandLogin());
        commands.put("signup", new CommandSignUp());
        commands.put("pay", new CommandPay());
        commands.put("buyticket", new CommandBuyTicket());
        commands.put("usertickets", new CommandUserTickets());
        commands.put("logout", new CommandLogout());
        commands.put("administration", new CommandNotAdminUsers());
        commands.put("deleteticket", new CommandDeleteTicket());
        commands.put("deleteuser", new CommandDeleteUser());
        commands.put("settings", new CommandSettings());
        commands.put("settingssave", new CommandSettingsSave());
        commands.put("ordertest", new CommandOrderTest());
        commands.put("shoppingcart", new CommandShoppingCart());
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
 