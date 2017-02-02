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
public class Config {

    private static Config instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";
    public static final String ERROR = "error",
            LOGIN = "login",
            ORDER = "order",
            PAY_TICKET = "pay_ticket",
            USER_TICKETS = "user_tickets",
            ADMINISTRATE = "administrate",
            SETTINGS = "settings",
            CUSTOMER_TICKETS = "customer_tickets",
            RETURN_TICKETS = "return_tickets",
            SIGN_UP = "sign_up";

    private Config(){}
    
    public synchronized static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
