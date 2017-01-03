package epam.railway.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class Neo4jConnectionPool {

    private Vector<Connection> availableConns = new Vector<Connection>();
    private Vector<Connection> usedConns = new Vector<Connection>();
    private String url = "jdbc:neo4j:bolt://localhost";
    private String userName = "neo4j";
    private String userPassword = "ycthssw5sn";
    private String driver = "org.neo4j.jdbc.Driver";
    private Integer initConnCnt = 5;
    private static Neo4jConnectionPool instance;

    private Neo4jConnectionPool(){
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < initConnCnt; i++) {
            availableConns.addElement(getConnection());
        }
    }

    public static Neo4jConnectionPool getInstance() {
        if (instance == null) {
            instance = new Neo4jConnectionPool();
        }
        return instance;
    }


    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;
        if (availableConns.size() == 0) {
            newConn = getConnection();
        } else {
            newConn = (Connection) availableConns.lastElement();
            availableConns.removeElement(newConn);
        }
        usedConns.addElement(newConn);
        return newConn;
    }

    public synchronized void putback(Connection c) throws NullPointerException {
        if (c != null) {
            if (usedConns.removeElement(c)) {
                availableConns.addElement(c);
            } else {
                throw new NullPointerException("Connection not in the usedConns array");
            }
        }
    }

    public int getAvailableConnsCnt() {
        return availableConns.size();
    }
}