package epam.railway.manager.connectionpool.neo4j;

import epam.railway.dao.implementation.DaoTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Neo4jConnectionPool {

    private final ConcurrentLinkedQueue<Neo4jConnection> сonnections = new ConcurrentLinkedQueue<>();
    private final static String url = "jdbc:neo4j:bolt://localhost";
    private final static String userName = "neo4j";
    private final static String userPassword = "ycthssw5sn";
    private final static String driver = "org.neo4j.jdbc.Driver";
    private Integer initConnCnt = 6;
    private static Neo4jConnectionPool instance;
    private static final Logger log = LogManager.getLogger(DaoTicket.class.getName());

    private Neo4jConnectionPool(){
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            log.error("Can't get jdbc driver: " + e.getMessage());
        }
        for (int i = 0; i < initConnCnt; i++) {
            сonnections.add(createConnection());
        }
    }

    public synchronized static Neo4jConnectionPool getInstance() {
        if (instance == null) {
            instance = new Neo4jConnectionPool();
        }
        return instance;
    }


    private Neo4jConnection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Neo4jConnection(connection);
    }

    public synchronized Neo4jConnection getConnection() {
        Neo4jConnection connection;
        if (сonnections.isEmpty()) {
            connection = createConnection();
        } else {
            connection = сonnections.poll();
        }
        return connection;
    }

    public synchronized void putback(Neo4jConnection connection) {
        if (connection != null) {
            сonnections.add(connection);
        } else {
            log.error("Can't put null connection in connection pool");
        }
    }

}