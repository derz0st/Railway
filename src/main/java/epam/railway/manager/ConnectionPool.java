/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.manager;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author denis
 */
public class ConnectionPool {
    public static Connection createConnection() throws NamingException, SQLException {
        DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/railway");
        return ds.getConnection();
    }
}
