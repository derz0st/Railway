/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.manager;

import java.util.ResourceBundle;

/**
 *
 * @author denis
 */
public class Message {

    private static Message instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "messages";
    public static final String SERVLET_EXECPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String PERMISSION_DENIED = "permission_denied",
            PARSE_EXCEPTION = "parse_exception",
            NO_SEATS = "no_seats",
            EXISTED_TICKET = "already_existed_ticket",
            INCORRECT_DATE = "incorrect_date",
            INCORRECT_L_OR_P = "incorrect_l_or_p",
            BLOCKED_USER = "blocked_user";
    public static final String UNEXIST_PAGE = "UNEXIST_PAGE";
    public static final String NO_TRAINS = "NO_TRAINS";

    public synchronized static Message getInstance() {
        if (instance == null) {
            instance = new Message();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}