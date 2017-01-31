package epam.railway.commands.user;

import epam.railway.commands.ICommand;
import epam.railway.entities.User;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import epam.railway.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command for user blocking. Only administrator can block user.
 */
public class CommandBlockUser implements ICommand {

    private static final Logger commandLogger = LogManager.getLogger(CommandBlockUser.class);
    private static final String USER_ID = "userid",
            USER_ENTITY = "userentity",
            ERROR = "error",
            USERS = "users";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        Integer userId = Integer.parseInt(request.getParameter(USER_ID));

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ENTITY);

        if (currentUser.getAdminid().equals(1)){

            User user = UserService.getInstance().findUser(userId);
            System.out.println(user);

            if (user.getIsBlocked() == 0){
                commandLogger.info("Admin user - id: "+ currentUser.getId() + " blocked user.");
                UserService.getInstance().blockUser(userId);
            } else {
                commandLogger.info("Admin user - id: "+ currentUser.getId() + " unlocked user.");
                UserService.getInstance().unlockUser(userId);
            }

            List<User> users = UserService.getInstance().findNotAdminUsers();
            request.setAttribute(USERS, users);
            page = Config.getInstance().getProperty(Config.ADMINISTRATE);

        } else {
            commandLogger.error("Not admin user - id: "+ currentUser.getId() + " tried to block/unlock user.");
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.PERMISSION_DENIED));
            page = Config.getInstance().getProperty(Config.ERROR);
        }

        return page;
    }

}
