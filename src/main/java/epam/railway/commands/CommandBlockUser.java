package epam.railway.commands;

import epam.railway.dao.daofactory.DaoFactory;
import epam.railway.entities.User;
import epam.railway.manager.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by denis on 08.01.17.
 */
public class CommandBlockUser implements ICommand{
    private static final String USER_ID = "userid";
    private static final String ERROR = "error";
    private static final String USERS = "users";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String page = null;
        Integer userId = Integer.parseInt(request.getParameter(USER_ID));

        HttpSession session = request.getSession(false);
        Integer sessionUserId = (Integer) session.getAttribute(USER_ID);

        User user = DaoFactory.getDaoUser().findById(userId);

        if (user.getIsBlocked() == 1){
            DaoFactory.getDaoUser().unblockById(userId);
        } else {
            DaoFactory.getDaoUser().blockById(userId);
        }



        List<User> users = DaoFactory.getDaoUser().findAllNotAdmin();
        request.setAttribute(USERS, users);
        page = Config.getInstance().getProperty(Config.ADMINISTRATE);



        return page;
    }
}
