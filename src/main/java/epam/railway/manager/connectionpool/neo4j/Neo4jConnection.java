package epam.railway.manager.connectionpool.neo4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Connection's wrapper for Neo4j connection pool
 */
public class Neo4jConnection implements AutoCloseable {

    private final Connection connection;

    public Neo4jConnection (Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public void close() {
        Neo4jConnectionPool.getInstance().putback(this);
    }


}
