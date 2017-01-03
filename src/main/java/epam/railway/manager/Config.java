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
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";
    public static final String ORDER = "ORDER";
    public static final String PAY_TICKET = "PAY_TICKET";
    public static final String USER_TICKETS = "USER_TICKETS";
    public static final String ADMINISTRATE = "ADMINISTRATE";
    public static final String SETTINGS = "SETTINGS";
    public static final String SHOW_TRAIN_DETAIL = "SHOW_TRAIN_DETAIL";

    private Config(){}
    
    public static Config getInstance() {
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
